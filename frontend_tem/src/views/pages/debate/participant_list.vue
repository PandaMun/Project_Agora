<template>
  <div class="side-element">
    <div style="font-size: 20px">참가자 리스트</div>
    <div style="padding: 5px 1px" v-for="participantName in participant_list">
      <div>{{ participantName }}</div>
    </div>
  </div>
</template>

<script>
import {mapState, useStore} from "vuex";

export default {
  name: "participant_list",
  setup() {
    const store = useStore();
    return {store}
  },
  data() {
    return {}
  },
  computed: {
    ...mapState('debate', {middle_box: 'middle_box'}),
    ...mapState('debate', {participant_list: 'participant_list'})
  },
  mounted() {
  }

}
</script>

<style scoped>
.side-element {
  width: 400px;
  height: 92vh;
  background-color: #0B0B0B;
  color: whitesmoke;
  padding: 10px;
}


</style>