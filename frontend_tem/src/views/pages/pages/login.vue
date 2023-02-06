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
                            <form action="instructor-dashboard">
                                <div class="form-group">
                                    <label class="form-control-label">Email</label>
                                    <input type="email" class="form-control" placeholder="이메일을 입력하세요">
                                </div>
                                <div class="form-group">
                                    <label class="form-control-label">Password</label>
                                    <div class="pass-group">
                                        <input type="password" class="form-control pass-input" placeholder="비밀번호를 입력하세요">
                                        <span class="feather-eye toggle-password"></span>
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
                                <div class="d-grid">
                                    <button class="btn btn-primary btn-start" type="submit">로그인</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="google-bg text-center">
                        <span><a href="javascript:void(0);">Or sign in with</a></span>
                        <div class="sign-google">
                            <ul>
                                <li><a href="javascript:void(0);"><img src="../../../assets/img/net-icon-01.png" class="img-fluid" alt="Logo"> Sign In using Google</a></li>
                                <li><a href="javascript:void(0);"><img src="../../../assets/img/net-icon-02.png" class="img-fluid" alt="Logo">Sign In using Facebook</a></li>
                            </ul>
                        </div>
                        <p class="mb-0">New User ? <router-link to="register">Create an Account</router-link></p>
                    </div>
                </div>
                <!-- /Login -->
                
            </div>
            
        </div>
    </div>
    <!-- /Main Wrapper -->
</template>
<script>
    import Vue from 'vue'
    export default {
      computed:{
           
        },
      data() {
            return {
                
               
            }
        },
        mounted() {
            if($('.toggle-password').length > 0) {
		$(document).on('click', '.toggle-password', function() {
			$(this).toggleClass("feather-eye feather-eye-off");
			var input = $(".pass-input");
			if (input.attr("type") == "password") {
				input.attr("type", "text");
			} else {
				input.attr("type", "password");
			}
		});
	}
        }
    }
</script>