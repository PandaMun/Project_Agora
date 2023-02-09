<template>
  <div class="side-element" >
    여기에 채팅 컴포넌트
  </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
  name: "chat",
  setup() {
    const store = useStore();
    return { store }
  },



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