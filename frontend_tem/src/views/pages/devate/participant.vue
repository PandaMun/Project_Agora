<template>
    <div
        :class="[middle_box == true ? 'participant-box' : 'participant-box_2']">
        <div :class="[middle_box == true ? 'A_box' : 'A_box_2']"><teamA></teamA></div>
        <div class="moderator"><moderator></moderator></div>
        <div :class="[middle_box == true ? 'B_box' : 'B_box_2']"><teamB></teamB></div>
    </div>
</template>

<script>
import moderator from "./moderator.vue";
import teamA from "./teamA.vue";
import teamB from "./teamB.vue";
import {mapState, useStore} from "vuex";
export default {
  name: 'participant',
  components: { moderator, teamA, teamB },
  setup() {
    const store = useStore();
    return { store }
  },
  data() {
    return {
    }
  },
  computed : {
    ...mapState('devate',{ middle_box: 'middle_box' })
  },
  mounted() {
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