<template>
    <!-- Main Wrapper -->
    <div class="main-wrapper">
        
        <div class="row">
			
            <loginbanner></loginbanner>
            
            <div class="col-md-6 login-wrap-bg">		
            
                <!-- Login -->
                <div class="login-wrapper">
                    <div class="loginbox">
                        <div class="img-logo">
                            <img src="../../../assets/img/logo.svg" class="img-fluid" alt="Logo">
                            <div class="back-home">
                                <router-link to="/">Back to Home</router-link>
                            </div>
                        </div>
                        <h1>Forgot Password ?</h1>
                        <div class="reset-password">
                            <p>Enter your email to reset your password.</p>
                        </div>
                            <div class="form-group">
                                <label class="form-control-label">Email</label>
                                <input type="email" class="form-control" v-model="state.userEmail" placeholder="Enter your email address">
                            </div>
                            <div class="d-grid">
                                <button class="btn btn-start" @click="sendEmail" type="submit">Submit</button>
                            </div>
                    </div>
                </div>
                <!-- /Login -->
                
            </div>
            
        </div>
       
    </div>
    <!-- /Main Wrapper -->
</template>

<script>
import { apiInstance } from "../../../api/index.js";
import Vue, {reactive, computed, ref, onMounted, watch} from 'vue';
import {useRouter, useRoute} from 'vue-router';


export default {
  setup(props) {
    const api = apiInstance();
    const router = useRouter();
    const state = reactive({
      userEmail: ''

    });
    const sendEmail = () => {
        if (state.userEmail != '') {
          console.log("sending email");
          api.post('/users/email',
              {
                userEmail: state.userEmail
              }).then(response => {
              movetoLogin();
          }).catch(error => {
            console.log(error)
            alert(error.response.data.message)
          })
        }
      }
      const movetoLogin = async () => {
        alert("이메일이 전송되었습니다." +
            "30분 내로 변경 가능합니다.")
        await router.push('/login');
      }
    return {state,sendEmail,movetoLogin};
    }
  }



</script>
