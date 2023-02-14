<template>
  <div class="document-box" v-if="document_box">
    <document v-show="document_box"></document>
  </div>

</template>

<script>
import {mapState, useStore} from 'vuex';
import document from "./document.vue";

export default {
  name: 'documentbox',
  components: {document},
  setup() {
    const store = useStore();
    return {store}
  },
  computed: {
    ...mapState('debate', {document_box: 'document_box'}),
  },
}
</script>

<style scoped>
.document-box {
  bottom: 100px;
  width: 300px;
  height: 400px;
  display: flex;
  left: 36.5%;
  position: absolute;
  background: black;
  color: whitesmoke;
  opacity: 0.6;
  border-radius: 5%;
}

.document-box:after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 0;
  border: 20px solid transparent;
  border-top-color: black;
  border-bottom: 0;
  margin-left: -20px;
  margin-bottom: -20px;
}

</style>