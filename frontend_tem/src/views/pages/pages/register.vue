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
                <img src="../../../assets/img/Agora2.png" class="img-fluid" alt="Logo">
              <div class="back-home">
                <router-link to="/">홈으로</router-link>
              </div>
            </div>
            <h1> 회원가입 </h1>
            <form ref="SignupForm" :model="state.form" :rules="state.rules" :label-position="state.form.align">
              <div class="form-group">
                <label class="form-control-label">E-mail 아이디</label>
                <input v-model="state.form.userEmail" type="email" class="form-control" placeholder="Enter your email address">
              </div>
              <div class="form-group">
                <label class="form-control-label">비밀번호</label>
                <div class="pass-group" id="passwordInput">
                  <input v-model="state.form.password" type="password" class="form-control pass-input" placeholder="Enter your password">
                  <span class="toggle-password feather-eye"></span>
                  <span class="pass-checked"><i class="feather-check"></i></span>
                </div>
                <div class="password-strength" id="passwordStrength">
                  <span id="poor"></span>
                  <span id="weak"></span>
                  <span id="strong"></span>
                  <span id="heavy"></span>
                </div>
                <div id="passwordInfo"></div>
              </div>
              <div class="form-group">
                <label class="form-control-label">비밀번호 확인</label>
                <div class="pass-group" id="passwordCheckInput">
                  <input v-model="state.form.passwordCheck" type="password" class="form-control pass-input" placeholder="Enter your password check">
                  <span class="toggle-password feather-eye"></span>
                  <span class="pass-checked2"><i class="feather-check"></i></span>
                </div>
              </div>

              <div class="form-group">
                <label class="form-control-label">이름</label>
                <input v-model="state.form.name" type="text" class="form-control" placeholder="Enter your Full Name">
              </div>

              <div class="form-group">
                <label class="form-control-label">소속</label>
                <input v-model="state.form.department" type="text" class="form-control" placeholder="Enter your department">
              </div>

              <div class="form-group">
                <label class="form-control-label">학년</label>
                <input v-model="state.form.grade" type="number" class="form-control" placeholder="Enter your grade">
              </div>

              <div class="form-group">
                <label class="form-control-label">반</label>
                <input v-model="state.form.classNum" type="number" class="form-control" placeholder="Enter your classNum">
              </div>

              <div class="form-group">
                <label class="form-control-label">직위</label>
                <input v-model="state.form.position" type="text" class="form-control" placeholder="Enter your position">
              </div>

              <div class="form-check remember-me">
                <label class="form-check-label mb-0">
                  <input class="form-check-input" type="checkbox" name="remember"> I agree to the
                  <router-link to="term-condition">Terms of Service</router-link>
                  and
                  <router-link to="privacy-policy">Privacy Policy.</router-link>
                </label>
              </div>
            </form>
            <div class="d-grid">
              <button class="btn btn-primary btn-start" type="default" @click="clickSignUp" :disabled="state.form.isFormValid">Create Account</button>
            </div>

            <div class="google-bg text-center">
              <p class="mb-0"> 이미 계정이 있으신가요?
                <router-link to="login"> 로그인하기 </router-link>
              </p>
            </div>
          </div>
          <!-- /Login -->

        </div>

      </div>
    </div>
    <!-- /Main Wrapper -->
  </div>
</template>
<script>
import  Vue, { reactive, computed, ref, onMounted } from 'vue';
import { useStore } from 'vuex';
import http from '../../../api/http';
import {useRouter, useRoute} from 'vue-router';
import {apiInstance} from "../../../api";

export default {
  name: "register",
  components: {},
  props : {},
  setup(props) {
    const store = useStore();
    const api = apiInstance();
    const SignupForm = ref(null);
    const router = useRouter();
    const state = reactive({
      form : {
        userEmail : '',
        password : '',
        passwordCheck : '',
        name : '',
        department : '',
        grade : '',
        classNum : '',
        position : '',
        role : 'common',
        align: 'left',
        isFormValid: false,
      },
      rules : {
        department: [
          { required: false, message: 'Please input your department within 10 to 30 letters.', trigger: 'blur'},
          { max : 30, message: '최대 30자까지 입력 가능합니다.' },
          { min : 10, message: '최소 10자 이상 입력 하세요.' }
        ],
        position: [
          { required: false, message: 'Please input your position within 30 letters.', trigger: 'blur'},
          { max: 30, message: '최대 30자까지 입력 가능합니다.'}
        ],

      }
    })

    onMounted(() => {
      console.log(SignupForm.value);
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
      if ($('#passwordInput').length > 0) {
        let passwordInput = document.querySelector('#passwordInput input[type="password"]');
        let passwordStrength = document.getElementById('passwordStrength');
        let poor = document.querySelector('#passwordStrength #poor');
        let weak = document.querySelector('#passwordStrength #weak');
        let strong = document.querySelector('#passwordStrength #strong');
        let heavy = document.querySelector('#passwordStrength #heavy');
        let passwordInfo = document.getElementById('passwordInfo');
        let passcheck = document.querySelector('#passwordInput .pass-checked');
        let passcheck2 = document.querySelector('#passwordInput .pass-checked2');

        let poorRegExp = /[a-z]/;
        let weakRegExp = /(?=.*?[0-9])/;
        ;
        let strongRegExp = /(?=.*?[#?!@$%^&*-])/;

        let whitespaceRegExp = /^$|\s+/;


        passwordInput.oninput = function () {

          let passwordValue = passwordInput.value;
          let passwordLength = passwordValue.length;
          let poorPassword = passwordValue.match(poorRegExp);
          let weakPassword = passwordValue.match(weakRegExp);
          let strongPassword = passwordValue.match(strongRegExp);
          let whitespace = passwordValue.match(whitespaceRegExp);
          if (passwordValue !== "") {
            passwordStrength.style.display = "block";
            passwordStrength.style.display = "flex";
            passwordInfo.style.display = "block";
            passwordInfo.style.color = "black";
            if (whitespace) {
              passwordInfo.textContent = "whitespaces are not allowed";
            } else {
              poorPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
              weakPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
              strongPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
              heavyPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
            }

          } else {
            passwordInfo.style.display = "none";
            passwordStrength.classList.remove("poor-active");
            passwordStrength.classList.remove("avg-active");
            passwordStrength.classList.remove("strong-active");
            passwordStrength.classList.remove("heavy-active");
            passcheck.classList.remove("active");
            passcheck2.classList.remove("active");
          }
        }

        function poorPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword) {
          if (passwordLength < 8) {
            poor.classList.add("active");
            passwordStrength.classList.add("poor-active");
            passwordStrength.classList.remove("avg-active");
            passwordStrength.classList.remove("strong-active");
            passwordStrength.classList.remove("heavy-active");
            passwordInfo.style.display = "block";
            passwordInfo.style.color = "#FF0000";
            passwordInfo.innerHTML = "Weak. Must contain at least 8 characters";

          }
        }

        function weakPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword) {
          if (passwordLength >= 8 && (poorPassword || weakPassword || strongPassword)) {
            weak.classList.add("active");
            passwordStrength.classList.remove("poor-active");
            passwordStrength.classList.add("avg-active");
            passwordStrength.classList.remove("strong-active");
            passwordStrength.classList.remove("heavy-active");
            passwordInfo.style.display = "block";
            passwordInfo.style.color = "#FFB54A";
            passwordInfo.innerHTML = "Average. Must contain at least 1 letter or number";

          } else {
            weak.classList.remove("active");

          }
        }

        function strongPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword) {
          if (passwordLength >= 8 && poorPassword && (weakPassword || strongPassword)) {
            strong.classList.add("active");
            passwordStrength.classList.remove("avg-active");
            passwordStrength.classList.remove("poor-active");
            passwordStrength.classList.add("strong-active");
            passwordStrength.classList.remove("heavy-active");
            passwordInfo.innerHTML = "Almost. Must contain special symbol";
            passwordInfo.style.color = "#1D9CFD";

          } else {
            strong.classList.remove("active");

          }
        }

        function heavyPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword) {
          if (passwordLength >= 8 && (poorPassword && weakPassword) && strongPassword) {
            heavy.classList.add("active");
            passwordStrength.classList.remove("poor-active");
            passwordStrength.classList.remove("avg-active");
            passwordStrength.classList.remove("strong-active");
            passwordStrength.classList.add("heavy-active");
            passcheck.classList.add("active");
            passcheck2.classList.add("active");
            passwordInfo.innerHTML = "Awesome! You have a secure password.";
            passwordInfo.style.color = "#159F46";
          } else {
            heavy.classList.remove("active");
            passcheck.classList.remove("active");
            passcheck2.classList.remove("active");
          }
        }
      }


      if ($('#passwordInputs').length > 0) {

        let passwordInput1 = document.querySelector('#passwordInputs input[type="password"]');
        let passwordStrength1 = document.getElementById('passwordStrengths');
        let poor1 = document.querySelector('#passwordStrengths #poors');
        let weak1 = document.querySelector('#passwordStrengths #weaks');
        let strong1 = document.querySelector('#passwordStrengths #strongs');
        let heavy1 = document.querySelector('#passwordStrengths #heavys');
        let passwordInfos = document.getElementById('passwordInfos');
        let passcheck1 = document.querySelector('#passwordInputs .pass-checked');
        let passcheck3 = document.querySelector('#passwordInputs .pass-checked2');

        let poor1RegExp1 = /[a-z]/;
        let weak1RegExp1 = /(?=.*?[0-9])/;
        ;
        let strong1RegExp1 = /(?=.*?[#?!@$%^&*-])/;

        let whitespace1RegExp1 = /^$|\s+/;


        passwordInput1.oninput = function () {

          let passwordValue1 = passwordInput1.value;
          let passwordLength1 = passwordValue1.length;
          let poor1Password1 = passwordValue1.match(poor1RegExp1);
          let weak1Password1 = passwordValue1.match(weak1RegExp1);
          let strong1Password1 = passwordValue1.match(strong1RegExp1);
          let whitespace1 = passwordValue1.match(whitespace1RegExp1);
          if (passwordValue1 !== "") {
            passwordStrength1.style.display = "block";
            passwordStrength1.style.display = "flex";
            passwordInfos.style.display = "block";
            passwordInfos.style.color = "black";
            if (whitespace1) {
              passwordInfos.textContent = "whitespace1s are not allowed";
            } else {
              poor1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
              weak1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
              strong1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
              heavy1passwordStrength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
            }

          } else {
            passwordInfos.style.display = "none";
            passwordStrength1.classList.remove("poor-active");
            passwordStrength1.classList.remove("avg-active");
            passwordStrength1.classList.remove("strong-active");
            passwordStrength1.classList.remove("heavy-active");
            passcheck1.classList.remove("active");
            passcheck3.classList.remove("active");

          }
        }

        function poor1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1) {
          if (passwordLength1 < 8) {
            poor1.classList.add("active");
            passwordStrength1.classList.add("poor-active");
            passwordStrength1.classList.remove("avg-active");
            passwordStrength1.classList.remove("strong-active");
            passwordStrength1.classList.remove("heavy-active");
            passwordInfos.style.display = "block";
            passwordInfos.style.color = "#FF0000";
            passwordInfos.innerHTML = "weak1. Must contain at least 8 characters";

          }
        }

        function weak1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1) {
          if (passwordLength1 >= 8 && (poor1Password1 || weak1Password1 || strong1Password1)) {
            weak1.classList.add("active");
            passwordStrength1.classList.remove("poor-active");
            passwordStrength1.classList.add("avg-active");
            passwordStrength1.classList.remove("strong-active");
            passwordStrength1.classList.remove("heavy-active");
            passwordInfos.style.display = "block";
            passwordInfos.style.color = "#FFB54A";
            passwordInfos.innerHTML = "Average. Must contain at least 1 letter or number";

          } else {
            weak1.classList.remove("active");

          }
        }

        function strong1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1) {
          if (passwordLength1 >= 8 && poor1Password1 && (weak1Password1 || strong1Password1)) {
            strong1.classList.add("active");
            passwordStrength1.classList.remove("avg-active");
            passwordStrength1.classList.remove("poor-active");
            passwordStrength1.classList.add("strong-active");
            passwordStrength1.classList.remove("heavy-active");
            passwordInfos.innerHTML = "Almost. Must contain special symbol";
            passwordInfos.style.color = "#1D9CFD";

          } else {
            strong1.classList.remove("active");

          }
        }

        function heavy1passwordStrength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1) {
          if (passwordLength1 >= 8 && (poor1Password1 && weak1Password1) && strong1Password1) {
            heavy1.classList.add("active");
            passwordStrength1.classList.remove("poor-active");
            passwordStrength1.classList.remove("avg-active");
            passwordStrength1.classList.remove("strong-active");
            passwordStrength1.classList.add("heavy-active");
            passcheck1.classList.add("active");
            passcheck3.classList.add("active");
            passwordInfos.innerHTML = "Awesome! You have a secure password.";
            passwordInfos.style.color = "#159F46";
          } else {
            heavy1.classList.remove("active");
            passcheck1.classList.remove("active");
            passcheck3.classList.remove("active");
          }
        }

      }
    })

    const clickSignUp = function () {
      console.log("signup start")
      http.post(`/users`, {user_email: state.form.userEmail, password: state.form.password,
        department: state.form.department, position: state.form.position, name: state.form.name,
        grade: state.form.grade, classNum: state.form.classNum, role: state.form.role})
          .then(({ data }) => {
              console.log(data)
                if (data.message === "Success") {
                  alert("등록이 완료되었습니다.");
                  router.push('/login');
                }
              }
          ).catch(error => {
            alert(error.response.data.message)
      })
    }
    const isValid = () => {
      SignupForm.value.validate((valid) => {
        if(valid){
          console.log(valid);
          state.form.isFormValid = true;
          return true;
        } else {
          state.form.isFormValid = false;
          console.log(valid);
          return false;
        }
      })
    }

    return { SignupForm, state, clickSignUp, isValid };
  }
}
</script>