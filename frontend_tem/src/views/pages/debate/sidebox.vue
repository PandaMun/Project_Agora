<template>
  <div
      class="side-box"
      v-if="participant_list_btn === true ||chat_box === true">
<!--    <div class="box1" v-if="middle_box === true"></div>-->
<!--    <div :class="[ middle_box === true ? 'side-el-full' : 'side-el' ]">-->
    <div class="'side-el-full'">
     <participant_list v-if="participant_list_btn === true"></participant_list>
     <chat v-show="chat_box === true"></chat>
    </div>
    <div class="box2" v-if="middle_box === true"></div>

  </div>

</template>

<script>
import {mapState, useStore} from 'vuex';
import participant_list from "./participant_list.vue";
import chat from './chat.vue';
import Stomp from "webstomp-client";
import SockJS from 'sockjs-client'

export default {
  name: "sidebox",
  components : { chat, participant_list },
  setup() {
    const store = useStore();
    const chatSocket = store.state.debate.chatSocket;
    const stompClient = store.state.debate.stompClient;
    const chatList = store.state.debate.chatList;
    return {store, chatSocket, stompClient, chatList}
  },
  computed : {
    ...mapState('debate',{middle_box:'middle_box'}),
    ...mapState('debate',{participant_list_btn:'participant_list_btn'}),
    ...mapState('debate',{chat_box:'chat_box'}),
  },
  created() {
    // const serverURL = "https://i8c205.p.ssafy.io:8084/my-chat/"
    const serverURL = "https://i8c205.p.ssafy.io/my-chat/"
    this.chatSocket = new SockJS(serverURL);
    this.stompClient = Stomp.over(this.chatSocket);
    this.store.state.debate.chatSocket = this.chatSocket
    this.store.state.debate.stompClient = this.stompClient
    console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
    this.stompClient.connect(
        {},
        frame => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe("/topic/group/1", res => {
            console.log('구독으로 받은 메시지 입니다.', res.body);
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            let message = JSON.parse(res.body);
            let date = new Date(message.timestamp)
            date.setHours(date.getHours() + 9)
            message.timestamp = date.toLocaleTimeString();

            this.chatList.push(message)
          });
        },
        error => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
    );
  }

}
</script>

<style scoped>
.side-box {
  width: 400px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  position : absolute;
  right: 0;
}

.side-el {
  color: black;
  height: 72vh;
}

.side-el-full {
  color: black;
  height: 92vh;
}

.box1 {
  height: 20vh;
}
.box2 {
  height: 8vh;
}

</style>