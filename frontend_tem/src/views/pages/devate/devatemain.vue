<template>
  <div class="main">
      <sidebox class="side-box"></sidebox>
    <div class="main-wrapper">
      <participant></participant>
      <middlebox v-if="middle_box == true"></middlebox>
    </div>
      <uibar></uibar>
  </div>
</template>

<script>
import participant from "./participant.vue";
import middlebox from "./middlebox.vue";
import uibar from "./uibar.vue";
import participant_list from './participant_list.vue';
import chat from './chat.vue';
import { mapState } from 'vuex';
import Sidebox from "./sidebox.vue";


export default{
    components: {Sidebox, participant, middlebox, participant_list, chat, uibar},
  data() {
      return {
      }
  },
    computed: {
        ...mapState('devate',{participant_list:'participant_list'}),
        ...mapState('devate',{chat_box:'chat_box'}),
        ...mapState('devate',{middle_box:'middle_box'}),

  },
  mounted() {

  }


}
</script>

<style scoped>
.main {
  height: 100vh;
}

.main-wrapper {
  display: flex;
  flex-direction: column;
  overflow: hidden;
  height: 92vh;
  width: 100%;
  background-image: url("../../../assets/img/banner.png");
}

@media screen and (max-width: 800px) {
.side-box {
  display: none;
}
}


</style>