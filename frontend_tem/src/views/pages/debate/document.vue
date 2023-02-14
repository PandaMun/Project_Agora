<template>
  <div class="document">
    <div>
      <strong>김피먹</strong>
      <spsn>님이 </spsn>
      <a>파일</a>
      <span> 을/를 업로드 하였습니다.</span>
      <div>
        오후 04:20
      </div>
    </div>
  </div>
</template>

<script>
import {useStore} from 'vuex';

export default {
  name: "document",
  setup() {
    const store = useStore();
    return {store}
  },
  data() {
    return {
    }
  },
}
</script>

<style scoped>
.document {
  /*background: cornflowerblue;*/
  opacity: 0.8;
  border-radius: 0.1%;
  /*height: ;*/
}

</style>