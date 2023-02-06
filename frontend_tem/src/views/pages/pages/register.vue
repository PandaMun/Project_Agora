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
                        <h1>Sign up</h1>
                        <form action="login">
                            <div class="form-group">
                                <label class="form-control-label">Full Name</label>
                                <input type="text" class="form-control" placeholder="Enter your Full Name">
                            </div>
                            <div class="form-group">
                                <label class="form-control-label">Email</label>
                                <input type="email" class="form-control" placeholder="Enter your email address">
                            </div>
                            <div class="form-group">
                                <label class="form-control-label">Password</label>
                                <div class="pass-group" id="passwordInput">																	
                                    <input type="password" class="form-control pass-input" placeholder="Enter your password">
                                    <span class="toggle-password feather-eye"></span>
                                    <span class="pass-checked"><i class="feather-check"></i></span>
                                </div>
                                <div  class="password-strength" id="passwordStrength">
                                    <span id="poor"></span>
                                    <span id="weak"></span>
                                    <span id="strong"></span>
                                    <span id="heavy"></span>
                                </div>
                                <div id="passwordInfo"></div>	
                            </div>

                            <div class="form-check remember-me">
                                <label class="form-check-label mb-0">
                                  <input class="form-check-input" type="checkbox" name="remember"> I agree to the <router-link to="term-condition">Terms of Service</router-link> and <router-link to="privacy-policy">Privacy Policy.</router-link>
                                </label>
                            </div>
                            <div class="d-grid">
                                <button class="btn btn-primary btn-start" type="submit">Create Account</button>
                            </div>
                        </form>
                    </div>
                    <div class="google-bg text-center">
                        <span><a href="javascript:void(0);">Or sign in with</a></span>
                        <div class="sign-google">
                            <ul>
                                <li><a href="javascript:void(0);"><img src="../../../assets/img/net-icon-01.png" class="img-fluid" alt="Logo"> Sign In using Google</a></li>
                                <li><a href="javascript:void(0);"><img src="../../../assets/img/net-icon-02.png" class="img-fluid" alt="Logo">Sign In using Facebook</a></li>
                            </ul>
                        </div>
                        <p class="mb-0">Already have an account? <router-link to="login">Sign in</router-link></p>
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
      components: {
        
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