<template>
  <div id="container">
    <div id="wrapper">
      <div id="join" class="animate join">
        <h1>Join a Room</h1>
        <form v-on:submit.prevent>
          <h4> 방 생성 정보 </h4>
          <p>
            <input type="text" name="title" id="title" v-model="data.title"
                   placeholder="Debate Title" required>
          </p>
          <p>
            <input type="text" name="roomType" id="roomType" v-model="data.roomType"
                   placeholder="Room Type" required>
          </p>
          <p>
            <input type="number" name="time" id="time" v-model="data.time"
                   placeholder="time" required>
          </p>
          <h4>방 입장 정보</h4>
          <p>
            <input type="text" name="debateId" id="debateId" v-model="data.debateId"
                   placeholder="DebateId" required>
          </p>
          <p>
            <input type="text" name="user_Name" id="userName" v-model="data.userName"
                   placeholder="Username" required>
          </p>
          <p>
            <input type="text" name="position" id="position" v-model="data.position"
                   placeholder="Position" required>
          </p>
          <button type="submit" name="commit" value="Join!" @click="connect" >Join</button>
        </form>
      </div>
      <div id="room" >
        <h2 id="room-header"></h2>
        <div id="participants">
          <div id="participants-agree"></div>
          <div id="moderator"></div>
          <div id="participants-opp" style="float: right;"></div>
        </div>
        <div id="timer" style="font-size: 20px"></div>
        <div id="screen"></div>
        <div id="buttons">
          <input type="button" id="button-start" onmouseup="start();"
                 value="start">
          <input type="button" id="button-stop" onmouseup="stop();"
                 value="stop">
          <input type="button" id="button-share-on" onmouseup="shareScreen()"
                 value="share screen on">
          <input type="button" id="button-share-off" onmouseup="stopShareScreen()" style="display: none"
                 value="share screen off">
          <input type="button" id="vidOn" onmouseup="videoOnOff()" style="display: none"
                 value="video On">
          <input type="button" id="vidOff" onmouseup="videoOnOff()"
                 value="video Off">
          <input type="button" id="audOn" onmouseup="audioOnOff()" style="display: none"
                 value="Audio On">
          <input type="button" id="audOff" onmouseup="audioOnOff()"
                 value="Audio Off">
          <input type="button" id="button-system-comment" onmouseup="sendSystemComment()"
                 value="system comment">
          <button type="submit" name="button-leave" value="Leave room" @click="leaveRoom" >Leave</button>

        </div>
      </div>

    </div>
  </div>
  {{this.$data}}
</template>
<script>
import room from "../../../assets/js/conferenceroom.js";

import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
//   name: "debate",
//   data() {
//     return {
//       data : {
//       title : "",
//       roomType:"",
//       time : "",
//       debateId: "",
//       userName: "",
//       position : ""
//       },
//
//     }
//   },
//   mounted() {
//       room.connect();
//   },
//   methods: {
//     connect() {
//       room.registerRoom(this.data);
//     },
//
//     leaveRoom() {
//       room.leaveRoom();
//     },
//     muteAudio() {
//       room.muteAudio(this.data.userName)
//     }
//   }
}
</script>