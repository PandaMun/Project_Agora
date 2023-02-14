<template>
	<!-- Main Wrapper -->
	<div class="main-wrapper">
  
	  <layouts2></layouts2>
	  <coursegridbreadcrumb></coursegridbreadcrumb>
	  <!-- Course -->
	  <section class="course-content">
		<div class="container">
		  <div class="row">
			<div class="col-lg-9">
  
			  <coursefilter></coursefilter>
  
			  <div class="row">
				<div class="col-lg-12 col-md-12 d-flex" v-for="(debate, index) in data.debate_list">
				  <div class="course-box course-design list-course d-flex">
					<div class="product">
					  <div class="product-img">
						<router-link to="course-details">
						  <img class="img-fluid" alt="" src="../../../../assets/img/course/testImg.jpg" @click="setDebateId(debate.debateId)">
						</router-link>
						<div class="price">
						  <!-- <h3>$300 <span>$99.00</span></h3> -->
						  <h3 :class="debate.state">{{ debate.state }}</h3>
						</div>
					  </div>
					  <div class="product-content">
						<div class="head-course-title pt-2 pb-2">
						  <h3 class="title">
							<router-link to="course-details">[No.{{debate.debateId}}] {{ debate.title }}</router-link>
						  </h3>
						</div>
						<div class="course-info border-bottom-0 pb-0 align-items-center">
							<div class="rating-img d-flex align-items-center pb-2">
								<img src="../../../../assets/img/icon/icon-01.svg" alt="">
								<p> Mode : {{ debate.debateMode }}</p>
							</div>
							<div class="course-view d-flex align-items-center  pl-2">
								<img src="../../../../assets/img/icon/icon-02.svg" alt="">
								<p>{{ debate.callStartTime }} ~ {{ debate.callEndTime }}</p>
						  	</div>
						</div>
						<div class="course-group d-flex mb-0">
						  <div class="course-group-img d-flex">
							<!-- <router-link to="instructor-profile"><img :src="debate.owner_profile" alt="" class="img-fluid"></router-link> -->
							<router-link to="instructor-profile"><img src="../../../../assets/img/user/temp_user2.png"
																	  alt="" class="img-fluid"></router-link>
							<!-- <router-link to="instructor-profile"><img :src="debate.owner_profile" alt="" class="img-fluid"></router-link> -->
  
							<div class="course-name">
							  <h4>
								<router-link to="instructor-profile">{{ debate.ownerName }}</router-link>
							  </h4>
							  <p>{{ debate.ownerDepartment }}</p>
							</div>
						  </div>
						  <div class="course-share d-flex align-items-center justify-content-center">
							<a href="javascript:;"><i class="fa-regular fa-heart"></i></a>
						  </div>
						</div>
					  </div>
					</div>
				  </div>
				</div>
			  </div>
  
			  <pagination></pagination>
  
			</div>
			<gridlistsidebar></gridlistsidebar>
		  </div>
		</div>
	  </section>
	  <!-- /Course -->
  
	  <layouts1></layouts1>
  
	</div>
	<!-- /Main Wrapper -->
  </template>
  <script>
  
  import {apiInstance} from "/api/index.js";
  import {useStore} from "vuex";
  import {reactive, watch} from "vue";
  import {useRouter} from "vue-router";
  
  const api = apiInstance();
  
  export default {
	setup() {
	  const store = useStore();
	  const router = useRouter();
	  const data = reactive({
		debate_list : [],
	  })
	  
	  watch(
		  // pretend you have a getData getter in store
		  () => store.getters["debate/getDebateList"],
		  (val, oldVal) => {
			data.debate_list = store.getters["debate/getDebateList"];
		  }
	  )

	  function setDebateId(debateId){
		console.log(debateId);
		if(debateId == undefined) debateId = 3; // 토론 ID가 없을시 대신 표시될 임시 데이터.
		store.commit('debate/SET_DEBATE_ID', debateId)
	}
  
	  return {data, setDebateId}
	},
	data() {
	  return {
		owners: [],
	  }
	},
	mounted() {
  
	},
	methods: {
		
	},
  
  }
  </script>