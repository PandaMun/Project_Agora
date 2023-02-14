<template>
  <div>
    <div id="room">
      <h2 id="room-header"></h2>
      <div
          :class="[middle_box ? 'participant-box' : 'participant-box_2']"
          id="participants">
        <div id="participants-agree"
             :class="[middle_box ? 'A_box' : 'A_box_2']"></div>
        <div id="moderator"></div>
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
import {onMounted, reactive} from "vue";
import Participant from "../../../assets/js/participant.js";

export default {
  name: 'debate2',
  components: {},
  computed: {
    ...mapState('debate', {middle_box: 'middle_box'})
  },
  props: {
    call: JSON
  },
  setup(props) {
    const store = useStore();
    const data = reactive({
      ws: store.getters["debate/getWebRtcSocket"],
      participants: {},
      name: '',
      title: '',
      position: ''
    })
    onMounted(() => {
      connect();
    })

    const makeWebsocket = () => {
      data.ws = new WebSocket('wss://localhost:8086/groupcall');
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
            onExistingParticipants(parsedMessage)
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
            document.getElementById('timer').innerText = parseInt(time / 60) + ':' + time % 60
            break
          case 'pauseSpeaking':
            time = parsedMessage.time;
            document.getElementById('timer').innerText = parseInt(time / 60) + ':' + time % 60
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

    const register = () => {
      console.log('register')
      data.name = props.call.name;
      data.title = props.call.title;
      let debateId = props.call.debateId;
      data.position = props.call.position;
      let roomType = props.call.roomType;
      let time = props.call.time;

      sendMessage({
        id: 'createRoom',
        debateId: debateId,
        title: data.title,
        roomType: roomType,
        time: time
      })

      let message = {
        id: 'joinRoom',
        userName: data.name,
        debateId: debateId,
        title: data.title,
        position: data.position,
      }
      sendMessage(message);
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
      receiveVideo(request.userName, request.position, request.isScreen);
    }

    const onExistingParticipants = (msg) => {
      let constraints = {
        audio: true,
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

      msg.data.forEach(m => (receiveVideo(m.userName, m.position, m.isScreen)));
    }

    const receiveVideoResponse = (result) => {
      console.log(data.participants);
      data.participants[result.userName].rtcPeer.processAnswer(result.sdpAnswer, function (error) {
        if (error) return console.error(error);
      });
    }

    const start = () => {
      let debateId = document.getElementById('debateId').value;

      sendMessage({
        id: 'startSpeaking',
        debateId: debateId
      });
    }

    const stop = () => {
      let debateId = document.getElementById('debateId').value;

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

      document.getElementById('join').style.display = 'block';
      document.getElementById('room').style.display = 'none';

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
      console.log('Participant ' + request.name + ' left');
      var participant = data.participants[request.name];
      participant.dispose();
      document.getElementById('video-' + request.name).remove();
      delete data.participants[request.name];
    }
    const sendMessage = (message) => {
      console.log(data.ws)
      console.log('send message', message)
      let jsonMessage = JSON.stringify(message);
      console.log('Sending message: ' + jsonMessage);
      data.ws.send(jsonMessage);
      console.log(data.ws)
    }
    const videoOnOff = () => {
      if (data.participants[name].rtcPeer.videoEnabled) {
        // 끌때
        data.participants[name].rtcPeer.videoEnabled = false;
        document.getElementById("vidOn").style.display = "";
        document.getElementById("vidOff").style.display = "none";
      } else {
        data.participants[name].rtcPeer.videoEnabled = true;
        document.getElementById("vidOn").style.display = "none";
        document.getElementById("vidOff").style.display = "";
      }
    }
    const audioOnOff = () => {
      if (data.participants[name].rtcPeer.audioEnabled) {
        data.participants[name].rtcPeer.audioEnabled = false;
        document.getElementById("audOn").style.display = "";
        document.getElementById("audOff").style.display = "none";
      } else {
        data.participants[name].rtcPeer.audioEnabled = true;
        document.getElementById("audOn").style.display = "none";
        document.getElementById("audOff").style.display = "";
      }
    }
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

    return {store, data}
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
  align-items: center;
  justify-content: center;
  margin: auto;
}

.A_box {
  height: 20vh;
  width: 40vw;
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
  height: 20vh;
  width: 40vw;
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

</style>