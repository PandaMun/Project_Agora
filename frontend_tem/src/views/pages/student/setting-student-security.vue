<template>
    <!-- Main Wrapper -->
    <div class="main-wrapper">

        <layouts></layouts>

         <!--Dashbord Student -->
			<div class="page-content">
				<div class="container">
					<div class="row">

						<studentsidebar></studentsidebar>

						<!-- Student Security -->
						<div class="col-xl-9 col-md-8">
							<div class="settings-widget profile-details">
								<div class="settings-menu p-0">
									<div class="profile-heading">
										<h3>보안설정</h3>
										<p>비밀번호를 변경할 수 있습니다</p>
									</div>
<!--									<div class="checkout-form personal-address border-line">-->
<!--										<div class="personal-info-head">-->
<!--											<h4>Email Address</h4>-->
<!--											<p>Your current email address is <span>maxwell@example.com</span></p>-->
<!--										</div>-->
<!--										<form action="#">-->
<!--											<div class="new-address">-->
<!--												<div class="row">-->
<!--													<div class="col-lg-6">-->
<!--														<div class="form-group">-->
<!--															<label class="form-control-label">New email address</label>-->
<!--															<input type="text" class="form-control" placeholder="Enter your New email address">-->
<!--														</div>-->
<!--													</div>-->
<!--													<div class="profile-share d-flex ">-->
<!--														<button type="button" class="btn btn-success">Update</button>-->
<!--													</div>-->
<!--												</div>-->
<!--											</div>-->
<!--										</form>-->
<!--									</div>-->
									<div class="checkout-form personal-address">
										<div class="personal-info-head">
											<h4>비밀번호 변경</h4>
											<p>안전한 비밀번호로 내 정보를 보호하세요</p>
										</div>
										<div class="row">
											<div class="col-lg-6">
												<form action="#">
													<div class="form-group">
														<label class="form-control-label">현재 비밀번호</label>
														<input type="password" v-model="password" class="form-control" placeholder="현재 비밀번호">
													</div>
													<div class="form-group">
														<label class="form-control-label">새 비밀번호</label>
														<div class="pass-group" id="passwordInput">
															<input type="password" v-model="new_password" class="form-control pass-input" placeholder="새 비밀번호">
														</div>
														<div  class="password-strength" id="passwordStrength">
															<span id="poor"></span>
															<span id="weak"></span>
															<span id="strong"></span>
															<span id="heavy"></span>
														</div>
														<div id="passwordInfo"></div>
													</div>
													<div class="form-group">
														<label class="form-control-label">새 비밀번호 확인</label>
														<input type="password" class="form-control" v-model="new_password_check" placeholder="새 비밀번호 확인" >
													</div>
													<div class="update-profile save-password">
														<button type="button" class="btn btn-primary" @click="changePassword">비밀번호 변경</button>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- Student Security -->

					</div>
				</div>
			</div>
			<!-- /Dashbord Student -->

        <layouts1></layouts1>

    </div>
    <!-- /Main Wrapper -->
</template>
<script>
    import Vue from 'vue'
    import {apiInstance} from "../../../api";
    import VueSimpleAlert from "vue-simple-alert";

    export default {
      components: {
        
      },
      data() {
            return {
              password : '',
              new_password : '',
              new_password_check : '',
            }
        },

      methods: {
        changePassword() {
          if (this.new_password == this.new_password_check) {

          const api = apiInstance();
          api.defaults.headers.common['Authorization'] = 'Bearer ' + sessionStorage.getItem('access-token');
          api.patch('/users/password', {
            newPassword: this.new_password,
            nowPassword: this.password
          }).then(res => {
            VueSimpleAlert.alert("비밀번호가 변경되었습니다.")
          }).catch(error => {
            if (error.response.status == 409) {
              VueSimpleAlert.alert("다시 한번 확인해주세요.");
            }
          })

        }else{
            VueSimpleAlert.alert("재 설정할 비밀번호가 일치하지 않습니다.");
          }

      }
      },
        mounted() {
		 
            if($('#passwordInput').length > 0) {
	let passwordInput = document.querySelector('#passwordInput input[type="password"]');
    let passwordStrength= document.getElementById('passwordStrength');
    let poor = document.querySelector('#passwordStrength #poor');
    let weak = document.querySelector('#passwordStrength #weak');
    let strong = document.querySelector('#passwordStrength #strong');
    let heavy = document.querySelector('#passwordStrength #heavy');
    let passwordInfo = document.getElementById('passwordInfo');
    let passcheck = document.querySelector('#passwordInput .pass-checked');
  
    let poorRegExp = /[a-z]/;
    let weakRegExp = /(?=.*?[0-9])/;;
    let strongRegExp = /(?=.*?[#?!@$%^&*-])/;
  
	let whitespaceRegExp = /^$|\s+/;
 
	

    passwordInput.oninput= function(){
   
        let passwordValue= passwordInput.value;
        let passwordLength= passwordValue.length;
        let poorPassword= passwordValue.match(poorRegExp);
        let weakPassword= passwordValue.match(weakRegExp);
        let strongPassword= passwordValue.match(strongRegExp);
        let whitespace= passwordValue.match(whitespaceRegExp);
		if(passwordValue != ""){
			passwordStrength.style.display = "block";
			passwordStrength.style.display = "flex";
			passwordInfo.style.display = "block";
			passwordInfo.style.color = "black";
			if(whitespace)
			{
				passwordInfo.textContent = "whitespaces are not allowed";
			}
			else {
				poorPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
				weakPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
				strongPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
				heavyPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword);
			}
			
		}
		else {
			   passwordInfo.style.display = "none";
			   passwordStrength.classList.remove("poor-active");
			   passwordStrength.classList.remove("avg-active");
			   passwordStrength.classList.remove("strong-active");
			   passwordStrength.classList.remove("heavy-active");
			   passcheck.classList.remove("active");
			
		   }
		}
		
		function poorPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword){
			if(passwordLength < 8)
			{
			   poor.classList.add("active");
			   passwordStrength.classList.add("poor-active");
			   passwordStrength.classList.remove("avg-active");
			   passwordStrength.classList.remove("strong-active");
			   passwordStrength.classList.remove("heavy-active");
			   passwordInfo.style.display = "block";
			   passwordInfo.style.color = "#FF0000";
			   passwordInfo.innerHTML  = "Weak. Must contain at least 8 characters";
				  
			}
		}

		function weakPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword){
			if(passwordLength >= 8 && (poorPassword || weakPassword || strongPassword))
			{
			   weak.classList.add("active");
			   passwordStrength.classList.remove("poor-active");
			   passwordStrength.classList.add("avg-active");
			   passwordStrength.classList.remove("strong-active");
			   passwordStrength.classList.remove("heavy-active");
			   passwordInfo.style.display = "block";
			   passwordInfo.style.color = "#FFB54A";
			   passwordInfo.innerHTML = "Average. Must contain at least 1 letter or number";
				  
			}else{
			 weak.classList.remove("active");
			 
		   }
		}

		function strongPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword){
		   if(passwordLength>= 8 && poorPassword && (weakPassword || strongPassword))
			{
			 strong.classList.add("active");
			 passwordStrength.classList.remove("avg-active");
			 passwordStrength.classList.remove("poor-active");
			 passwordStrength.classList.add("strong-active");
			 passwordStrength.classList.remove("heavy-active");
			 passwordInfo.innerHTML = "Almost. Must contain special symbol";
			 passwordInfo.style.color = "#1D9CFD";
		   
		   }else{
			 strong.classList.remove("active");
			 
		   }
		}
		
		function heavyPasswordStrength(passwordLength, poorPassword, weakPassword, strongPassword){
		  if(passwordLength >= 8 && (poorPassword && weakPassword) && strongPassword)
			{
			 heavy.classList.add("active");
			 passwordStrength.classList.remove("poor-active");
			 passwordStrength.classList.remove("avg-active");
			 passwordStrength.classList.remove("strong-active");
			 passwordStrength.classList.add("heavy-active");
			 passcheck.classList.add("active");
			 passwordInfo.innerHTML = "Awesome! You have a secure password.";
			 passwordInfo.style.color = "#159F46";
		   }else{
			 heavy.classList.remove("active");
			 passcheck.classList.remove("active");
			 
		   }
		}
	}	
		
		
	if($('#passwordInputs').length > 0) {
		
	let passwordInput1 = document.querySelector('#passwordInputs input[type="password"]');
    let passwordStrength1= document.getElementById('passwordStrengths');
    let poor1 = document.querySelector('#passwordStrengths #poors');
    let weak1 = document.querySelector('#passwordStrengths #weaks');
    let strong1 = document.querySelector('#passwordStrengths #strongs');
    let heavy1 = document.querySelector('#passwordStrengths #heavys');
    let passwordInfos = document.getElementById('passwordInfos');
    let passcheck1 = document.querySelector('#passwordInputs .pass-checked');
  
    let poor1RegExp1 = /[a-z]/;
    let weak1RegExp1 = /(?=.*?[0-9])/;;
    let strong1RegExp1 = /(?=.*?[#?!@$%^&*-])/;
  
	let whitespace1RegExp1 = /^$|\s+/;
 
	

    passwordInput1.oninput= function(){
   
        let passwordValue1= passwordInput1.value;
        let passwordLength1= passwordValue1.length;
        let poor1Password1= passwordValue1.match(poor1RegExp1);
        let weak1Password1= passwordValue1.match(weak1RegExp1);
        let strong1Password1= passwordValue1.match(strong1RegExp1);
        let whitespace1= passwordValue1.match(whitespace1RegExp1);
		if(passwordValue1 != ""){
			passwordStrength1.style.display = "block";
			passwordStrength1.style.display = "flex";
			passwordInfos.style.display = "block";
			passwordInfos.style.color = "black";
			if(whitespace1)
			{
				passwordInfos.textContent = "whitespace1s are not allowed";
			}
			else {
				poor1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
				weak1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
				strong1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
				heavy1passwordStrength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1);
			}
			
		}
		else {
			   passwordInfos.style.display = "none";
			   passwordStrength1.classList.remove("poor-active");
			   passwordStrength1.classList.remove("avg-active");
			   passwordStrength1.classList.remove("strong-active");
			   passwordStrength1.classList.remove("heavy-active");
			   passcheck1.classList.remove("active");
			
		   }
		}
		
		function poor1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1){
			if(passwordLength1 < 8)
			{
			   poor1.classList.add("active");
			   passwordStrength1.classList.add("poor-active");
			   passwordStrength1.classList.remove("avg-active");
			   passwordStrength1.classList.remove("strong-active");
			   passwordStrength1.classList.remove("heavy-active");
			   passwordInfos.style.display = "block";
			   passwordInfos.style.color = "#FF0000";
			   passwordInfos.innerHTML  = "weak1. Must contain at least 8 characters";
				  
			}
		}

		function weak1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1){
			if(passwordLength1 >= 8 && (poor1Password1 || weak1Password1 || strong1Password1))
			{
			   weak1.classList.add("active");
			   passwordStrength1.classList.remove("poor-active");
			   passwordStrength1.classList.add("avg-active");
			   passwordStrength1.classList.remove("strong-active");
			   passwordStrength1.classList.remove("heavy-active");
			   passwordInfos.style.display = "block";
			   passwordInfos.style.color = "#FFB54A";
			   passwordInfos.innerHTML = "Average. Must contain at least 1 letter or number";
				  
			}else{
			 weak1.classList.remove("active");
			 
		   }
		}

		function strong1Password1Strength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1){
		   if(passwordLength1>= 8 && poor1Password1 && (weak1Password1 || strong1Password1))
			{
			 strong1.classList.add("active");
			 passwordStrength1.classList.remove("avg-active");
			 passwordStrength1.classList.remove("poor-active");
			 passwordStrength1.classList.add("strong-active");
			 passwordStrength1.classList.remove("heavy-active");
			 passwordInfos.innerHTML = "Almost. Must contain special symbol";
			 passwordInfos.style.color = "#1D9CFD";
		   
		   }else{
			 strong1.classList.remove("active");
			 
		   }
		}
		
		function heavy1passwordStrength1(passwordLength1, poor1Password1, weak1Password1, strong1Password1){
		  if(passwordLength1 >= 8 && (poor1Password1 && weak1Password1) && strong1Password1)
			{
			 heavy1.classList.add("active");
			 passwordStrength1.classList.remove("poor-active");
			 passwordStrength1.classList.remove("avg-active");
			 passwordStrength1.classList.remove("strong-active");
			 passwordStrength1.classList.add("heavy-active");
			 passcheck1.classList.add("active");
			 passwordInfos.innerHTML = "Awesome! You have a secure password.";
			 passwordInfos.style.color = "#159F46";
		   }else{
			 heavy1.classList.remove("active");
			 passcheck1.classList.remove("active");
			 
		   }
		}
		
	}	
        }
    }
</script>