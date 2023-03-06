<template>
<!--  <button @click="start" value="start">스타트</button>-->
<!--  <button @click="stop" value="stop">스탑</button>-->
  <div>
    <div id="room">
      <div style="height: 8vh;"></div>
      <!--      <h2 id="room-header">{{info.title}}</h2>-->
      <div style="padding: 15px;">
        <div class="title-wrapper">
          <img src="../../../assets/img/Agora3.png"/>
          <h2 class="title">토론 주제</h2>
          <div class="debate-title">{{ data.title }}</div>
        </div>
      </div>
      <div class="box_1" style="color: purple;">
        <div id="timer-찬성" style="margin-left: 300px;">{{addZeros(parseInt(parseInt(data.time) / 60), 2) + ':' + addZeros(data.time % 60, 2)}}</div>
        <div id="timer-반대" style="margin-right: 300px;">{{addZeros(parseInt(parseInt(data.time) / 60), 2) + ':' + addZeros(data.time % 60, 2)}}</div>
      </div>
        <div style="height: 5vh; margin-bottom: 15px;"></div>
      <div
          :class="[middle_box ? 'participant-box_2' : 'participant-box_2']"
          id="participants">
        <div id="participants-agree"
             :class="[middle_box ? 'A_box' : 'A_box_2']"></div>
        <div id="moderator"
             class=moderator></div>
        <div id="participants-opp"
             :class="[middle_box ? 'B_box' : 'B_box_2']"></div>
      </div>
      <div id="timer" style="font-size: 20px"></div>
      <div id="screen"></div>
      <div id="buttons" style="display: none">
        <input type="button" id="button-start" onMouseUp="start();"
               value="start">
        <input type="button" id="button-stop" onMouseUp="stop();"
               value="stop">
        <input type="button" id="button-share-on" onMouseUp="shareScreen()"
               value="share screen on">
        <input type="button" id="button-share-off" onMouseUp="stopShareScreen()" style="display: none"
               value="share screen off">
        <input type="button" id="vidOn" onMouseUp="videoOnOff()" style="display: none"
               value="video On">
        <input type="button" id="vidOff" onMouseUp="videoOnOff()"
               value="video Off">
        <input type="button" id="audOn" onMouseUp="audioOnOff()" style="display: none"
               value="Audio On">
        <input type="button" id="audOff" onMouseUp="audioOnOff()"
               value="Audio Off">
        <input type="button" id="button-system-comment" onMouseUp="sendSystemComment()"
               value="system comment">
        <input type="button" id="button-leave" onMouseUp="leaveRoom();"
               value="Leave room">
      </div>
    </div>
  </div>
</template>


<script>
import {mapState, useStore} from "vuex";
import kurentoUtils from "kurento-utils";
import {onMounted, reactive, watch} from "vue";
import Participant from "../../../assets/js/participant.js";

export default {
  name: 'debate2',
  components: {},
  computed: {
    ...mapState('debate', {info: 'participantInfo'}),
    ...mapState('debate', {middle_box: 'middle_box'})
  },
  props: {
    call: JSON
  },
  setup(props) {
    const store = useStore();
    console.log('보고타', store)
    const data = reactive({
      ws: store.state.debate.webRtcSocket,
      participants: {},
      name: '',
      title: '',
      position: '',
      time: '00:00',
    })
    onMounted(() => {
      connect();
      console.log('============= ws 테스트 ==============')
      console.log(data.ws);
      store.commit('debate/SET_WEB_SOCKET', data.ws);
    })

    const makeWebsocket = () => {
      data.ws = new WebSocket('wss://i8c205.p.ssafy.io:8083/groupcall');

      data.ws.onopen = () => {
        console.log('WebSocket connection established');
        register()
      };

      data.ws.onerror = (error) => {
        console.error('WebSocket connection error:', error);
      };
    }

    const connect = async () => {
      await makeWebsocket()

      data.ws.onmessage = (message) => {
        let parsedMessage = JSON.parse(message.data);
        console.info('Received message: ' + message.data);
        switch (parsedMessage.id) {
          case 'existingParticipants':
            onExistingParticipants(parsedMessage);
            break;
          case 'newParticipantArrived':
            onNewParticipant(parsedMessage);
            break;
          case 'participantLeft':
            onParticipantLeft(parsedMessage);
            break;
          case 'receiveVideoAnswer':
            receiveVideoResponse(parsedMessage);
            break;
          case 'iceCandidate':
            console.log(parsedMessage.userName, "parsedMessage.userName");
            const test = data.participants[parsedMessage.userName];
            console.log(test, "test");
            console.log("test.rtcPeer", test.rtcPeer);
            data.participants[parsedMessage.userName].rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
              if (error) {
                console.error("Error adding candidate: " + error);
                return;
              }
            });
            break;
          case 'timeRemaining':
            let time = parsedMessage.time;
            if (parsedMessage.position === '찬성') {
              document.getElementById('timer-' + '찬성').innerText = addZeros(parseInt(time / 60), 2) + ':' + addZeros(time % 60, 2)
            }
            else if (parsedMessage.position === '반대'){
              document.getElementById('timer-' + '반대').innerText = addZeros(parseInt(time / 60), 2) + ':' + addZeros(time % 60, 2)
          }
            break;
          case 'pauseSpeaking':``
            time = parsedMessage.time;
            if (parsedMessage.position === '찬성') {
              document.getElementById('timer-' + '찬성').innerText = addZeros(parseInt(time / 60), 2) + ':' + addZeros(time % 60, 2)
            }
            else if (parsedMessage.position === '반대'){
              document.getElementById('timer-' + '반대').innerText = addZeros(parseInt(time / 60), 2) + ':' + addZeros(time % 60, 2)
            }
            break
          case 'receiveSystemComment':
            alert(parsedMessage.comment)
            break;
          case 'terminateDebate':
            alert('토론이 종료되었습니다.');
            this.leaveRoom();
            break
          default:
            console.error('Unrecognized message', parsedMessage);
        }
      }
      console.log('connect done')
    }

    const addZeros = (num, digit) => { // 자릿수 맞춰주기
      let zero = '';
      num = num.toString();
      if (num.length < digit) {
        for (let i = 0; i < digit - num.length; i++) {
          zero += '0';
        }
      }
      return zero + num;
    }

    const register = () => {
      console.log('register')
      data.name = props.call.name;
      data.title = props.call.title;
      let debateId = props.call.debateId;
      data.position = props.call.position;
      let roomType = props.call.roomType;
      data.time = props.call.time;

      sendMessage({
        id: 'createRoom',
        debateId: debateId,
        title: data.title,
        roomType: roomType,
        time: data.time
      })

      let message = {
        id: 'joinRoom',
        userName: data.name,
        debateId: debateId,
        title: data.title,
        position: data.position,
      }
      sendMessage(message);
      console.log(store.state.debate.participant)
      console.log(data.name)
      if (store.state.debate.participant_list === undefined) {
        store.state.debate.participant_list = new Set();
      }
      store.state.debate.participant_list.add(data.name)
    }

    const onIceCandidate = (candidate) => {
      let message = {
        id: 'onIceCandidate',
        candidate: candidate,
        userName: data.name
      };
      sendMessage(message);
    }

    const onNewParticipant = (request) => {
      console.log('리퀘스트', request)
      store.state.debate.participant_list.add(request.userName)
      receiveVideo(request.userName, request.position, request.isScreen);
    }

    const onExistingParticipants = (msg) => {
      let constraints = {
        audio: false,
        video: {
          mandatory: {
            maxWidth: 320,
            maxFrameRate: 15,
            minFrameRate: 15
          }
        }
      };

      console.log(data.name + " registered in room " + data.title);
      let participant = new Participant(data.name, data.position, msg.isScreen);
      data.participants[data.name] = participant;
      participant.constraints = constraints;

      let video = participant.getVideoElement();

      participant.onIceCandidate = (candidate) => {
        let message = {
          id: 'onIceCandidate',
          candidate: candidate,
          userName: data.name
        };
        sendMessage(message);
      };

      let options = {
        localVideo: video,
        mediaConstraints: constraints,
        onicecandidate: onIceCandidate
      }

      data.participants[data.name].rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerSendonly(options, function (error) {
        console.log('이젠여기', options)
        store.commit("debate/participantRegister", data.participants)
        store.commit("debate/Register", data.name)
        console.log('이젠여기3', store.state.debate.participant)

        if (error) {
          return console.error(error);
        }
        this.generateOffer((error, offerSdp, wp) => {
          let msg = {
            id: "receiveVideoFrom",
            sender: data.name,
            sdpOffer: offerSdp
          };
          sendMessage(msg);
        });
      });

      msg.data.forEach(m => {
        receiveVideo(m.userName, m.position, m.isScreen)
        store.state.debate.participant_list.add(m.userName)
      });
    }


    const receiveVideoResponse = (result) => {
      console.log(data.participants);
      data.participants[result.userName].rtcPeer.processAnswer(result.sdpAnswer, function (error) {
        if (error) return console.error(error);
      });
    }

    const start = () => {
      let debateId = store.state.debate.participantInfo.debateId
      let time = store.state.debate.participantInfo.time

      console.log({
        id: 'startSpeaking',
        debateId: debateId,
        time: time
      });
      sendMessage({
        id: 'startSpeaking',
        debateId: debateId,
        time: time
      });
    }

    const stop = () => {
      let debateId = store.state.debate.participantInfo.debateId

      sendMessage({
        id: 'pauseSpeaking',
        debateId: debateId
      });
    }

    const shareScreen = () => {
      name = document.getElementById('name').value;
      let debateId = document.getElementById('debateId').value;
      let title = document.getElementById('title').value;
      let isScreen = true;

      this.sendMessage({
        id: 'leaveRoom'
      });
      for (var key in data.participants) {
        if (data.participants[key].name !== name) {
          var partVideo = document.getElementById("video-" + data.participants[key].name);
          console.log('leave', data.participants[key].name);
          partVideo.parentElement.remove();
        }
      }
      delete data.participants[data.name];
      store.state.debate.participant_list.delete(data.name)
      document.getElementById("video-" + name).parentElement.remove();

      var message = {
        id: 'shareScreen',
        userName: data.name,
        debateId: debateId,
        title: title,
        position: data.position,
      }
      // name = 'screen_' + name;
      sendMessage(message);
      document.getElementById("button-share-on").style.display = "none";
      document.getElementById("button-share-off").style.display = "";
    }
    const stopShareScreen = () => {
      name = document.getElementById('name').value;
      let debateId = document.getElementById('debateId').value;
      let title = document.getElementById('title').value;
      let isScreen = false;

      sendMessage({
        id: 'leaveRoom'
      });
      for (let key in data.participants) {
        if (data.participants[key].name !== name) {
          let partVideo = document.getElementById("video-" + data.participants[key].name);
          console.log('leave', data.participants[key].name);
          partVideo.parentElement.remove();
        }
      }
      delete data.participants[data.name];
      document.getElementById(name).remove();
      let video = document.getElementById("video-" + name);
      video.srcObject.getTracks().forEach(track => track.stop());
      video.remove();


      var message = {
        id: 'joinRoom',
        userName: data.name,
        debateId: debateId,
        position: data.position,
      }
      // name = 'screen_' + name;
      sendMessage(message);
      document.getElementById("button-share-on").style.display = "";
      document.getElementById("button-share-off").style.display = "none";
    }

    const leaveRoom = () => {
      sendMessage({
        id: 'leaveRoom'
      });

      for (var key in data.participants) {
        data.participants[key].dispose();
      }

      data.ws.close();
    }
    const receiveVideo = (name, position, isScreen) => {
      console.log(name, position, "======================= receiveVideo")
      var sender = name;
      var participant = new Participant(name, position, isScreen);
      data.participants[name] = participant;
      var video = participant.getVideoElement();


      participant.onIceCandidate = (candidate) => {
        let message = {
          id: 'onIceCandidate',
          candidate: candidate,
          userName: name
        };
        sendMessage(message);
      };

      let options = {
        remoteVideo: video,
        onicecandidate: participant.onIceCandidate.bind(participant)
      }

      participant.rtcPeer = new kurentoUtils.WebRtcPeer.WebRtcPeerRecvonly(options,
          function (error) {
            if (error) {
              return console.error(error);
            }
            this.generateOffer((error, offerSdp, wp) => {
              let msg = {
                id: "receiveVideoFrom",
                sender: name,
                sdpOffer: offerSdp
              };
              sendMessage(msg);
            })
          });
    }

    // const callResponse = (message) => {
    //   if (message.response !== 'accepted') {
    //     console.info('Call not accepted by peer. Closing call');
    //     stop();
    //   } else {
    //     webRtcPeer.processAnswer(message.sdpAnswer, function (error) {
    //       if (error) return console.error(error);
    //     });
    //   }
    // }
    const onParticipantLeft = (request) => {
      console.log('Participant ' + request.userName + ' left');
      var participant = data.participants[request.userName];
      document.getElementById('video-' + request.userName).remove();
      participant.dispose();
      delete data.participants[request.userName];
      store.state.debate.participant_list.delete(request.userName)
    }
    const sendMessage = (message) => {
      console.log(data.ws)
      console.log('send message', message)
      let jsonMessage = JSON.stringify(message);
      console.log('Sending message: ' + jsonMessage);
      data.ws.send(jsonMessage);
      console.log(data.ws)
    }
    // const videoOnOff = () => {
    //   if (data.participants[data.name].rtcPeer.videoEnabled) {
    //     // 끌때
    //     data.participants[name].rtcPeer.videoEnabled = false;
    //     document.getElementById("vidOn").style.display = "";
    //     document.getElementById("vidOff").style.display = "none";
    //   } else {
    //     data.participants[name].rtcPeer.videoEnabled = true;
    //     document.getElementById("vidOn").style.display = "none";
    //     document.getElementById("vidOff").style.display = "";
    //   }
    // }
    // const audioOnOff = () => {
    //   if (data.participants[name].rtcPeer.audioEnabled) {
    //     data.participants[name].rtcPeer.audioEnabled = false;
    //     document.getElementById("audOn").style.display = "";
    //     document.getElementById("audOff").style.display = "none";
    //   } else {
    //     data.participants[name].rtcPeer.audioEnabled = true;
    //     document.getElementById("audOn").style.display = "none";
    //     document.getElementById("audOff").style.display = "";
    //   }
    // }
    const sendSystemComment = () => {
      let debateId = document.getElementById('debateId').value;

      sendMessage({
        id: 'sendSystemComment',
        debateId: debateId,
        comment: 'test',
      })
    }
    const terminateDebate = () => {
      let debateId = document.getElementById('debateId').value;

      sendMessage({
        id: 'terminateDebate',
        debateId: debateId
      })
    }

    return {store, data, start, stop, connect, addZeros, leaveRoom}
  }
}

</script>

<style scoped>
.participant-box {
  height: 20vh;
  width: 100vw;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.participant-box_2 {
  height: 92vh;
  width: 90vw;
  display: flex;
  flex-direction: row;
  /*align-items: center;*/
  justify-content: center;
  margin: auto;
}

.A_box {
  height: 249px;
  width: 329px;
  display: flex;
  flex-direction: row;
  border: orangered solid 5px;
}

.A_box_2 {
  height: 40vh;
  width: 20vw;
  display: flex;
  flex-direction: column;
  border: orangered solid 5px;
  margin: 30px;

}

@media screen and (max-width: 800px) {
  .A_box {
    display: none;
  }
}

.moderator {
  width: 20vw;
}

.B_box {
  height: 249px;
  width: 329px;

  display: flex;
  flex-direction: row;
  border: navy solid 5px;
}

.B_box_2 {
  height: 40vh;
  width: 20vw;
  display: flex;
  flex-direction: column;
  border: navy solid 5px;
  margin: 30px;
}

@media screen and (max-width: 800px) {
  .B_box {
    display: none;

  }
}

.btn {
  color: red;
  background-color: red;
}

.moderator {
  height: 249px;
  width: 330px;
  border: purple 5px solid;
}

#timer {
  color: red;
}

#room-header {
  text-align: center;
  color: blueviolet;
}

.debate-title {
  font-size: 30px;
  padding: 10px;
  font-family: 'KIMM_Bold';
}

.title-wrapper {
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.6); /* 배경색상 */
  padding: 10px; /* 제목 주위 여백 */
  text-align: center;

}


.title-wrapper img {
  display: inline-block;
  width: 30px;

}

.title-wrapper .title {
  display: inline-block;
  font-size: 20px;
  /*color: #FF4667;*/
  color: #392C7D;
}

.title {
  text-align: center; /* 제목 가운데 정렬 */
}

.box_1 {
  height: 3vh;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-family: "LABD";
  font-size: 50px;
}

@font-face {
  font-family: 'KIMM_Bold';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2212@1.0/KIMM_Bold.woff2') format('woff2');
  font-weight: 700;
  font-style: normal;
}

@font-face {
  font-family: "LABD";
  src:url("../../../assets/fonts/LABD.woff") format("woff");
  font-weight: normal;;
  font-style: normal;
}

</style>