<template>
  <el-dialog custom-class="signup-dialog" title="회원가입" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="SignupForm" :label-position="state.form.align">
      <el-form-item prop="department" label="소속" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.department" autocomplete="off" placeholder="Please enter your department"></el-input>
      </el-form-item>
      <el-form-item prop="position" label="직책" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.position" autocomplete="off" placeholder="Please enter your position"></el-input>
      </el-form-item>
      <el-form-item prop="name" label="이름" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.name" autocomplete="off" placeholder="Please enter your name"></el-input>
      </el-form-item>
      <el-form-item prop="user_id" label="아이디" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.user_id" autocomplete="off" placeholder="Please enter id"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="비밀번호" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password" autocomplete="off" show-password placeholder="Please enter password"></el-input>
      </el-form-item>
      <el-form-item prop="password2" label="비밀번호확인" :label-width="state.formLabelWidth">
        <el-input v-model="state.form.password2" autocomplete="off" show-password placeholder="Please enter password again"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="default" @click="clickSignUp" :disabled="state.form.isFormValid">회원가입</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref, onMounted } from 'vue'
import { useStore } from 'vuex'
import { requestSignup } from "../../../common/api/accountAPI";

export default {
  name: "signup-dialog",
  props: {
    open: {
      type: Boolean,
      default: false
    }
  },
  setup(props, {emit}) {
    const store = useStore()
    const SignupForm = ref(null)
    const state = reactive( {
      form: {
        department: '',
        position: '',
        name: '',
        user_id: '',
        password : '',
        password2 : '',
        align: 'left',
        isFormValid: false

      },
      rules: {
        department: [
          { required: false, message: 'Please input your department within 10 to 30 letters.', trigger: 'blur'},
          { max : 30, message: '최대 30자까지 입력 가능합니다.' },
          { min : 10, message: '최소 10자 이상 입력 하세요.' }
        ],
        position: [
          { required: false, message: 'Please input your position within 30 letters.', trigger: 'blur'},
          { max: 30, message: '최대 30자까지 입력 가능합니다.'}
        ],
        name: [
          { required: true, message: 'Please input your name within 30 letters', trigger: 'blur' }
        ],
        user_id: [
          { required: true, message: 'Please input ID', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please input password', trigger: 'blur'},
          { min: 9, message: '최소 9글자를 입력해야 합니다.', trigger: 'blur' },
          { max: 16, message: '최대 16자까지 입력 가능합니다.', trigger: 'blur' },
          { pattern: new RegExp(/^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{9,16}$/), message: '비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다.', trigger: 'blur' },
        ],
        password2: [
          { required: true, message: 'Please input password again', trigger: 'blur' }
        ]
      },
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px'
    })

    onMounted(() => {
      console.log(SignupForm.value)
    })

    const clickSignUp = function () {
      console.log("signup start")
      // 로그인 클릭 시 validate 체크 후 그 결과 값에 따라, 로그인 API 호출 또는 경고창 표시
      SignupForm.value.validate(async (valid) => {
        if (state.form.password !== state.form.password2) {
          alert('Those passwords are different')
          return
        }
        if (valid) {
          console.log('valid-signup')
          const response = await requestSignup({user_id: state.form.user_id, password: state.form.password,
            department: state.form.department, position: state.form.position, name: state.form.name})
          if(response.data.message == "Success") {
            console.log(response)
            alert('회원가입이 성공하였습니다.')
            handleClose();
          }else{
            alert('회원가입이 실패하였습니다.')
          }
        } else {
          alert('Validate error!')
        }
      })
    }
    const handleClose = function () {
      state.form.department = ''
      state.form.position = ''
      state.form.name = ''
      state.form.user_id = ''
      state.form.password = ''
      state.form.password2 = ''
      emit('closeSignupDialog')
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

    return { SignupForm, state, clickSignUp, handleClose, isValid }

  }
};
</script>

<style scoped>

</style>
