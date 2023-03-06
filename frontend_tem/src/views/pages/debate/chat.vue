<template>
  <div class="side-element">
      <div class="chatList" v-for="(m, idx) in chatList" :key="idx">
        <div class="chatItem msg-box">
          <div class="msg-bg">
            <strong class="name">{{ m.author }}</strong>
            <span class="comment">{{ m.content }}</span>
          </div>
          <ul class="chat-time" >
            <li style="list-style: none; float: right">{{ m.timestamp }}</li>
          </ul>
        </div>
      </div>
    <div id="sendBar" class="input-group">
      <input v-model="content" @keyup.enter="sendMessage" type="text" class="input-msg-send form-control" placeholder="Type your message here...">
      <button @click="sendMessage" type="button" class="btn btn-primary msg-send-btn rounded-pill"><img src="../../../assets/img/send-icon.svg" alt="" ></button>
    </div>
  </div>
</template>

<script>
import {useStore} from 'vuex';
import Axios from 'axios';

export default {
  name: "chat",
  setup() {
    const store = useStore();
    const chatSocket = store.state.debate.chatSocket
    const stompClient = store.state.debate.stompClient
    const chatList = store.state.debate.chatList
    const api = Axios.create({
      baseURL: "https://i8c205.p.ssafy.io:8084/kafka",
    });
    const participantInfo = store.state.debate.participantInfo
    return {store, chatSocket, stompClient, chatList, api, participantInfo}
  },
  data() {
    return {
      content: '',
    }
  },
  methods: {
    sendMessage() {
      console.log('click send')
      console.log(this.content)
      console.log(this.content.trim())
      if (this.content.trim() !== '' && this.stompClient !== null) {
        let chatMessage = {
          'content': this.content,
          'roomId': this.participantInfo.debateId,
          'author': this.participantInfo.name
        }
        console.log(chatMessage)
        // this.stompClient.send("/pub/message", JSON.stringify(chatMessage), {})
        this.api.post(`/publish`, chatMessage, {
          headers: { "Content-Type": "application/json" },
        });
        this.content = ''
      }
    }
  }
}
</script>

<style scoped>
.side-element {
  width: 400px;
  height: 92vh;
  background-color: whitesmoke;
  padding: 10px;
  overflow-y:auto;
  display: flex;
  flex-direction: column;
}

.chatItem {
  padding: 2px 5px;
}

.name {
  padding: 0 5px;
}

.comment {
  padding: 0 5px;
}

#sendBar {
  width: 100%;
  position: absolute;
  bottom: 80px;
  padding: 5px 15px 5px 0;
  background: whitesmoke;

}

.msg-box > div {
  background-color: #FF875A;
  padding: 10px;
  border-radius: 10px;
  display: inline-block;
  margin-bottom: 5px;
}

.msg-box > div strong {
  color: #fff;
}

.msg-box > div span {
  color: #fff;
}

</style>