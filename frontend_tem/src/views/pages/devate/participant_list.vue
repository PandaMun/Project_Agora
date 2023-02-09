<template>
  <div class="side-element">
    참가자 리스트
  </div>
</template>

<script>
import {mapState, useStore} from "vuex";

export default {
  name: "participant_list",
  setup() {
    const store = useStore();
    return { store }
  },
  data() {
    return {
    }
  },
  computed : {
    ...mapState('devate',{middle_box:'middle_box'}),

  },
  mounted() {
  }

}
</script>

<style scoped>
.side-element {
  width: 400px;
  height: 100%;
  background-color: #0B0B0B;
  color: whitesmoke;
  padding: 10px;
}


</style>