<template>
<!-- sidebar -->
<div class="col-xl-3 col-md-4 theiaStickySidebar">
    <div class="stickysidebar">
        <div class="settings-widget dash-profile mb-3">
            <div class="settings-menu p-0">
                <div class="profile-bg">
                    <h5>Student여기에 직위</h5>
                    <img src="../views/pages/student/문태호.jpg" alt="">
                    <div class="profile-img">
                        <img src="../views/pages/student/문태호.jpg" alt="">
                    </div>
                </div>
                <div class="profile-group">
                    <div class="profile-name text-center">
                        <h4><router-link to="student-profile">여기에 학생 이름</router-link></h4>
                        <p>직위</p>
                    </div>
                    <div class="go-dashboard text-center">
                        <router-link to="deposit-student-dashboard" class="btn btn-primary">내 정보 보기</router-link>
                    </div>
                </div>
            </div>
        </div>
        <div class="settings-widget account-settings">
            <div class="settings-menu">
                <h3>내 정보 설정</h3>
                <ul>
                    <li class="nav-item">
                      <router-link :class="currentPath == 'instructor-dashboard' ? 'active' : 'notactive'" to="instructor-dashboard" class="nav-link">
                        <i class="feather-user-plus"></i> 나의 발자취?
                      </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="currentPath == 'instructor-course' ? 'active' : 'notactive'" to="instructor-course" class="nav-link">
                            <i class="feather-user-plus"></i> 내 토론 목록
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="currentPath == 'setting-edit-profile' ? 'active' : 'notactive'" to="setting-edit-profile" class="nav-link">
                            <i class="feather-settings"></i> 프로필 수정
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="currentPath == 'setting-student-security' ? 'active' : 'notactive'" to="setting-student-security" class="nav-link">
                            <i class="feather-user"></i> 보안 설정
                        </router-link>
                    </li>
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-social-profile' ? 'active' : 'notactive'" to="setting-student-social-profile" class="nav-link">-->
<!--                            <i class="feather-refresh-cw"></i> Social Profiles-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-notification' ? 'active' : 'notactive'" to="setting-student-notification" class="nav-link">-->
<!--                            <i class="feather-bell"></i> Notifications-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-privacy' ? 'active' : 'notactive'" to="setting-student-privacy" class="nav-link">-->
<!--                            <i class="feather-lock"></i> Profile Privacy-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-accounts' ? 'active' : 'notactive'" to="setting-student-accounts" class="nav-link">-->
<!--                            <i class="feather-user"></i> Linked Accounts-->
<!--                        </router-link>-->
<!--                    </li>-->
                    <li class="nav-item">
                        <router-link :class="currentPath == 'login' ? 'active' : 'notactive'" to="login" class="nav-link">
                            <i class="feather-power"></i> 로그아웃
                        </router-link>
                    </li>
                    <li class="nav-item">
                        <router-link :class="currentPath == 'setting-student-delete-profile' ? 'active' : 'notactive'" to="setting-student-delete-profile" class="nav-link">
                            <i class="feather-trash-2"></i> 회원 탈퇴
                        </router-link>
                    </li>
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-support-tickets' ? 'active' : 'notactive'" to="setting-support-tickets" class="nav-link">-->
<!--                            <i class="feather-clipboard"></i> Support Tickets-->
<!--                        </router-link>-->
<!--                    </li>-->
                </ul>
<!--                <h3>SUBSCRIPTION</h3>-->
<!--                <ul>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-subscription' ? 'active' : 'notactive'" to="setting-student-subscription" class="nav-link ">-->
<!--                            <i class="feather-calendar"></i> My Subscriptions-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-billing' ? 'active' : 'notactive'" to="setting-student-billing" class="nav-link">-->
<!--                            <i class="feather-credit-card"></i> Billing Info-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-payment' ? 'active' : 'notactive'" to="setting-student-payment" class="nav-link">-->
<!--                            <i class="feather-credit-card"></i> Payment-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                    <li class="nav-item">-->
<!--                        <router-link :class="currentPath == 'setting-student-invoice' ? 'active' : 'notactive'" to="setting-student-invoice" class="nav-link">-->
<!--                            <i class="feather-clipboard"></i> Invoice-->
<!--                        </router-link>-->
<!--                    </li>-->
<!--                </ul>-->
            </div>
        </div>	
    </div>				
</div>
<!-- /sidebar -->
</template>
<script>
    export default {
        computed:{
            currentPath() {
                return this.$route.name;
            }
        }
    }
    </script>