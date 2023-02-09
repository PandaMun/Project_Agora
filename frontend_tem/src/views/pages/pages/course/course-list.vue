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
								<div class="col-lg-12 col-md-12 d-flex" v-for="debate in debate_list">
									<div class="course-box course-design list-course d-flex">
										<div class="product">
											<div class="product-img">
												<router-link to="course-details">
													<img class="img-fluid" alt="" src="../../../../assets/img/course/testImg.jpg">
                                                </router-link>
												<div class="price">
													<h3>$300 <span>$99.00</span></h3>
												</div>
											</div>
											<div class="product-content">
												<div class="head-course-title">
													<h3 class="title"><router-link to="course-details">{{ debate.debate_title }}</router-link></h3>
													<div class="all-btn all-category d-flex align-items-center">
														<router-link to="checkout" class="btn btn-primary">참여하기</router-link>
													</div>
												</div>
												<div class="course-info border-bottom-0 pb-0 d-flex align-items-center">
													<div class="rating-img d-flex align-items-center">
														<img src="../../../../assets/img/icon/icon-01.svg" alt="">
														<p>12+ Lesson</p>
													</div>
													<div class="course-view d-flex align-items-center">
														<img src="../../../../assets/img/icon/icon-02.svg" alt="">
														<p>9hr 30min</p>
													</div>
												</div>
												<div class="rating">							
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star me-1"></i>
													<span class="d-inline-block average-rating"><span>4.0</span> (15)</span>
												</div>
												<div class="course-group d-flex mb-0">
													<div class="course-group-img d-flex">
														<!-- <router-link to="instructor-profile"><img :src="debate.owner_thumbnail" alt="" class="img-fluid"></router-link> -->
														<router-link to="instructor-profile"><img src="../../../../assets/img/user/temp_user1.png" alt="" class="img-fluid"></router-link>
														<div class="course-name">
															<h4><router-link to="instructor-profile">{{ debate.owner_name }}</router-link></h4>
															<p>{{ debate.owner_department_position }}</p>
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
								<div class="col-lg-12 col-md-12 d-flex">
									<div class="course-box course-design list-course d-flex">
										<div class="product">
											<div class="product-img">
												<router-link to="course-details">
													<img class="img-fluid" alt="" src="../../../../assets/img/course/course-12.jpg">
                                                </router-link>
												<div class="price">
													<h3>$300 <span>$99.00</span></h3>
												</div>
											</div>
											<div class="product-content">
												<div class="head-course-title">
													<h3 class="title"><router-link to="course-details">Learn Angular Fundamentals From beginning to advance lavel</router-link></h3>
													<div class="all-btn all-category d-flex align-items-center">
														<router-link to="checkout" class="btn btn-primary">BUY NOW</router-link>
													</div>
												</div>
												<div class="course-info border-bottom-0 pb-0 d-flex align-items-center">
													<div class="rating-img d-flex align-items-center">
														<img src="../../../../assets/img/icon/icon-01.svg" alt="">
														<p>12+ Lesson</p>
													</div>
													<div class="course-view d-flex align-items-center">
														<img src="../../../../assets/img/icon/icon-02.svg" alt="">
														<p>9hr 30min</p>
													</div>
												</div>
												<div class="rating">							
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star filled me-1"></i>
													<i class="fas fa-star me-1"></i>
													<span class="d-inline-block average-rating"><span>4.0</span> (15)</span>
												</div>
												<div class="course-group d-flex mb-0">
													<div class="course-group-img d-flex">
														<router-link to="instructor-profile"><img src="../../../../assets/img/user/temp_user2.png" alt="" class="img-fluid"></router-link>
														<div class="course-name">
															<h4><router-link to="instructor-profile">Jesse Stevens</router-link></h4>
															<p>Instructor</p>
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

import { apiInstance } from "/api/index.js";      

const api = apiInstance();

export default {
    setup(){

    },
    data(){
        return {
            debate_list : [
			{
					debate_id : 1,
					debate_mode : "CEDA",
					debate_title : "반려동물 보유세 필요한가?",
					call_start_time : "2023-01-11 13:00:00",
					call_end_time : "2023-01-11 16:00:00",
					thumbnail_url : "../../../../assets/img/course/testImg.jpg",
					owner_email : "ssafy@naver.com",
					owner_name : "김싸피",
					owner_thumbnail : "../../../../assets/img/user/temp_user1.png",
					owner_department_position : "싸피고등학교 선생님",
					status : "active",
				},
				{
					debate_id : 2,
					debate_mode : "TL",
					debate_title : "향후 자율 주행이 보급될 경우 운전면허는 필수인가?",
					call_start_time : "2023-02-11 13:00:00",
					call_end_time : "2023-02-11 16:00:00",
					thumbnail_url : "../../../../assets/img/course/testImg2.jpg",
					owner_email : "debate@naver.com",
					owner_name : "김토론",
					owner_thumbnail : "../../../../assets/img/user/temp_user2.png",
					owner_department_position : "토론고등학교 선생님",
					status : "inactive",
				},
				{
					debate_id : 3,
					debate_mode : "CEDA",
					debate_title : "인공지능 법관 필요한가?",
					call_start_time : "2023-02-20 18:00:00",
					call_end_time : "2023-02-20 20:00:00",
					thumbnail_url : "../../../../assets/img/course/testImg3.jpg",
					owner_email : "fake@naver.com",
					owner_name : "김철수",
					owner_thumbnail : "../../../../assets/img/user/temp_user3.png",
					owner_department_position : "광주고등학교 선생님",
					status : "active",
				},
			]
        }
    },
    mounted(){
    },
    methods: {
        
    },
    
}
</script>