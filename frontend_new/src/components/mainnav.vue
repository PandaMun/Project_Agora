<template>
    <ul class="main-nav">
        <li v-bind:class="{'active': currentPath == 'index' || currentPath == 'index'}">
            <router-link to="/">홈</router-link>
        </li>

      <li v-bind:class="{'active': currentPath == 'debate' || currentPath == 'debate'}">
        <router-link to="/debate">토론입장</router-link>
      </li>




      <li class="has-submenu" :class=" InstructorMenu ? 'active' : 'notactive'">
            <a href="javascript:void(0);">토론 <i class="fas fa-chevron-down"></i></a>
            <ul class="submenu">
                <li :class="currentPath == 'instructor-dashboard' || currentPath == 'dashboard-instructor' ? 'active' : 'notactive'"><router-link to="instructor-dashboard">Dashboard</router-link></li>
                <li class="has-submenu" :class=" InstructorlistMenu ? 'active' : 'notactive'">
                    <router-link to="instructor-list">Instructor</router-link>
                    <ul class="submenu">
                        <li :class="currentPath == 'instructor-list' ? 'active' : 'notactive'"><router-link to="instructor-list">List</router-link></li>
                        <li :class="currentPath == 'instructor-grid' || currentPath == 'instructor-grid-2' ? 'active' : 'notactive'"><router-link to="instructor-grid">Grid</router-link></li>
                    </ul>
                </li>
                <li :class="currentPath == 'instructor-course' ? 'active' : 'notactive'"><router-link to="instructor-course">My Course</router-link></li>
                <li :class="currentPath == 'instructor-reviews' ? 'active' : 'notactive'"><router-link to="instructor-reviews">Reviews</router-link></li>
                <li :class="currentPath == 'instructor-earnings' ? 'active' : 'notactive'"><router-link to="instructor-earnings">Earnings</router-link></li>
                <li :class="currentPath == 'instructor-orders' ? 'active' : 'notactive'"><router-link to="instructor-orders">Orders</router-link></li>
                <li :class="currentPath == 'instructor-payouts' ? 'active' : 'notactive'"><router-link to="instructor-payouts">Payouts</router-link></li>
                <li :class="currentPath == 'instructor-tickets' || currentPath == 'instructor-new-tickets' ? 'active' : 'notactive'"><router-link to="instructor-tickets">Support Ticket</router-link></li>
                <li :class="currentPath == 'instructor-edit-profile' || currentPath == 'instructor-profile' ? 'active' : 'notactive'"><router-link to="instructor-edit-profile">Instructor Profile</router-link></li>
                <li :class="currentPath == 'instructor-security' ? 'active' : 'notactive'"><router-link to="instructor-security">Security</router-link></li>
                <li :class="currentPath == 'instructor-social-profiles' ? 'active' : 'notactive'"><router-link to="instructor-social-profiles">Social Profiles</router-link></li>
                <li :class="currentPath == 'instructor-notification' ? 'active' : 'notactive'"><router-link to="instructor-notification">Notifications</router-link></li>
                <li :class="currentPath == 'instructor-profile-privacy' ? 'active' : 'notactive'"><router-link to="instructor-profile-privacy">Profile Privacy</router-link></li>
                <li :class="currentPath == 'instructor-delete-profile' ? 'active' : 'notactive'"><router-link to="instructor-delete-profile">Delete Profile</router-link></li>
                <li :class="currentPath == 'instructor-linked-account' ? 'active' : 'notactive'"><router-link to="instructor-linked-account">Linked Accounts</router-link></li>
            </ul>
        </li>	
        <li class="has-submenu" :class=" StudentMenu ? 'active' : 'notactive'">
            <a href="javascript:void(0)">커뮤니티 <i class="fas fa-chevron-down"></i></a>
            <ul class="submenu first-submenu">
                <li class="has-submenu" :class=" StudentlistMenu ? 'active' : 'notactive'">
                    <router-link to="students-list">Student</router-link>
                    <ul class="submenu">
                        <li :class="currentPath == 'students-list' ? 'active' : 'notactive'"><router-link to="students-list">List</router-link></li>
                        <li :class="currentPath == 'students-grid' || currentPath == 'students-grid2' ? 'active' : 'notactive'"><router-link to="students-grid">Grid</router-link></li>
                    </ul>
                </li>
                <li :class="currentPath == 'setting-edit-profile' || currentPath == 'student-profile' ? 'active' : 'notactive'"><router-link to="setting-edit-profile">Student Profile</router-link></li>
                <li :class="currentPath == 'setting-student-security' ? 'active' : 'notactive'"><router-link to="setting-student-security">Security</router-link></li>
                <li :class="currentPath == 'setting-student-social-profile' ? 'active' : 'notactive'"><router-link to="setting-student-social-profile">Social profile</router-link></li>
                <li :class="currentPath == 'setting-student-notification' ? 'active' : 'notactive'"><router-link to="setting-student-notification">Notification</router-link></li>
                <li :class="currentPath == 'setting-student-privacy' ? 'active' : 'notactive'"><router-link to="setting-student-privacy">Profile Privacy</router-link></li>
                <li :class="currentPath == 'setting-student-accounts' ? 'active' : 'notactive'"><router-link to="setting-student-accounts">Link Accounts</router-link></li>
                <li :class="currentPath == 'setting-student-referral' ? 'active' : 'notactive'"><router-link to="setting-student-referral">Referal</router-link></li>
                <li :class="currentPath == 'setting-student-subscription' ? 'active' : 'notactive'"><router-link to="setting-student-subscription">Subscribtion</router-link></li>
                <li :class="currentPath == 'setting-student-billing' ? 'active' : 'notactive'"><router-link to="setting-student-billing">Billing</router-link></li>
                <li :class="currentPath == 'setting-student-payment' ? 'active' : 'notactive'"><router-link to="setting-student-payment">Payment</router-link></li>
                <li :class="currentPath == 'setting-student-invoice' || currentPath == 'view-invoice' ? 'active' : 'notactive'"><router-link to="setting-student-invoice">Invoice</router-link></li>
                <li :class="currentPath == 'setting-support-tickets' || currentPath == 'setting-support-view-tickets' ? 'active' : 'notactive'"><router-link to="setting-support-tickets">Support Tickets</router-link></li>
            </ul>
        </li>	
        <li class="has-submenu" :class=" PagesMenu ? 'active' : 'notactive'">
            <a href="javascript:void(0)">HELP DESK <i class="fas fa-chevron-down"></i></a>
            <ul class="submenu">
                <li :class="currentPath == 'notifications' ? 'active' : 'notactive'"><router-link to="notifications">Notification</router-link></li>
                <li :class="currentPath == 'pricing-plan' || currentPath == 'pricing-plan-2' ? 'active' : 'notactive'"><router-link to="pricing-plan">Pricing Plan</router-link></li>
                <li :class="currentPath == 'wishlist' ? 'active' : 'notactive'"><router-link to="wishlist">Wishlist</router-link></li>
                <li class="has-submenu" :class=" PageslistMenu ? 'active' : 'notactive'">
                    <router-link to="course-list">Course</router-link>
                    <ul class="submenu">
                        <li :class="currentPath == 'add-course' ? 'active' : 'notactive'"><router-link to="add-course">Add Course</router-link></li>
                        <li :class="currentPath == 'course-list' ? 'active' : 'notactive'"><router-link to="course-list">Course List</router-link></li>
                        <li :class="currentPath == 'course-grid' ? 'active' : 'notactive'"><router-link to="course-grid">Course Grid</router-link></li>
                        <li :class="currentPath == 'course-details' || currentPath == 'course-details1' || currentPath == 'course-details2' ? 'active' : 'notactive'"><router-link to="course-details">Course Details</router-link></li>
                    </ul>
                </li>
                <li class="has-submenu" :class=" ErrorMenu ? 'active' : 'notactive'">
                    <router-link to="come-soon">Error</router-link>
                    <ul class="submenu">
                        <li :class="currentPath == 'come-soon' ? 'active' : 'notactive'"><router-link to="come-soon">Comeing soon</router-link></li>
                        <li :class="currentPath == 'error-404' ? 'active' : 'notactive'"><router-link to="error-404">404</router-link></li>
                        <li :class="currentPath == 'error-500' ? 'active' : 'notactive'"><router-link to="error-500">500</router-link></li>
                        <li :class="currentPath == 'under-construction' ? 'active' : 'notactive'"><router-link to="under-construction">Under Construction</router-link></li>
                    </ul>
                </li>
                <li :class="currentPath == 'help-center' ? 'active' : 'notactive'"><router-link to="help-center">Help Center</router-link></li>
                <li :class="currentPath == 'faq' ? 'active' : 'notactive'"><router-link to="faq">FAQ</router-link></li>
                <li :class="currentPath == 'support' ? 'active' : 'notactive'"><router-link to="support">Support</router-link></li>
                <li :class="currentPath == 'term-condition' ? 'active' : 'notactive'"><router-link to="term-condition">Terms & Conditions</router-link></li>
                <li :class="currentPath == 'privacy-policy' ? 'active' : 'notactive'"><router-link to="privacy-policy">Privacy Policy</router-link></li>
                <li :class="currentPath == 'job-category' ? 'active' : 'notactive'"><router-link to="job-category">Category</router-link></li>
                <li :class="currentPath == 'cart' ? 'active' : 'notactive'"><router-link to="cart">Cart</router-link></li>
                <li :class="currentPath == 'checkout' ? 'active' : 'notactive'"><router-link to="checkout">Checkout</router-link></li>
                <li :class="currentPath == 'login' ? 'active' : 'notactive'"><router-link to="login">Login</router-link></li>
                <li :class="currentPath == 'register' ? 'active' : 'notactive'"><router-link to="register">Register</router-link></li>
                <li :class="currentPath == 'forgot-password' ? 'active' : 'notactive'"><router-link to="forgot-password">Forgot Password</router-link></li>
            </ul>
        </li>
        <li class="has-submenu"  :class=" BlogMenu ? 'active' : 'notactive'">
            <a href="javascript:void(0)">Blog <i class="fas fa-chevron-down"></i></a>
            <ul class="submenu">
                <li :class="currentPath == 'blog-list' ? 'active' : 'notactive'"><router-link to="blog-list">Blog List</router-link></li>
                <li :class="currentPath == 'blog-grid' ? 'active' : 'notactive'"><router-link to="blog-grid">Blog Grid</router-link></li>
                <li :class="currentPath == 'blog-masonry' ? 'active' : 'notactive'"><router-link to="blog-masonry">Blog Masonry</router-link></li>
                <li :class="currentPath == 'blog-modern' ? 'active' : 'notactive'"><router-link to="blog-modern">Blog Modern</router-link></li>
                <li :class="currentPath == 'blog-details' ? 'active' : 'notactive'"><router-link to="blog-details">Blog Details</router-link></li>
            </ul>
        </li>
        <li class="login-link">
            <router-link to="login">Login / Signup</router-link>
        </li>
    </ul>
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

        },
        computed: {
        currentPath() {
            return this.$route.name
        },
        InstructorMenu() {
        return this.$route.name == 'instructor-list' || this.$route.name == 'withdrawal-instructor' || this.$route.name == 'transactions-instructor' || this.$route.name == 'instructor-student-list'  || this.$route.name == 'instructor-student-grid' || this.$route.name == 'instructor-profile' || this.$route.name == 'instructor-new-tickets' || this.$route.name == 'instructor-grid-2' || this.$route.name == 'instructor-edit' || this.$route.name == 'instructor-chat' || this.$route.name == 'deposit-instructor-dashboard' || this.$route.name == 'deposit-instructor' || this.$route.name == 'dashboard-instructor' || this.$route.name == 'instructor-dashboard' || this.$route.name == 'instructor-grid'  || this.$route.name == 'instructor-course'  || this.$route.name == 'instructor-reviews'  
        || this.$route.name == 'instructor-earnings'  || this.$route.name == 'instructor-orders'  || this.$route.name == 'instructor-payouts'  || this.$route.name == 'instructor-tickets'  
        || this.$route.name == 'instructor-edit-profile'  || this.$route.name == 'instructor-security'  || this.$route.name == 'instructor-social-profiles'  || this.$route.name == 'instructor-notification'  
        || this.$route.name == 'instructor-profile-privacy'  || this.$route.name == 'instructor-delete-profile'  || this.$route.name == 'instructor-linked-account';
        },
        InstructorlistMenu() {
        return this.$route.name == 'instructor-list' || this.$route.name == 'instructor-grid' || this.$route.name == 'instructor-grid-2';
        },
        StudentMenu() {
        return this.$route.name == 'students-list' || this.$route.name == 'view-invoice' || this.$route.name == 'transactions-student' || this.$route.name == 'students-grid2' || this.$route.name == 'student-profile' || this.$route.name == 'setting-support-view-tickets' || this.$route.name == 'setting-student-delete-profile' || this.$route.name == 'deposit-student-dashboard' || this.$route.name == 'deposit-student' || this.$route.name == 'purchase-history' || this.$route.name == 'course-student' || this.$route.name == 'course-message' || this.$route.name == 'students-grid' || this.$route.name == 'setting-edit-profile'  || this.$route.name == 'setting-student-security'  || this.$route.name == 'setting-student-social-profile' 
        || this.$route.name == 'setting-student-notification' || this.$route.name == 'setting-student-privacy' || this.$route.name == 'setting-student-accounts'  || this.$route.name == 'setting-student-referral'  || this.$route.name == 'setting-student-subscription'  
        || this.$route.name == 'setting-student-billing' || this.$route.name == 'setting-student-payment' || this.$route.name == 'setting-student-invoice'  || this.$route.name == 'setting-support-tickets';
        },
        StudentlistMenu() {
        return this.$route.name == 'students-list' || this.$route.name == 'students-grid' || this.$route.name == 'students-grid2';
        },
        PagesMenu() {
        return this.$route.name == 'notifications' || this.$route.name == 'term-condition' || this.$route.name == 'privacy-policy' || this.$route.name == 'course-wishlist' || this.$route.name == 'pricing-plan-2' || this.$route.name == 'pricing-plan' || this.$route.name == 'wishlist'  || this.$route.name == 'add-course'  || this.$route.name == 'course-list' 
        || this.$route.name == 'course-grid' || this.$route.name == 'course-details' || this.$route.name == 'course-details1' || this.$route.name == 'course-details2'  || this.$route.name == 'come-soon'  || this.$route.name == 'error-404' || this.$route.name == 'error-500' 
        || this.$route.name == 'under-construction' || this.$route.name == 'help-center' || this.$route.name == 'faq'  || this.$route.name == 'support'  || this.$route.name == 'job-category'  || this.$route.name == 'cart' 
        || this.$route.name == 'checkout' || this.$route.name == 'login' || this.$route.name == 'register'  || this.$route.name == 'forgot-password' || this.$route.name == 'course-lesson';
        },
        PageslistMenu() {
        return this.$route.name == 'add-course' || this.$route.name == 'course-details1' || this.$route.name == 'course-details2' || this.$route.name == 'course-list' || this.$route.name == 'course-grid' || this.$route.name == 'course-details';
        },
        ErrorMenu() {
        return this.$route.name == 'come-soon' || this.$route.name == 'error-404' || this.$route.name == 'error-500'  || this.$route.name == 'under-construction';
        },
        BlogMenu() {
        return this.$route.name == 'blog-list' || this.$route.name == 'blog-grid' || this.$route.name == 'blog-masonry' || this.$route.name == 'blog-modern' || this.$route.name == 'blog-details';
        },
    },
    }
</script>