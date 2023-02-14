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
                <img :src="imageUrl" alt="" class="img-fluid">

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

<script>
import { apiInstance } from "../../../api/index.js";
import {findById} from "../../../api/User";
import axios from "axios";

export default {

  name: "editProfileImg",
  data(){
    return{
      imageUrl : ""
    }
  },
  mounted() {
    findById(data =>{
      console.log(data)
          if(data.data.imageUrl != null) {
            this.imageUrl = data.data.imageUrl;
            console.log(this.imageUrl)
          }else if(data.data.imageUrl == null){
            this.imageUrl = '../../../assets/img/user/female.png';
          }
        },
        data =>{
          alert("정보를 불러올수 없습니다.")
        })
  },
  methods:{
    async uploadImage(event) {
      try {
        const api = axios.create({
          // baseURL: process.env.VUE_APP_API_BASE_URL,
          // baseURL: "http://localhost:8082/api/v1",
          baseURL: "http://i8c205.p.ssafy.io:8082/api/v1",
          headers: {
          }
        });

        api.defaults.headers["authorization"] = "Bearer " +  sessionStorage.getItem("access-token");
        const file = event.target.files[0];
        const formData = new FormData();
        formData.append('file', file);
        const response = await api.patch('/users/profile', formData);
        this.form.profilePicture = response.data.imageUrl;
      } catch (error) {
        console.error(error)
      }


    }
  }

}
</script>

<style scoped>

</style>