<template>
  <div class="modal-styles modal fade" id="editProfileImg" tabindex="-1" aria-labelledby="addpaymentMethod" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <div class="profile-name text-center">
            <h4>Profile Image</h4>
          </div>
        </div>
        <div class="modal-body">
                <img :src="'https://i8c205.p.ssafy.io/api/v1/users/images/' + userInfo.userEmail" alt="" class="img-fluid">

            </div>
            <div class="profile-group">
              <div class="go-dashboard text-center">
                <br>
                <h5>프로필 사진 불러오기</h5>
                <br>
                <input type="file"  @change="uploadImage" class="btn btn-outline-dark" >
              </div>
            </div>
      </div>
    </div>
  </div>
</template>

<!--<script>-->
<!--import axios from "axios";-->
<!--import VueSimpleAlert from "vue-simple-alert";-->
<!--import {useStore} from "vuex";-->
<!--import {useRouter} from "vue-router";-->
<!--import {reactive} from "vue";-->

<!--export default {-->

<!--  name: "editProfileImg",-->
<!--  setup() {-->
<!--    const store = useStore();-->
<!--    const userInfo = reactive({-->
<!--      username: store.getters["userStore/checkUserInfo"].name,-->
<!--      userEmail: store.getters["userStore/checkUserInfo"].userEmail,-->
<!--      position: store.getters["userStore/checkUserInfo"].position,-->
<!--      profile: store.state.userStore.isLogin,-->
<!--    });-->
<!--    return {userInfo};-->
<!--  },-->
<!--  methods:{-->
<!--    async uploadImage(event) {-->
<!--      try {-->
<!--        const api = axios.create({-->
<!--          // baseURL: process.env.VUE_APP_API_BASE_URL,-->
<!--          baseURL: "http://localhost:8082/api/v1",-->
<!--          // baseURL: "http://i8c205.p.ssafy.io:8082/api/v1",-->
<!--          headers: {-->
<!--          }-->
<!--        });-->
<!--        api.defaults.headers["authorization"] = "Bearer " +  sessionStorage.getItem("access-token");-->
<!--        const file = event.target.files[0];-->
<!--        const formData = new FormData();-->
<!--        formData.append('file', file);-->
<!--        const response = await api.patch('/users/profile', formData);-->
<!--        console.log(response)-->
<!--        await VueSimpleAlert.alert("변경에 성공하셨습니다.")-->
<!--        const route = useRouter();-->
<!--        await route.push("/setting-edit-profile");-->
<!--      } catch (error) {-->
<!--        console.error(error)-->
<!--      }-->


<!--    }-->
<!--  }-->

<!--}-->
<!--</script>-->
<script>
import axios from "axios";
import VueSimpleAlert from 'vue-simple-alert';
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive } from "vue";

export default {
  name: "editProfileImg",
  setup() {
    const store = useStore();
    const route = useRouter();
    const userInfo = reactive({
      username: store.getters["userStore/checkUserInfo"].name,
      userEmail: store.getters["userStore/checkUserInfo"].userEmail,
      position: store.getters["userStore/checkUserInfo"].position,
      profile: store.state.userStore.isLogin,
    });

    async function uploadImage(event) {
      try {
        const api = axios.create({
          // baseURL: process.env.VUE_APP_API_BASE_URL,
          baseURL: "https://i8c205.p.ssafy.io/api/v1",
          headers: {},
        });

        api.defaults.headers["authorization"] =
            "Bearer " + sessionStorage.getItem("access-token");
        const file = event.target.files[0];
        const formData = new FormData();
        formData.append("file", file);
        const response = await api.patch("/users/profile", formData);
        console.log(response);

        await VueSimpleAlert.alert("변경에 성공하셨습니다.");
        await location.reload();
      } catch (error) {
        console.error(error);
      }
    }

    return { userInfo, uploadImage };
  },
};
</script>
<style scoped>

</style>