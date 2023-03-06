<template>
    <div class="main-wrapper">
        <layouts></layouts>
       <div class="page-content">
				<div class="container">
					<div class="row">
						
						<studentsidebar></studentsidebar>

						<div class="col-xl-9 col-md-8">	
							<div class="settings-widget profile-details">
								<div class="settings-menu p-0">
									<div class="profile-heading">
										<h3>프로필</h3>
										<p>자유롭게 프로필을 꾸며보세요</p>
									</div>
									<div class="course-group mb-0 d-flex">
										<div class="course-group-img d-flex align-items-center">
											<router-link to="student-profile"><img :src="'https://i8c205.p.ssafy.io/api/v1/users/images/' + userInfo.userEmail" alt="" class="img-fluid"></router-link>
                      <div class="course-name">
												<h4><router-link to="student-profile">프로필사진</router-link></h4>
												<p>800px 이하의 png, jpg 파일이 허용됩니다.</p>
											</div>
										</div>
										<div class="profile-share d-flex align-items-center justify-content-center">
											<a href="javascript:;" data-bs-toggle="modal" data-bs-target="#editProfileImg" class="btn btn-success">수정</a>
											<a href="javascript:;" class="btn btn-danger">삭제</a>
										</div>
									</div>
									<div class="checkout-form personal-address add-course-info ">
										<div class="personal-info-head">
											<h4>개인 정보</h4>
											<p>개인 정보를 수정할 수 있습니다</p>
										</div>
										<form action="#">
											<div class="row">
                        <div class="col-lg-6">
                          <div class="form-group">
                            <label class="form-control-label">Email</label>
                            <input type="text" class="form-control"  v-model="user.userEmail" placeholder="Email을 입력하세요"  readonly onfocus="this.blur();">
                          </div>
                        </div>
												<div class="col-lg-6">
													<div class="form-group">
														<label class="form-control-label">학교</label>
														<input type="text" class="form-control" v-model="user.department" placeholder="학교를 입력하세요">
													</div>
												</div>
												<div class="col-lg-6">
													<div class="form-group">
														<label class="form-control-label">학년</label>
														<input type="text" class="form-control" v-model="user.grade" placeholder="학년을 입력하세요">
													</div>
												</div>
												<div class="col-lg-6">
													<div class="form-group">
														<label class="form-control-label">반</label>
														<input type="text" class="form-control" v-model="user.classNum" placeholder="반을 입력하세요">
													</div>
												</div>
                        <div class="col-lg-6">
                          <div class="form-group">
                            <label class="form-control-label">직위</label>
                            <input type="text" class="form-control" v-model="user.position" placeholder="직위" readonly onfocus="this.blur();">
                          </div>
                        </div>
												<div class="update-profile">
													<button type="button" class="btn btn-primary" @click = changeUserInfo>프로필 수정</button>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>	
						<!-- Profile Details -->
						
					</div>
				</div>
			</div>
        <layouts1></layouts1>
    </div>
  <editProfileImg></editProfileImg>
</template>
<script>
import { apiInstance } from "../../../api/index.js";
import {findById} from "../../../api/User";
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {reactive} from "vue";

    export default {
      setup() {
        const store = useStore();
        const router = useRouter();
        const userInfo = reactive({
          username: store.getters["userStore/checkUserInfo"].name,
          userEmail: store.getters["userStore/checkUserInfo"].userEmail,
          position: store.getters["userStore/checkUserInfo"].position,
          profile: store.state.userStore.isLogin,
        });
        return {userInfo};
      },
      data() {
            return {
              user: {
                userEmail: '',
                name: '',
                department: '',
                grade: '',
                classNum: '',
                position: '',
                profileUrl:'',
              },
            }
        },
      methods:{
        async changeUserInfo() {
          console.log(this.user)
          if(this.user.userEmail != null && this.user.name != null && this.user.department != null
               && this.user.grade!= null && this.user.classNum != null) {
            const api = apiInstance();
            api.defaults.headers["authorization"] = "Bearer " +  sessionStorage.getItem("access-token");
            await api.patch("/users/info", this.user)
                .then(response => {
                  if (response.status == 200) {
                    alert("회원정보 변경에 성공하셨습니다.");
                  } else if (response.status == 401) {
                    this.$router.push('/error-404')
                  } else if (response.status == 500) {
                    this.$router.push('/error-500')
                  }
                }).catch(error =>{
                  console.log(error)
                });
          } else{
            alert("빈칸을 채워주세요")
          }
        }
      },
        mounted() {
          findById(data =>{
                console.log(data)
                this.user.userEmail = data.data.userEmail;
                this.user.name = data.data.name;
                this.user.department = data.data.department;
                this.user.grade = data.data.grade;
                this.user.classNum = data.data.classNum;
                this.user.position = data.data.position;
          },
          data =>{
            if(data.data.statusCode == 401){
              alert("토큰이 만료")
            }
          })
        },
    }
</script>
