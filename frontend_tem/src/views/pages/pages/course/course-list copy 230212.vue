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
								<div class="col-lg-12 col-md-12 d-flex" v-for="(debate, index) in debate_list">
									<div class="course-box course-design list-course d-flex">
										<div class="product">
											<div class="product-img">
												<a href ="/course-details" @click="this.setDebateId(debate.debate_id)">
													<img class="img-fluid" alt="" src="../../../../assets/img/course/testImg.jpg">
													<!-- <img class="img-fluid" alt="../../../../assets/img/course/testImg.jpg" :src="debate.thumbnailUrl"> -->
												</a>
												<div class="price">
													<h3 :class="debate.state">{{ debate.state }}</h3>
												</div>
											</div>
											<div class="product-content">
												<div class="head-course-title">
													<h3 class="title"><router-link to="course-details">{{ debate.title }}</router-link></h3>
													<div class="all-btn all-category d-flex align-items-center">
														<router-link to="checkout" class="btn btn-primary">상세 보기</router-link>
													</div>
												</div>
												<div class="course-info border-bottom-0 pb-0 align-items-center">
													<div class="rating-img d-flex align-items-center pb-1">
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
														<!-- <router-link to="instructor-profile"><img :src="debate.ownerProfile" alt="" class="img-fluid"></router-link> -->
														<router-link to="instructor-profile"><img src="../../../../assets/img/user/temp_user2.png" alt="" class="img-fluid"></router-link>
														<!-- <router-link to="instructor-profile"><img :src="require(debate.ownerProfile)" alt="" class="img-fluid"></router-link> -->
														
														<div class="course-name">
															<!-- <h4><router-link to="instructor-profile">{{ debate.ownerName }}</router-link></h4> -->
															<!-- TODO : API 수정 이후 삼항 연산자 삭제 -->
															<h4><router-link to="instructor-profile">{{ debate.ownerName == undefined ? '아직 이름이 없습니다. API 수정 필요. ' : debate.ownerName }} </router-link></h4>
															<p>{{ debate.ownerDepartment}}</p>
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
			owners : [],
			debate_list : [],
        }
    },
    mounted(){
		this.getDebateList();
    },
    methods: {
		async getDebateList(){
			api.get('/debates')
			.then((response) => {
				console.log(response.data.data.content);
				this.debate_list = response.data.data.content;
            })
            .catch((error)=>{
                console.log(error);
                // 사용자를 선택하지 않거나, 없는 사용자를 선택하거나 등.
                alert("입력값을 확인해주세요")
            });
		},
		setDebateId(debate_id){
			console.log("store에 값 저장.");
			this.$store.dispatch('setToken', debate_id)
		}
    },
    
}
</script>