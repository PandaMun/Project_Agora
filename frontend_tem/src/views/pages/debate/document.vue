<template>
  <div class="form-group">
    <label class="download-link attach-label" for="thumbnail">첨부 파일</label>
    <div>
      <input accept="image/jpeg, image/png" type="file" @change="uploadFile" id="thumbnail"
             class="btn btn-outline-dark download-link attach-input">
    </div>
    <div>
      <div v-for="file in state.debate.fileList">
        <a class="download-link attach-input" :href="file.fileDownloadUri">{{ file.fileName }}</a>
      </div>
    </div>
  </div>
</template>

<script>
import {useStore} from 'vuex';
import {onMounted, reactive, ref} from "vue";
import {apiInstance} from "../../../api/index";

export default {
  name: "document",
  setup() {
    const store = useStore();
    const api = apiInstance();
    const upload = ref(null);
    api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
    const state = reactive({
      debate: {
        id: 15,
        userEmail: "",
        role: "찬성",
        fileList: null,
      }
    });

    onMounted(() => {
      state.debate.id = store.getters["debate/getDebateId"];
      state.debate.userEmail = store.getters["userStore/checkUserInfo"].userEmail;
      // state.debate.role = store.getters.["debate/getParticipantInfo"].position;
    })

    onMounted(() => {
      console.log(state.debate.id);
      api.get("/files/list/" + state.debate.id)
          .then((data) => {
            state.debate.fileList = data.data;
          }).catch((error) => {
        console.log(error);
      })
    })

    const uploadFile = (event) => {
      const file = event.target.files[0];
      const formData = new FormData();
      formData.append('role', state.debate.role);
      formData.append('file', file);
      api.patch('/files/uploads/' + state.debate.id, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((data) => {
        api.get('/files/list/' + state.debate.id)
            .then((data) => {
              state.debate.fileList = data.data;
              document.getElementById("thumbnail").value = "";
            }).catch((error) => {
          console.log("show list error : " + error);
        })
      }).catch((error) => {
        console.log("file upload error : " + error);
      });
    }
    return {store, state, uploadFile}
  },
  data() {
    return {}
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

.download-link {
  color: white;
  opacity: 1.0;
}

.attach-label {
  margin-top: 20px;
  margin-left: 20px;
}

.attach-input {
  margin-left: 20px;
}

</style>