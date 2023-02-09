import { createApp } from 'vue';
import App from "./App.vue";
import { router } from './router';
import Vue3Autocounter from 'vue3-autocounter';
import global from './Globals/global';
import store from './Store/store';
import axios from 'axios';

/*********Header component**********/
import Header from './views/layouts/header.vue'
import IndexHeader from './views/layouts/indexheader.vue'
import HeaderBorder from './views/layouts/headerborder.vue'
import HeaderPage from './components/headerpage.vue'
import HeaderPage1 from './components/headerpage1.vue'
import Menuheader from './components/menuheader.vue'
import Mainnav from './components/mainnav.vue'
import Signpages from './components/signpages.vue'
import Navbar from './components/navbar.vue'
import LoginHeader from './views/layouts/loginheader.vue'
import LoginHeaderBorder from './views/layouts/loginheaderborder.vue'
import Instructorheader from './views/layouts/instructorheader.vue'
import Instructorheaderborder from './views/layouts/instructorheaderborder.vue'

/*********Footer component**********/
import Footer from './views/layouts/footer.vue'
import FooterTop from './components/footertop.vue'
import FooterBottom from './components/footerbottom.vue'
/*********Pages**********/
import Addcourse from './views/pages/pages/course/add-course.vue'
import CartBreadcrumb from './components/breadcrumb/cartbreadcrumb.vue'
import Cart from './views/pages/pages/cart.vue'
import Checkout from './views/pages/pages/checkout.vue'
import CheckoutBreadcrumb from './components/breadcrumb/checkoutbreadcrumb.vue'
import Comesoon from './views/pages/pages/error/come-soon.vue'
import courseDetails from './views/pages/pages/course/course-details.vue'
import CourseInnerpage from './views/pages/pages/course/inner-page.vue'
import CourseDetailsBreadcrumb from './components/breadcrumb/coursedetails.vue'
import courseDetails1 from './views/pages/pages/course/course-details1.vue'
import courseDetails2 from './views/pages/pages/course/course-details2.vue'
import CourseGrid from './views/pages/pages/course/course-grid.vue'
import Coursegridbreadcrumb from './components/breadcrumb/coursegridbreadcrumb.vue'
import Coursegridfilter from './components/coursefilter.vue'
import Gridlistsidebar from './views/pages/pages/course/gridlistsidebar.vue'
import CourseLesson from './views/pages/pages/course-lesson.vue'
import CourseList from './views/pages/pages/course/course-list.vue'
import CourseHeader from './components/courseheader.vue'
import Error404 from './views/pages/pages/error/error-404.vue'
import Error500 from './views/pages/pages/error/error-500.vue'
import Faq from './views/pages/pages/faq.vue'
import FaqBreadcrumb from './components/breadcrumb/faqbreadcrumb.vue'
import ForgotPassword from './views/pages/pages/forgot-password.vue'
import LoginBanner from './components/loginbanner.vue'
import HelpCenter from './views/pages/pages/help-center.vue'
import HelpCenterBreadcrumb from './components/breadcrumb/helpcenter.vue'
import RegisterBanner from './components/registerbanner.vue'
import Registerstepfive from './views/pages/pages/register-step-five.vue'
import Registerstepfour from './views/pages/pages/register-step-four.vue'
import Registerstepone from './views/pages/pages/register-step-one.vue'
import Registerstepthree from './views/pages/pages/register-step-three.vue'
import Registersteptwo from './views/pages/pages/register-step-two.vue'
import Register from './views/pages/pages/register.vue'
import Support from './views/pages/pages/support.vue'
import SupportBreadcrumb from './components/breadcrumb/supportbreadcrumb.vue'
import Termcondition from './views/pages/pages/term-condition.vue'
import TermconditionBreadcrumb from './components/breadcrumb/termcondition.vue'
import JobCategory from './views/pages/pages/job-category.vue'
import JobcategoryBreadcrumb from './components/breadcrumb/jobcategory.vue'
import Login from './views/pages/pages/login.vue'
import Newpassword from './views/pages/pages/new-password.vue'
import Notifications from './views/pages/pages/notifications.vue'
import NotificationsBreadcrumb from './components/breadcrumb/notificationsbreadcrumb.vue'
import Pricingplan from './components/breadcrumb/pricingplan.vue'
import PricingPlan2 from './views/pages/pages/pricing-plan-2.vue'
import PricingPlan from './views/pages/pages/pricing-plan.vue'
import Privacypolicy from './views/pages/pages/privacy-policy.vue'
import PrivacypolicyBreadcrumb from './components/breadcrumb/privacypolicy.vue'
import Underconstruction from './views/pages/pages/error/under-construction.vue'
import Verificationcode from './views/pages/pages/verification-code.vue'
import Wishlistbreadcrumb from './components/breadcrumb/wishlistbreadcrumb.vue'
import Wishlist from './views/pages/pages/wishlist.vue'

/*********Blog**********/
import BlogDetails from './views/pages/blog/blog-details.vue'
import BlogDetailsBreadcrumb from './components/breadcrumb/blogdetails.vue'
import BlogSidebar from './components/blogsidebar.vue'
import BlogGrid from './views/pages/blog/blog-grid.vue'
import BlogGridBreadcrumb from './components/breadcrumb/bloggrid.vue'
import BlogPagination from './components/pagination.vue'
import BlogList from './views/pages/blog/blog-list.vue'
import BloglistBreadcrumb from './components/breadcrumb/bloglist.vue'
import BlogMasonry from './views/pages/blog/blog-masonry.vue'
import BlogMasonryBreadcrumb from './components/breadcrumb/blogmasonry.vue'
import BlogModern from './views/pages/blog/blog-modern.vue'
import BlogModernBreadcrumb from './components/breadcrumb/blogmodern.vue'

/*********Student**********/
import CourseMessage from './views/pages/student/course-message.vue'
import CourseStudent from './views/pages/student/course-student.vue'
import StudentFilter from './components/studentfilter.vue'
import StudentHeader from './components/studentheader.vue'
import CourseWishlist from './views/pages/student/course-wishlist.vue'
import DepositStudentDashboard from './views/pages/student/deposit-student-dashboard.vue'
import DepositStudent from './views/pages/student/deposit-student.vue'
import Purchasehistory from './views/pages/student/purchase-history.vue'
import Settingeditprofile from './views/pages/student/setting-edit-profile.vue'
import Studentsidebar from './components/studentsidebar.vue'
import Settingstudentaccounts from './views/pages/student/setting-student-accounts.vue'
import Settingstudentbilling from './views/pages/student/setting-student-billing.vue'
import Settingstudentdeleteprofile from './views/pages/student/setting-student-delete-profile.vue'
import Settingstudentinvoice from './views/pages/student/setting-student-invoice.vue'
import Settingstudentnotification from './views/pages/student/setting-student-notification.vue'
import Settingstudentpayment from './views/pages/student/setting-student-payment.vue'
import Settingstudentprivacy from './views/pages/student/setting-student-privacy.vue'
import Settingstudentreferral from './views/pages/student/setting-student-referral.vue'
import Settingstudentsecurity from './views/pages/student/setting-student-security.vue'
import Settingstudentsocialprofile from './views/pages/student/setting-student-social-profile.vue'
import Settingstudentsubscription from './views/pages/student/setting-student-subscription.vue'
import Settingsupportnewtickets from './views/pages/student/setting-support-new-tickets.vue'
import Settingsupporttickets from './views/pages/student/setting-support-tickets.vue'
import Settingsupportviewtickets from './views/pages/student/setting-support-view-tickets.vue'
import SettingsupportviewticketsBreadcrumb from './components/breadcrumb/settingsupportviewtickets.vue'
import StudentprofileBreadcrumb from './components/breadcrumb/studentprofile.vue'
import Studentprofile from './views/pages/student/student-profile.vue'
import Studentsgrid from './views/pages/student/students-grid.vue'
import StudentsgridBreadcrumb from './components/breadcrumb/studentsgrid.vue'
import Studentsgrid2 from './views/pages/student/students-grid2.vue'
import Studentslist from './views/pages/student/students-list.vue'
import StudentslistBreadcrumb from './components/breadcrumb/studentslist.vue'
import Transactionsstudent from './views/pages/student/transactions-student.vue'
import Viewinvoice from './views/pages/student/view-invoice.vue'

/*********Instructor**********/
import DashboardInstructor from './views/pages/instructor/dashboard-instructor.vue'
import InstructorBreadcrumb from './components/breadcrumb/instructorbreadcrumb.vue'
import DepositDashboardInstructor from './views/pages/instructor/deposit-instructor-dashboard.vue'
import DepositBreadcrumb from './components/breadcrumb/depositbreadcrumb.vue'
import DepositInstructor from './views/pages/instructor/deposit-instructor.vue'
import DepositModal from './views/pages/instructor/depositmodal.vue'
import ChatHeader from './components/chatheader.vue'
import InstructorChat from './views/pages/instructor/instructor-chat.vue'
import InstructorCourse from './views/pages/instructor/instructor-course.vue'
import InstructorSidebar from './components/instructorsidebar.vue'
import InstructorDashboard from './views/pages/instructor/instructor-dashboard.vue'
import Instructordeleteprofile from './views/pages/instructor/instructor-delete-profile.vue'
import Instructorearnings from './views/pages/instructor/instructor-earnings.vue'
import Instructoreditprofile from './views/pages/instructor/instructor-edit-profile.vue'
import InstructorEdit from './views/pages/instructor/instructor-edit.vue'
import InstructoreditBreadcrumb from './components/breadcrumb/instructoredit.vue'
import Instructorgrid2Breadcrumb from './components/breadcrumb/instructorgrid2.vue'
import Instructorgrid2 from './views/pages/instructor/instructor-grid-2.vue'
import Instructorfilter from './components/instructorfilter.vue'
import Instructorgrid from './views/pages/instructor/instructor-grid.vue'
import Instructorlinkedaccount from './views/pages/instructor/instructor-linked-account.vue'
import Instructorlist from './views/pages/instructor/instructor-list.vue'
import InstructorlistBreadcrumb from './components/breadcrumb/instructorlist.vue'
import Instructornewtickets from './views/pages/instructor/instructor-new-tickets.vue'
import Instructornotification from './views/pages/instructor/instructor-notification.vue'
import Instructororders from './views/pages/instructor/instructor-orders.vue'
import Instructorpayouts from './views/pages/instructor/instructor-payouts.vue'
import Instructorprofileprivacy from './views/pages/instructor/instructor-profile-privacy.vue'
import Instructorprofile from './views/pages/instructor/instructor-profile.vue'
import Instructorreviews from './views/pages/instructor/instructor-reviews.vue'
import Instructorsecurity from './views/pages/instructor/instructor-security.vue'
import Instructorsocialprofiles from './views/pages/instructor/instructor-social-profiles.vue'
import Instructorstudentgrid from './views/pages/instructor/instructor-student-grid.vue'
import Instructorstudentlist from './views/pages/instructor/instructor-student-list.vue'
import Instructortickets from './views/pages/instructor/instructor-tickets.vue'
import Transactionsinstructor from './views/pages/instructor/transactions-instructor.vue'
import Withdrawalinstructor from './views/pages/instructor/withdrawal-instructor.vue'

/*********Home**********/
import homebanner from './views/pages/home/homebanner.vue'
import featurecourse from './views/pages/home/featurecourse.vue'
import trendingcourse from './views/pages/home/trendingcourse.vue'
import shareknowledge from './views/pages/home/shareknowledge.vue'
import latestblog from './views/pages/home/latestblog.vue'
import dashboardindex from './views/pages/home/dashboardindex.vue'
import featureinstructors from './views/pages/home/featureinstructors.vue'
import leadingcompanies from './views/pages/home/leadingcompanies.vue'

/*******Plugin import***********/
import jquery from 'jquery'; 
window.$ = jquery

import VueSelect from 'vue3-select2-component'
import SummernoteEditor from 'vue3-summernote-editor';
import DatePicker from 'vue3-datepicker'
import VueFeather from 'vue-feather';
import VueApexCharts from "vue3-apexcharts";

import './assets/js/jquery-3.6.0.min.js'
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import './assets/css/feather.css';
import './assets/plugins/fontawesome/css/fontawesome.min.css';
import './assets/plugins/fontawesome/css/all.min.css';
import'./assets/plugins/summernote/summernote-lite.min.css';
import'./assets/plugins/summernote/summernote-lite.min.js';
import'./assets/plugins/slick/slick.css';
import'./assets/plugins/slick/slick-theme.css';
import'./assets/plugins/slick/slick.js';
import'./assets/plugins/dropzone/dropzone.min.js';
import'./assets/plugins/dropzone/dropzone.min.css';
import'./assets/plugins/aos/aos.css';
import'./assets/plugins/aos/aos.js';

import './assets/css/style.css';



const app = createApp(App)

/*********Header component**********/
app.component('layouts', Header);
app.component('layoutsindex', IndexHeader);
app.component('layouts2', HeaderBorder);
app.component('headerpage', HeaderPage);
app.component('headerpage1', HeaderPage1);
app.component('menuheader', Menuheader);
app.component('mainnav', Mainnav);
app.component('signpages', Signpages);
app.component('navbar', Navbar);
app.component('layoutslogin', LoginHeader);
app.component('layoutsloginborder', LoginHeaderBorder);
app.component('layoutsinstructor', Instructorheader);
app.component('layoutsinstructorborder', Instructorheaderborder);

/*********Footer component**********/
app.component('layouts1', Footer);
app.component('footertop', FooterTop);
app.component('footerbottom', FooterBottom);
/*********Pages**********/
app.component('add-course', Addcourse);
app.component('cart', Cart);
app.component('cartbreadcrumb', CartBreadcrumb);
app.component('checkout', Checkout);
app.component('checkoutbreadcrumb', CheckoutBreadcrumb);
app.component('comesoon', Comesoon);
app.component('inner-page', CourseInnerpage);
app.component('course-details', courseDetails);
app.component('coursedetails', CourseDetailsBreadcrumb);
app.component('course-details1', courseDetails1);
app.component('course-details2', courseDetails2);
app.component('course-grid', CourseGrid);
app.component('coursegridbreadcrumb', Coursegridbreadcrumb);
app.component('coursefilter', Coursegridfilter);
app.component('gridlistsidebar', Gridlistsidebar);
app.component('course-lesson', CourseLesson);
app.component('course-list', CourseList);
app.component('courseheader', CourseHeader);
app.component('error-404', Error404);
app.component('error-500', Error500);
app.component('faq', Faq);
app.component('faqbreadcrumb', FaqBreadcrumb);
app.component('forgot-password', ForgotPassword);
app.component('help-center', HelpCenter);
app.component('helpcenter', HelpCenterBreadcrumb);
app.component('loginbanner', LoginBanner);
app.component('registerbanner', RegisterBanner);
app.component('register-step-five', Registerstepfive);
app.component('register-step-four', Registerstepfour);
app.component('register-step-one', Registerstepone);
app.component('register-step-three', Registerstepthree);
app.component('register-step-two', Registersteptwo);
app.component('register', Register);
app.component('support', Support);
app.component('supportbreadcrumb', SupportBreadcrumb);
app.component('term-condition', Termcondition);
app.component('termcondition', TermconditionBreadcrumb);
app.component('under-construction', Underconstruction);
app.component('verification-code', Verificationcode);
app.component('wishlist', Wishlist);
app.component('wishlistbreadcrumb', Wishlistbreadcrumb);

/*********Blog**********/
app.component('blog-details', BlogDetails);
app.component('blogdetails', BlogDetailsBreadcrumb);
app.component('blogsidebar', BlogSidebar);
app.component('blog-grid', BlogGrid);
app.component('bloggrid', BlogGridBreadcrumb);
app.component('pagination', BlogPagination);
app.component('blog-list', BlogList);
app.component('bloglist', BloglistBreadcrumb);
app.component('blog-masonry', BlogMasonry);
app.component('blogmasonry', BlogMasonryBreadcrumb);
app.component('blog-modern', BlogModern);
app.component('blogmodern', BlogModernBreadcrumb);
/*********Student**********/
app.component('course-message', CourseMessage);
app.component('course-student', CourseStudent);
app.component('studentfilter', StudentFilter);
app.component('studentheader', StudentHeader);
app.component('course-wishlist', CourseWishlist);
app.component('deposit-student-dashboard', DepositStudentDashboard);
app.component('deposit-student', DepositStudent);
app.component('purchase-history', Purchasehistory);
app.component('setting-edit-profile', Settingeditprofile);
app.component('studentsidebar', Studentsidebar);
app.component('setting-student-accounts', Settingstudentaccounts);
app.component('setting-student-billing', Settingstudentbilling);
app.component('setting-student-delete-profile', Settingstudentdeleteprofile);
app.component('setting-student-invoice', Settingstudentinvoice);
app.component('setting-student-notification', Settingstudentnotification);
app.component('setting-student-payment', Settingstudentpayment);
app.component('setting-student-privacy', Settingstudentprivacy);
app.component('setting-student-referral', Settingstudentreferral);
app.component('setting-student-security', Settingstudentsecurity);
app.component('setting-student-social-profile', Settingstudentsocialprofile);
app.component('setting-student-subscription', Settingstudentsubscription);
app.component('setting-support-new-tickets', Settingsupportnewtickets);
app.component('setting-support-tickets', Settingsupporttickets);
app.component('settingsupportviewtickets', SettingsupportviewticketsBreadcrumb);
app.component('setting-support-view-tickets', Settingsupportviewtickets);
app.component('student-profile', Studentprofile);
app.component('studentprofile', StudentprofileBreadcrumb);
app.component('students-grid', Studentsgrid);
app.component('studentsgrid', StudentsgridBreadcrumb);
app.component('students-grid2', Studentsgrid2);
app.component('students-list', Studentslist);
app.component('studentslist', StudentslistBreadcrumb);
app.component('transactions-student', Transactionsstudent);
app.component('view-invoice', Viewinvoice);

/*********Instructor**********/
app.component('dashboard-instructor', DashboardInstructor);
app.component('instructorbreadcrumb', InstructorBreadcrumb);
app.component('deposit-instructor-dashboard', DepositDashboardInstructor);
app.component('depositbreadcrumb', DepositBreadcrumb);
app.component('deposit-instructor', DepositInstructor);
app.component('depositmodal', DepositModal);
app.component('instructor-chat', InstructorChat);
app.component('chatheader', ChatHeader);
app.component('instructorsidebar', InstructorSidebar);
app.component('instructor-course', InstructorCourse);
app.component('instructor-dashboard', InstructorDashboard);
app.component('instructor-delete-profile', Instructordeleteprofile);
app.component('instructor-earnings', Instructorearnings);
app.component('instructor-edit-profile', Instructoreditprofile);
app.component('instructor-edit', InstructorEdit);
app.component('instructoredit', InstructoreditBreadcrumb);
app.component('instructorgrid2', Instructorgrid2Breadcrumb);
app.component('instructor-grid-2', Instructorgrid2);
app.component('instructorfilter', Instructorfilter);
app.component('instructor-grid', Instructorgrid);
app.component('instructor-linked-account', Instructorlinkedaccount);
app.component('instructor-list', Instructorlist);
app.component('instructorlist', InstructorlistBreadcrumb);
app.component('instructor-new-tickets', Instructornewtickets);
app.component('instructor-notification', Instructornotification);
app.component('instructor-orders', Instructororders);
app.component('instructor-payouts', Instructorpayouts);
app.component('instructor-profile-privacy', Instructorprofileprivacy);
app.component('instructor-profile', Instructorprofile);
app.component('instructor-reviews', Instructorreviews);
app.component('instructor-security', Instructorsecurity);
app.component('instructor-social-profiles', Instructorsocialprofiles);
app.component('instructor-student-grid', Instructorstudentgrid);
app.component('instructor-student-list', Instructorstudentlist);
app.component('instructor-tickets', Instructortickets);
app.component('job-category', JobCategory);
app.component('jobcategory', JobcategoryBreadcrumb);
app.component('login', Login);
app.component('new-password', Newpassword);
app.component('notifications', Notifications);
app.component('notificationsbreadcrumb', NotificationsBreadcrumb);
app.component('pricing-plan-2', PricingPlan2);
app.component('pricingplan', Pricingplan);
app.component('pricing-plan', PricingPlan);
app.component('privacy-policy', Privacypolicy);
app.component('privacypolicy', PrivacypolicyBreadcrumb);
app.component('transactions-instructor', Transactionsinstructor);
app.component('withdrawal-instructor', Withdrawalinstructor);


/*********Home**********/
app.component('homebanner', homebanner);
app.component('featurecourse', featurecourse);
app.component('trendingcourse', trendingcourse);
app.component('shareknowledge', shareknowledge);
app.component('latestblog', latestblog);
app.component('dashboardindex', dashboardindex);
app.component('leadingcompanies', leadingcompanies);
app.component('featureinstructors', featureinstructors);



app.component('SummernoteEditor', SummernoteEditor);
app.component('vue3-autocounter', Vue3Autocounter)

app.component('vue-select', VueSelect);
app.component('datepicker', DatePicker);
app.component(VueFeather.name, VueFeather);
app.use(VueApexCharts);

app.config.globalProperties.$store = store;
app.config.globalProperties.$axios = axios;

app.use(router)
.mount('#app');

app.use(global);
app.use(store);




