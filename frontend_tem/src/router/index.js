import {createRouter, createWebHistory} from 'vue-router';
import AddCourse from '../views/pages/pages/course/add-course'
import Blogdetails from '../views/pages/blog/blog-details'
import Bloggrid from '../views/pages/blog/blog-grid'
import Bloglist from '../views/pages/blog/blog-list'
import Blogmasonry from '../views/pages/blog/blog-masonry'
import Blogmodern from '../views/pages/blog/blog-modern'
import BlogTemp from '../views/pages/blog/blog-temp';
import cart from '../views/pages/pages/cart.vue'
import checkout from '../views/pages/pages/checkout.vue'
import comesoon from '../views/pages/pages/error/come-soon.vue'
import coursedetails from '../views/pages/pages/course/course-details.vue'
import coursedetails1 from '../views/pages/pages/course/course-details1.vue'
import coursedetails2 from '../views/pages/pages/course/course-details2.vue'
import coursegird from '../views/pages/pages/course/course-grid.vue'
import courselesson from '../views/pages/pages/course-lesson.vue'
import courselist from '../views/pages/pages/course/course-list.vue'
import coursemessage from '../views/pages/student/course-message.vue'
import courseStudent from '../views/pages/student/course-student.vue'
import coursewishlist from '../views/pages/student/course-wishlist.vue'
import dashboardinstructor from '../views/pages/instructor/dashboard-instructor.vue'
import depositinstructordashboard from '../views/pages/instructor/deposit-instructor-dashboard.vue'
import depositinstructor from '../views/pages/instructor/deposit-instructor.vue'
import depositstudentdashboard from '../views/pages/student/deposit-student-dashboard.vue'
import depositstudent from '../views/pages/student/deposit-student.vue'
import error404 from '../views/pages/pages/error/error-404.vue'
import error500 from '../views/pages/pages/error/error-500.vue'
import faq from '../views/pages/pages/faq.vue'
import forgotpassword from '../views/pages/pages/forgot-password.vue'
import helpcenter from '../views/pages/pages/help-center.vue'
import index from '../views/pages/home/dashboardindex.vue'
import instructorchat from '../views/pages/instructor/instructor-chat.vue'
import instructorcourse from '../views/pages/instructor/instructor-course.vue'
import instructordashboard from '../views/pages/instructor/instructor-dashboard.vue'
import instructordeleteprofile from '../views/pages/instructor/instructor-delete-profile.vue'
import instructorearnings from '../views/pages/instructor/instructor-earnings.vue'
import instructoreditprofile from '../views/pages/instructor/instructor-edit-profile.vue'
import instructoredit from '../views/pages/instructor/instructor-edit.vue'
import instructorgrid2 from '../views/pages/instructor/instructor-grid-2.vue'
import instructorgrid from '../views/pages/instructor/instructor-grid.vue'
import instructorlinkedaccount from '../views/pages/instructor/instructor-linked-account.vue'
import instructorlist from '../views/pages/instructor/instructor-list.vue'
import instructornewtickets from '../views/pages/instructor/instructor-new-tickets.vue'
import instructornotification from '../views/pages/instructor/instructor-notification.vue'
import instructororders from '../views/pages/instructor/instructor-orders.vue'
import instructorpayouts from '../views/pages/instructor/instructor-payouts.vue'
import instructorprofileprivacy from '../views/pages/instructor/instructor-profile-privacy.vue'
import instructorprofile from '../views/pages/instructor/instructor-profile.vue'
import instructorreviews from '../views/pages/instructor/instructor-reviews.vue'
import instructorsecurity from '../views/pages/instructor/instructor-security.vue'
import instructorsocialprofiles from '../views/pages/instructor/instructor-social-profiles.vue'
import instructorstudentgrid from '../views/pages/instructor/instructor-student-grid.vue'
import instructorstudentlist from '../views/pages/instructor/instructor-student-list.vue'
import instructortickets from '../views/pages/instructor/instructor-tickets.vue'
import jobcategory from '../views/pages/pages/job-category.vue'
import login from '../views/pages/pages/login.vue'
import newpassword from '../views/pages/pages/new-password.vue'
import notifications from '../views/pages/pages/notifications.vue'
import pricingplan2 from '../views/pages/pages/pricing-plan-2.vue'
import pricingplan from '../views/pages/pages/pricing-plan.vue'
import privacypolicy from '../views/pages/pages/privacy-policy.vue'
import purchasehistory from '../views/pages/student/purchase-history.vue'
import registerstepfive from '../views/pages/pages/register-step-five.vue'
import registerstepfour from '../views/pages/pages/register-step-four.vue'
import registerstepone from '../views/pages/pages/register-step-one.vue'
import registerstepthree from '../views/pages/pages/register-step-three.vue'
import registersteptwo from '../views/pages/pages/register-step-two.vue'
import register from '../views/pages/pages/register.vue'
import settingeditprofile from '../views/pages/student/setting-edit-profile.vue'
import settingstudentaccounts from '../views/pages/student/setting-student-accounts.vue'
import settingstudentbilling from '../views/pages/student/setting-student-billing.vue'
import settingstudentdeleteprofile from '../views/pages/student/setting-student-delete-profile.vue'
import settingstudentinvoice from '../views/pages/student/setting-student-invoice.vue'
import settingstudentnotification from '../views/pages/student/setting-student-notification.vue'
import settingstudentpayment from '../views/pages/student/setting-student-payment.vue'
import settingstudentprivacy from '../views/pages/student/setting-student-privacy.vue'
import settingstudentreferral from '../views/pages/student/setting-student-referral.vue'
import settingstudentsecurity from '../views/pages/student/setting-student-security.vue'
import settingstudentsocialprofile from '../views/pages/student/setting-student-social-profile.vue'
import settingstudentsubscription from '../views/pages/student/setting-student-subscription.vue'
import settingsupportnewtickets from '../views/pages/student/setting-support-new-tickets.vue'
import settingsupporttickets from '../views/pages/student/setting-support-tickets.vue'
import settingsupportviewtickets from '../views/pages/student/setting-support-view-tickets.vue'
import studentprofile from '../views/pages/student/student-profile.vue'
import studentsgrid from '../views/pages/student/students-grid.vue'
import studentsgrid2 from '../views/pages/student/students-grid2.vue'
import studentslist from '../views/pages/student/students-list.vue'
import support from '../views/pages/pages/support.vue'
import termcondition from '../views/pages/pages/term-condition.vue'
import transactionsinstructor from '../views/pages/instructor/transactions-instructor.vue'
import transactionsstudent from '../views/pages/student/transactions-student.vue'
import underconstruction from '../views/pages/pages/error/under-construction.vue'
import verificationcode from '../views/pages/pages/verification-code.vue'
import viewinvoice from '../views/pages/student/view-invoice.vue'
import wishlist from '../views/pages/pages/wishlist.vue'
import withdrawalinstructor from '../views/pages/instructor/withdrawal-instructor.vue'
import changePasswordEmail from '../views/pages/pages/change-password-email.vue'
import debateRegist from '../views/pages/debate/debateConfiguration.vue'
import debateModify from '../views/pages/debate/debateConfigurationModify.vue'
import debatemain from '../views/pages/debate/debatemain.vue'
import debatewaiting from "../views/pages/debate/debatewaiting.vue";
import debate from "../views/pages/debate/debate.vue";

const routes = [
  {
    path: '/add-course',
    name: 'add-course',
    component: AddCourse
  },
  {
    path: '/blog-details',
    name: 'blog-details',
    component: Blogdetails
  },
  {
    path: '/blog-grid',
    name: 'blog-grid',
    component: Bloggrid
  },
  {
    path: '/blog-list',
    name: 'blog-list',
    component: Bloglist
  },
  {
    path: '/blog-masonry',
    name: 'blog-masonry',
    component: Blogmasonry
  },
  {
    path: '/blog-modern',
    name: 'blog-modern',
    component: Blogmodern
  },
  {
    path: '/blog-temp',
    name: 'blog-temp',
    component: BlogTemp
  },
  {
    path: '/cart',
    name: 'cart',
    component: cart
  },
  {
    path: '/checkout',
    name: 'checkout',
    component: checkout
  },
  {
    path: '/come-soon',
    name: 'come-soon',
    component: comesoon
  },
  {
    path: '/course-details',
    name: 'course-details',
    component: coursedetails
  },
  {
    path: '/course-details1',
    name: 'course-details1',
    component: coursedetails1
  },
  {
    path: '/course-details2',
    name: 'course-details2',
    component: coursedetails2
  },
  {
    path: '/course-grid',
    name: 'course-grid',
    component: coursegird
  },
  {
    path: '/course-lesson',
    name: 'course-lesson',
    component: courselesson
  },
  {
    path: '/course-list',
    name: 'course-list',
    component: courselist
  },
  {
    path: '/course-message',
    name: 'course-message',
    component: coursemessage
  },
  {
    path: '/course-student',
    name: 'course-student',
    component: courseStudent
  },
  {
    path: '/course-wishlist',
    name: 'course-wishlist',
    component: coursewishlist
  },
  {
    path: '/dashboard-instructor',
    name: 'dashboard-instructor',
    component: dashboardinstructor
  },
  {
    path: '/deposit-instructor-dashboard',
    name: 'deposit-instructor-dashboard',
    component: depositinstructordashboard
  },
  {
    path: '/deposit-instructor',
    name: 'deposit-instructor',
    component: depositinstructor
  },
  {
    path: '/deposit-student-dashboard',
    name: 'deposit-student-dashboard',
    component: depositstudentdashboard
  },
  {
    path: '/deposit-student',
    name: 'deposit-student',
    component: depositstudent
  },
  {
    path: '/error-404',
    name: 'error-404',
    component: error404
  },
  {
    path: '/error-500',
    name: 'error-500',
    component: error500
  },
  {
    path: '/faq',
    name: 'faq',
    component: faq
  },
  {
    path: '/forgot-password',
    name: 'forgot-password',
    component: forgotpassword
  },
  {
    path: '/help-center',
    name: 'help-center',
    component: helpcenter
  },
  {
    path: '/',
    name: 'index',
    component: index
  },
  {
    path: '/instructor-chat',
    name: 'instructor-chat',
    component: instructorchat
  },
  {
    path: '/instructor-course',
    name: 'instructor-course',
    component: instructorcourse
  },
  {
    path: '/instructor-dashboard',
    name: 'instructor-dashboard',
    component: instructordashboard
  },
  {
    path: '/instructor-delete-profile',
    name: 'instructor-delete-profile',
    component: instructordeleteprofile
  },
  {
    path: '/instructor-earnings',
    name: 'instructor-earnings',
    component: instructorearnings
  },
  {
    path: '/instructor-edit-profile',
    name: 'instructor-edit-profile',
    component: instructoreditprofile
  },
  {
    path: '/instructor-edit',
    name: 'instructor-edit',
    component: instructoredit
  },
  {
    path: '/instructor-grid-2',
    name: 'instructor-grid-2',
    component: instructorgrid2
  },
  {
    path: '/instructor-grid',
    name: 'instructor-grid',
    component: instructorgrid
  },
  {
    path: '/instructor-linked-account',
    name: 'instructor-linked-account',
    component: instructorlinkedaccount
  },
  {
    path: '/instructor-list',
    name: 'instructor-list',
    component: instructorlist
  },
  {
    path: '/instructor-new-tickets',
    name: 'instructor-new-tickets',
    component: instructornewtickets
  },
  {
    path: '/instructor-notification',
    name: 'instructor-notification',
    component: instructornotification
  },
  {
    path: '/instructor-orders',
    name: 'instructor-orders',
    component: instructororders
  },
  {
    path: '/instructor-payouts',
    name: 'instructor-payouts',
    component: instructorpayouts
  },
  {
    path: '/instructor-profile-privacy',
    name: 'instructor-profile-privacy',
    component: instructorprofileprivacy
  },
  {
    path: '/instructor-profile',
    name: 'instructor-profile',
    component: instructorprofile
  },
  {
    path: '/instructor-reviews',
    name: 'instructor-reviews',
    component: instructorreviews
  },
  {
    path: '/instructor-security',
    name: 'instructor-security',
    component: instructorsecurity
  },
  {
    path: '/instructor-social-profiles',
    name: 'instructor-social-profiles',
    component: instructorsocialprofiles
  },
  {
    path: '/instructor-student-grid',
    name: 'instructor-student-grid',
    component: instructorstudentgrid
  },
  {
    path: '/instructor-student-list',
    name: 'instructor-student-list',
    component: instructorstudentlist
  },
  {
    path: '/instructor-tickets',
    name: 'instructor-tickets',
    component: instructortickets
  },
  {
    path: '/job-category',
    name: 'job-category',
    component: jobcategory
  },
  {
    path: '/login',
    name: 'login',
    component: login
  },
  {
    path: '/new-password',
    name: 'new-password',
    component: newpassword
  },
  {
    path: '/notifications',
    name: 'notifications',
    component: notifications
  },
  {
    path: '/pricing-plan-2',
    name: 'pricing-plan-2',
    component: pricingplan2
  },
  {
    path: '/pricing-plan',
    name: 'pricing-plan',
    component: pricingplan
  },
  {
    path: '/privacy-policy',
    name: 'privacy-policy',
    component: privacypolicy
  },
  {
    path: '/purchase-history',
    name: 'purchase-history',
    component: purchasehistory
  },
  {
    path: '/register-step-five',
    name: 'register-step-five',
    component: registerstepfive
  },
  {
    path: '/register-step-four',
    name: 'register-step-four',
    component: registerstepfour
  },
  {
    path: '/register-step-one',
    name: 'register-step-one',
    component: registerstepone
  },
  {
    path: '/register-step-three',
    name: 'register-step-three',
    component: registerstepthree
  },
  {
    path: '/register-step-two',
    name: 'register-step-two',
    component: registersteptwo
  },
  {
    path: '/register',
    name: 'register',
    component: register
  },
  {
    path: '/setting-edit-profile',
    name: 'setting-edit-profile',
    component: settingeditprofile
  },
  {
    path: '/setting-student-accounts',
    name: 'setting-student-accounts',
    component: settingstudentaccounts
  },
  {
    path: '/setting-student-billing',
    name: 'setting-student-billing',
    component: settingstudentbilling
  },
  {
    path: '/setting-student-delete-profile',
    name: 'setting-student-delete-profile',
    component: settingstudentdeleteprofile
  },
  {
    path: '/setting-student-invoice',
    name: 'setting-student-invoice',
    component: settingstudentinvoice
  },
  {
    path: '/setting-student-notification',
    name: 'setting-student-notification',
    component: settingstudentnotification
  },
  {
    path: '/setting-student-payment',
    name: 'setting-student-payment',
    component: settingstudentpayment
  },
  {
    path: '/setting-student-privacy',
    name: 'setting-student-privacy',
    component: settingstudentprivacy
  },
  {
    path: '/setting-student-referral',
    name: 'setting-student-referral',
    component: settingstudentreferral
  },
  {
    path: '/setting-student-security',
    name: 'setting-student-security',
    component: settingstudentsecurity
  },
  {
    path: '/setting-student-social-profile',
    name: 'setting-student-social-profile',
    component: settingstudentsocialprofile
  },
  {
    path: '/setting-student-subscription',
    name: 'setting-student-subscription',
    component: settingstudentsubscription
  },
  {
    path: '/setting-support-new-tickets',
    name: 'setting-support-new-tickets',
    component: settingsupportnewtickets
  },
  {
    path: '/setting-support-tickets',
    name: 'setting-support-tickets',
    component: settingsupporttickets
  },
  {
    path: '/setting-support-view-tickets',
    name: 'setting-support-view-tickets',
    component: settingsupportviewtickets
  },
  {
    path: '/student-profile',
    name: 'student-profile',
    component: studentprofile
  },
  {
    path: '/students-grid',
    name: 'students-grid',
    component: studentsgrid
  },
  {
    path: '/students-grid2',
    name: 'students-grid2',
    component: studentsgrid2
  },
  {
    path: '/students-list',
    name: 'students-list',
    component: studentslist
  },
  {
    path: '/support',
    name: 'support',
    component: support
  },
  {
    path: '/term-condition',
    name: 'term-condition',
    component: termcondition
  },
  {
    path: '/transactions-instructor',
    name: 'transactions-instructor',
    component: transactionsinstructor
  },
  {
    path: '/transactions-student',
    name: 'transactions-student',
    component: transactionsstudent
  },
  {
    path: '/under-construction',
    name: 'under-construction',
    component: underconstruction
  },
  {
    path: '/verification-code',
    name: 'verification-code',
    component: verificationcode
  },
  {
    path: '/view-invoice',
    name: 'view-invoice',
    component: viewinvoice
  },
  {
    path: '/wishlist',
    name: 'wishlist',
    component: wishlist
  },
  {
    path: '/withdrawal-instructor',
    name: 'withdrawal-instructor',
    component: withdrawalinstructor
  },
  {
    path:'/change-password-email',
    name: 'change-password-email',
    component: changePasswordEmail
  },
  {
    path:'/debate-regist',
    name: 'debate-regist',
    component: debateRegist
  },
  {
    path:'/debate-modify',
    name: 'debate-modify',
    component: debateModify
  },
  {
    path: '/debatemain',
    name: 'debatemain',
    component: debatemain
  },
  {
    path: '/debatewaiting',
    name: 'debatewaiting',
    component: debatewaiting
  },
  {
    path: '/debate',
    name: 'debate',
    component: debate
  },
];
export const router = createRouter({
    history: createWebHistory(),
    linkActiveClass: 'active',
    routes
});
