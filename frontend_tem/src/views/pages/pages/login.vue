<template>
  <!-- Main Wrapper -->
  <div class="main-wrapper">

    <div class="row">

      <loginbanner></loginbanner>

      <div class="col-md-6 login-wrap-bg">

        <!-- Login -->
        <div class="login-wrapper">
          <div class="loginbox">
            <div class="w-100">
              <div class="img-logo">
                <img src="../../../assets/img/logo.svg" class="img-fluid" alt="Logo">
                <div class="back-home">
                  <router-link to="/">홈으로</router-link>
                </div>
              </div>
              <h1>로그인하세요</h1>
              <form :model="state.form" :rules="state.rules" ref="loginForm" :label-position="state.form.align"
                    @keyup="isValid">
                <div class="form-group">
                  <label class="input-title" :class="{ 'title-danger': state.emailHasError }">Email</label>
                  <input type="email" class="form-control" v-model="state.form.userEmail"
                         :class="{ 'input-danger': state.emailHasError }" placeholder="이메일을 입력하세요">
                  <p v-show="state.valid.email" class="input-error">
                    이메일 주소를 정확히 입력해주세요.
                  </p>
                </div>
                <div class="form-group">
                  <label class="form-control-label" :class="{ 'title-danger': state.passwordHasError }">Password</label>
                  <div class="pass-group">
                    <input type="password" v-model="state.form.password" class="form-control pass-input"
                           :class="{ 'input-danger': state.emailHasError }" placeholder="비밀번호를 입력하세요">
                    <span class="feather-eye toggle-password"></span>
                    <p v-show="state.valid.password" class="input-error">
                      비밀번호를 정확히 입력해주세요.
                    </p>
                  </div>
                </div>
                <div class="forgot">
                  <span><router-link class="forgot-link" to="forgot-password">비밀번호를 잊어버렸어요</router-link></span>
                </div>
                <div class="remember-me">
                  <label class="custom_check mr-2 mb-0 d-inline-flex remember-me"> 기억해두기
                    <input type="checkbox" name="radio">
                    <span class="checkmark"></span>
                  </label>
                </div>
              </form>
              <div class="d-grid">
                <button class="btn btn-primary btn-start" type="primary" @click="clickLogin"
                        :disabled="state.form.isFormValid">로그인
                </button>
              </div>
            </div>
          </div>
          <div class="google-bg text-center">
            <span><a href="javascript:void(0);">Or sign in with</a></span>
            <div class="sign-google">
              <ul>
                <li><a href="javascript:void(0);"><img src="../../../assets/img/net-icon-01.png" class="img-fluid"
                                                       alt="Logo"> Sign In using Google</a></li>
                <li><a href="javascript:void(0);"><img src="../../../assets/img/net-icon-02.png" class="img-fluid"
                                                       alt="Logo">Sign In using Facebook</a></li>
              </ul>
            </div>
            <p class="mb-0">New User ?
              <router-link to="register">Create an Account</router-link>
            </p>
          </div>
        </div>
        <!-- /Login -->

      </div>

    </div>
  </div>
  <!-- /Main Wrapper -->
</template>
<script>
import Vue, {reactive, computed, ref, onMounted, watch} from 'vue';
import {useStore} from 'vuex';
import {useRouter, useRoute} from 'vue-router';

export default {
  name: 'login',
  components: {},
  props: {},
  setup(props) {
    const store = useStore();
    const loginForm = ref(null)
    const router = useRouter();
    const route = useRoute();
    const state = reactive({
      form: {
        userEmail: 'hrlim@naver.com',
        password: '1q2w3e4r!',
        align: 'left',
        isFormValid: false,
      },
      valid: {
        email: false,
        password: false,
      },
      emailHasError: false,
      passwordHasError: false,
      isLogin: store.getters["userStore/getIsLogin"],
    });

    onMounted(() => {
      console.log(loginForm.value);
      if ($('.toggle-password').length > 0) {
        $(document).on('click', '.toggle-password', function () {
          $(this).toggleClass("feather-eye feather-eye-off");
          var input = $(".pass-input");
          if (input.attr("type") === "password") {
            input.attr("type", "text");
          } else {
            input.attr("type", "password");
          }
        });
      }
    })

    watch(() => state.form.userEmail, function (value) {
      const validateEmail = /^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\.[A-Za-z0-9\\-]+/;
      if (!validateEmail.test(value) || !value) {
        state.valid.email = true
        state.emailHasError = true
      } else {
        state.valid.email = false
        state.emailHasError = false
      }
    })
    watch(() => state.form.password, (value) => {
      const validatePassword = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/
      if (!validatePassword.test(value) || !value) {
        state.valid.password = true
        state.passwordHasError = true
      } else {
        state.valid.password = false
        state.passwordHasError = false
      }
    })

    const clickLogin = async () => {
      console.log(loginForm.value);
      await store.dispatch("userStore/userConfirm", {
        user_email: state.form.userEmail,
        password: state.form.password
      })
      moveHome();
    }

    const moveHome = () => {
      router.push('/');
    }

    return {loginForm, state, clickLogin, moveHome}

  }
  ,
}
</script>