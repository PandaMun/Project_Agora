<template>
    <!-- Main Wrapper -->
    <div class="main-wrapper">
        
        <layouts2></layouts2>
        <coursedetails :debate="data.debate"></coursedetails>
        <inner-page :debate="data.debate" :debate_detail="data.debate_detail"></inner-page>
            <!-- Course Content -->
			<section class="page-content course-sec">
				<div class="container">
				
					<div class="row">
						<div class="col-lg-8">
						
							<!-- Overview -->
							<div class="card overview-sec">
								<div class="card-body">
									<h5 class="subs-title">Overview</h5>
									<h6>토론 설명</h6>
									<p>{{ data.debate.description }}</p>
									<br/>
									<h6>관련 칼럼</h6>
									<ul class="mb-0">
										<li><a href="https://www.hani.co.kr/arti/animalpeople/companion_animal/1055181.html">[한겨레] “반려동물 보유세 어떻게 생각하시나요” 국민에 묻는다</a></li>
										<li><a href="https://www.sejungilbo.com/news/articleView.html?idxno=39319"> [세정일보] [칼럼] ‘반려동물 보유세’ 도입 시급하다</a></li>
										<li class="mb-0"><a href="https://namu.wiki/w/%EB%B0%98%EB%A0%A4%EB%8F%99%EB%AC%BC%20%EB%B3%B4%EC%9C%A0%EC%84%B8">[나무위키] 반려동물 보유세</a></li>
									</ul>
								</div>
							</div>
							<!-- /Overview -->
							
							<div class="card overview-sec" v-if="Object.keys(data.vote_result).length != 0">
								<div class="card-body">
									<h5 class="subs-title">토론 투표 결과</h5>
									<h6>이 토론의 MVP</h6>
									<div class="about-instructor">
										<div class="abt-instructor-img">
											<router-link to="instructor-profile"><img src="../../../../assets/img/user/temp_user1.png" alt="img" class="img-fluid"></router-link>
											<!-- <router-link to="instructor-profile"><img :src="vote_result.mvp_profile" alt="img" class="img-fluid"></router-link> -->
										</div>
										<div class="instructor-detail">
											<h5><router-link to="instructor-profile">{{ data.vote_result.mvp_name }} </router-link></h5>
										</div>
									</div>
									<br/>

									<h6> 청중 투표 결과 </h6>
									<p v-for="perspective in vote_result.perspective_list">
										{{ perspective.perspective_name }} : {{ Math.round(perspective.percent * 1000)/10 }} %
									</p>
								</div>
							</div>

							<div class="card overview-sec" v-if="data.summary != ''"> 
								<div class="card-body">
									<h5 class="subs-title"> Summary </h5>
									<p> {{ data.summary }} </p>
								</div>
							</div>

							
							
						</div>	
						
						<div class="col-lg-4">
							<div class="sidebar-sec">
							
								<!-- Video -->
								<div class="video-sec vid-bg">
									<div class="card">
										<div class="card-body">
											<a class="video-thumbnail" data-fancybox="">
												<img class="" src="../../../../assets/img/course/testImg.jpg" alt="">
												<!-- <img class="" :src="debate.thumbnail_url" alt=""> -->
											</a>
											<div class="video-details">
												<div class="row gx-2">
													<div class="col-md-6">
														<router-link to="course-wishlist" class="btn btn-wish w-100"><i class="feather-heart"></i> 위시리스트에 넣기 </router-link>
													</div>
													<div class="col-md-6">
														<a href="javascript:;" class="btn btn-wish w-100"><i class="feather-share-2"></i> 공유하기 </a>
													</div>
												</div>
												<button class="btn btn-primary w-100 mb-2" v-if="data.debate.state == 'active'" @click ="setDebateLink('찬성')"> 찬성측으로 입장하기 </button>
												<button class="btn btn-info w-100 mb-2" v-if="data.debate.state == 'active'" @click ="setDebateLink('반대')"> 반대측으로 입장하기 </button>
												<button class="btn btn-dark w-100 mb-2" v-if="data.debate.state == 'active'" @click ="setDebateLink('사회자')"> 사회자로 입장하기 </button>
												<router-link to="checkout" class="btn btn-enroll w-100 disabled" v-else-if="data.debate.state == 'inactive'" > 토론 준비 중 </router-link>
												<router-link to="checkout" class="btn btn-enroll w-100" v-else-if="data.debate.state == 'in ready'" > 대기열 입장 </router-link>
												<router-link to="checkout" class="btn btn-dark w-100 disabled" v-else> 종료됨 </router-link> <!-- state : closed -->
											</div>
										</div>
									</div>
								</div>
								<!-- /Video -->
								
								
								<!-- Features -->
								<div class="card feature-sec" v-if="Object.keys(data.debate_files).length != 0">
									<div class="card-body">
										<div class="cat-title">
											<h4> 사용된 파일 </h4>
										</div>
										<div class="perspective wrapper pb-2" v-for="(perspective, index) in data.debate_files" :key ="index">
											<h6 ><img src="../../../../assets/img/icon/chapter.svg" class="me-2" alt=""><strong>{{ perspective.perspective_name }}</strong></h6>
											<ul class="mb-0">
												<li v-for="file in perspective.files">{{ file.file_name }}</li>
											</ul>
											<br/>
										</div>
									</div>
								</div>
								<!-- /Features -->
					
								
							</div>
						</div>
					</div>	
				</div>
			</section>
			<!-- /Pricing Plan -->
        <layouts1></layouts1>
       
    </div>
    <!-- /Main Wrapper -->
</template>

<script>    
import { apiInstance } from "/api/index.js";      
import {useStore} from "vuex";
import {onMounted, reactive} from 'vue'
import { useRouter} from 'vue-router'

const api = apiInstance();

export default {
    setup(){
		const store = useStore();
		const router = useRouter();
		const data = reactive({
			// debateId : store.getters["debate/getDebateId"], 
			debateId : store.state.debate.debateId,
			debate : {},

			// TODO : 현재 페이지 진입시 debateId를 통해서 상세정보 요청. 혹은 기존 토론 정보 조회 개선.
			debate_detail : { 	  
				category : "정치",
				enroll_count : 6,
			},

			// TODO : 종료된 토론(state==closed)에 대해서만 데이터를 로드한다. 일부 API 구현 필요.
			vote_result : {}, 
			debate_files : [],
			summary : '',
		});
		


		onMounted(()=>{
			// data.debateId = store.getters["debate/getDebateId"];
			getDebateInfo();
		})

		const getDebateInfo  = async () => {
			console.log(data.debateId);
			await api.get('/debates/'+data.debateId)
            .then((response) => {
                if(response.status == 200){
                    data.debate = response.data.data;
                    console.log('특정 토론 정보 조회 완료');
					console.log(data.debate);
					checkState();
                } else {
                    console.log(response);
                    console.log('정상 조회 실패')
                }
            }).catch((error)=>{
                console.log(error);
            });
		}
		
		const setDebateLink = (position) => {
			const debate_link = {
				name : 'debatemain',
				query : {
					debateId : data.debateId,
					name : store.getters["user/checkUserInfo"],
					title : data.debate.title,
					position : position,
					roomType : data.debate.debateMode,
					time : 100,
				},
			}
			router.push(debate_link);
		}

		const checkState = async () => {
			if(data.debate.state == 'closed'){
				getVoteResult();
				getDebateFiles();
				getSummary();
			}
		}

		const getVoteResult = async () => {
            api.get('/vote/debates/'+data.debateId)
            .then((response) => {
                if(response.status == 200){
                    console.log(response);
                    data.vote_result = response.data;
                    console.log('특정 토론 청중 평가 결과 로드 완료');
                    
                } else {
                    console.log(response);
                    console.log('정상 조회 실패')
                }
            }).catch((error)=>{
                console.log(error);
            });
        }

		const getDebateFiles = async () => {
			// 토론의 각 패널에서 사용된(업로드된) 파일들
			data.debate_files = [
				{
					perspective_id : 143,
					perspective_name : "반려동물 보유세는 필요하다.",
					files : [
						{file_name : "143번측 파일명 1", file_url : "../../../../assets/img/user/temp_user2.png"},
						{file_name : "143번측 파일명 2", file_url : "../../../../assets/img/user/temp_user3.png"}
					]
				},  	
				{
					perspective_id : 144,
					perspective_name : "반려동물 보유세는 필요하지않다.",
					files : [
						{file_name : "144번측 파일명 1", file_url : "../../../../assets/img/user/temp_user2.png"},
						{file_name : "144번측 파일명 2", file_url : "../../../../assets/img/user/temp_user3.png"}
					]
				}, 

			];
		}

		const getSummary = async ()=>{
			data.summary = 
			'이건 대충 적은 요약글입니다. 적을 내용이 없어서 한겨레 자율주행 뉴스 글을 막 끌고 왔습니다. 어쩌구 저쩌구 '+
			'전문가들이 레벨 4∼5 수준의 자율주행 기술 구현이 시기상조라고 보는 데에는 몇가지 이유가 있다. 우선 막대한 비용이다. 김일평 삼성화재 자동차보험전략팀장은 “스마트 도로 시설, 모바일 네트워크 등 자율주행 인프라 구축에 천문학적 비용이 필요하다. 이 때문에 완전 자율주행차 시대는 굉장히 늦게 올 것”이라고 내다봤다. 실제 자율주행 기술이 상용화하려면 도로 위의 많은 변수를 통제해야 한다. 자율주행차에 적합한 전용 도로를 깔고 도로를 주행하는 차량 대부분이 사람의 개입을 최소화한 자율차여야 안정적인 기술 구현이 가능하다.'
		}


		return {store, data, setDebateLink};
    },    
}
</script>