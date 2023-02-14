<template>
  <div class="main">
    <sidebox
        class="side-box">

    </sidebox>
    <documentbox></documentbox>
    <div class="main-wrapper">
      <debate2
      :call = "call">
      </debate2>
      <middlebox v-if="middle_box === true"></middlebox>
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
import {mapState, useStore} from 'vuex';
import Sidebox from "./sidebox.vue";
import Documentbox from "./documentbox.vue";
import debate2 from "./debate2.vue";


export default {
  components: {Documentbox, Sidebox, participant, middlebox, participant_list, chat, uibar, debate2},
  data() {
    return {
      call: ''
    }
  },
  setup() {
    const store = useStore()

    return {store}
  },
  computed: {
    ...mapState('debate', {participant_list: 'participant_list'}),
    ...mapState('debate', {chat_box: 'chat_box'}),
    ...mapState('debate', {middle_box: 'middle_box'}),
  },
  mounted() {
    console.log(this.$route.query)
    this.call = this.$route.query
    this.store.commit('debate/participantInfo', this.call)
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