<template>
  <div class="checkout-form personal-address">
    <div class="personal-info-head">
      <h4>비밀번호 변경</h4>
      <p>안전한 비밀번호로 내 정보를 보호하세요</p>
    </div>
    <div class="row">
      <div class="col-lg-6">
        <form action="#">

          <div class="form-group">
            <!--														<label class="form-control-label">새 비밀번호</label>-->
            <div class="pass-group" id="passwordInput">
              <input type="password" class="form-control pass-input" placeholder="새 비밀번호" v-model=password>
            </div>
            <div id="passwordInfo"></div>
          </div>

          <div class="form-group">
            <input type="password" class="form-control" placeholder="새 비밀번호 확인" v-model=passwordConfirm>
          </div>
          <div class="update-profile save-password">
            <button type="button" class="btn btn-primary" @click="changePasswordByEmail">비밀번호 변경</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { apiInstance } from "../../../api/index.js";

export default {
  name: "change-password-email.vue",
  data(){
    return{
      token : null,
      password : null,
      passwordConfirm : null,
    }
    },
  computed:{

  },
  methods:{
    async changePasswordByEmail() {
      if(this.password != null && this.password == this.passwordConfirm) {
        const api = apiInstance();
        api.defaults.headers["authorization"] = "Bearer " + this.token;
        await api.patch("/users/email/password", { password : this.password})
            .then(response => {
              if (response.status == 200) {
                alert("비밀번호 변경에 성공하셨습니다.")
                this.$router.push('/')
              } else if (response.status == 404) {
                this.$router.push('/error-404')
              } else if (response.status == 500) {
                this.$router.push('/error-500')
              }
            });
      } else if(this.password == null || this.passwordConfirm == null) {
        alert("비밀번호를 입력해 주세요")
      }else if(this.password != this.passwordConfirm){
        alert("비밀번호가 같지 않습니다.")
      }
    }
  },
  mounted() {
    this.token = this.$route.query.token;
    console.log("token  " +this.token)
    if(this.token == null || this.token == ""){
      alert("잘못된 링크입니다.");
      this.$router.push('/')
    }
  }

}
</script>

<style scoped>

</style>


function changePasswordByEmail(token, success, fail) {
api.defaults.headers["authorization"] = "Bearer " + token;
api.patch(`/users/password`).then(success).catch(fail);
}