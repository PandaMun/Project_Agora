<template>
  <div
      class="side-box"
      v-if="participant_list == true ||chat_box == true">
    <div class="box1" v-if="middle_box == true"></div>
    <div :class="[ middle_box == true ? 'side-el' : 'side-el-full' ]">
     <participant_list v-if="participant_list == true"></participant_list>
     <chat v-if="chat_box == true"></chat>
    </div>
    <div class="box2" v-if="middle_box == true"></div>

  </div>

</template>

<script>
import {mapState, useStore} from 'vuex';
import participant_list from "./participant_list.vue";
import chat from './chat.vue';
export default {
  name: "sidebox",
  components : { chat, participant_list },
  setup() {
    const store = useStore();
    return {store}
  },
  computed : {
    ...mapState('devate',{middle_box:'middle_box'}),
    ...mapState('devate',{participant_list:'participant_list'}),
    ...mapState('devate',{chat_box:'chat_box'}),


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