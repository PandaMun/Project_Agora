<template>
    <!-- Main Wrapper -->
    <div class="main-wrapper">

      <layouts></layouts>
       
            <!-- Page Wrapper -->
			<div class="page-content">
				<div class="container">
					<div class="row">

            <studentsidebar></studentsidebar>
						
						<!-- Instructor Dashboard -->
						<div class="col-xl-9 col-lg-8 col-md-12">

              <div class="row">
                <div class="col-md-12">
                  <div class="settings-widget">
                    <div class="settings-inner-blk p-0">
                      <div class="sell-course-head comman-space">
                        <div style="text-align: start">
                          <h3>이전 참여 목록</h3>
                        </div>
                        <div v-if="check" style="text-align: end">
                          <a class="btn btn-primary download-link attach-input" :href="'https://i8c205.p.ssafy.io/api/v1/certification/' + userEmail">증명서 발급하기</a>

                        </div>
                      </div>
                      <div class="comman-space pb-0">
                        <div class="settings-tickets-blk course-instruct-blk table-responsive">

                          <!-- Referred Users-->
                          <table class="table table-nowrap mb-0">
                            <thead>
                            <tr>
                              <th>날짜</th>
                              <th>주제</th>
                              <th>역활</th>
                              <th>소요 시간</th>
                              <th>토론 평가 확인</th>
                            </tr>
                            </thead>
                            <tbody v-for="item in contents">
                            <tr>

                                <td>{{item.date}}</td>
                              <td>{{ item.title }}</td>
                              <td>{{item.role}}</td>
                              <td>{{item.activeTime}}</td>
                              <td>
                              <button type="button" class="btn btn-cart" data-bs-toggle="modal" :data-bs-target="'#Modal' + item.id" @click="showModal=true, getVoteResult(item.debateId)">투표 결과 확인하기</button>
                              </td>
                            </tr>
                              <!-- 모달창 오픈 -->


                              <div class="modal-styles modal fade" :id="'Modal' + item.id" tabindex="-1" aria-labelledby="addpaymentMethod" aria-hidden="true">
                                <div class="modal-dialog">
                                  <div class="modal-content">
                                    <div class="modal-header">
                                      <h5 class="modal-title" id="addpaymentMethod"> 토론 : {{vote_result.title}} </h5>
                                      <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="fa-regular fa-circle-xmark"></i></button>
                                    </div>
                                    <div class="modal-body">
                                      <div class="addpaymethod-form add-course-info">
                                        <form action="#" id="evaluation_modal_form"  name = "content">
                                          <div class="row">
                                            <div class="col-lg-12">
                                              <div v-if="point.length === 0">
                                                <h4> 토론 "{{vote_result.title}}"와 현재 유저에 대한 평가 정보가 없습니다. </h4>
                                              </div>
                                              <div class="form-group mb-0" v-else>

                                                <div class="from-group col-md-10">
                                                  <div>
                                                    <h5>분석력</h5>
                                                    <hr>
                                                    <div class="from-group">
                                                      <label class="form-control-label" >준비</label>
                                                      <div class="from-group row"  >
                                                        <label class="col-10 form-control-label" >자신의 주장에 대해 실질적인 자료와 데이터를 제시하였는가?</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                          <!--   point-->{{point[0]}}
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div class="from-group">
                                                      <label class="form-control-label" >근거</label>
                                                      <div class="from-group row"  >
                                                        <label class="col-10 form-control-label" >상대방의 의견을 비판할때 적절한 근거를 들어 명확하게 설명하였는가?</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                          <!--   point-->{{point[1]}}
                                                        </div>
                                                      </div>
                                                    </div>
                                                  </div>
                                                  <br>
                                                  <div>
                                                    <h5>전달력</h5>
                                                    <hr>
                                                    <div class="from-group">
                                                      <label class="form-control-label" >언어</label>
                                                      <div class="from-group row"  >
                                                        <label class="col-10 form-control-label" >자신의 주장을 발표할 때 적절한 언어적 요소를 활용하였는가?</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                          <!--   point-->{{point[2]}}
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div class="from-group">
                                                      <label class="form-control-label" >비언어</label>
                                                      <div class="from-group row"  >
                                                        <label class="col-10 form-control-label" >자신의 주장을 발표할 때 적절한 비언어적 요소를 활용하였는가?</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                          <!--   point-->{{point[3]}}
                                                        </div>
                                                      </div>
                                                    </div>
                                                  </div>

<br>

                                                  <div>
                                                    <h5>토론 규칙 준수</h5>
                                                    <hr>
                                                    <div class="from-group">
                                                      <label class="form-control-label" >주제</label>
                                                      <div class="from-group row"  >
                                                        <label class="col-10 form-control-label" >정해진 주제에 벗어난 발언을 하지 않았는가?</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                          <!--   point-->{{point[4]}}
                                                        </div>
                                                      </div>
                                                    </div>
                                                    <div class="from-group">
                                                      <label class="form-control-label" >시간</label>
                                                      <div class="from-group row"  >
                                                        <label class="col-10 form-control-label" >발언시간을 잘 관리하고 토론 규칙을 준수하여 발언하였는가?</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                          <!--   point-->{{point[5]}}
                                                        </div>
                                                      </div>
                                                    </div>
                                                  </div>



                                                </div>
                                              </div>
                                            </div>
                                          </div>
                                        </form>
                                      </div>
                                    </div>
                                    <div class="modal-footer me-auto">
                                      <!-- <button type="button" class="btn btn-modal-style btn-theme" @click ="handleClick()" >Submit</button> -->
                                      <button type="button" class="btn btn-modal-style btn-cancel" data-bs-dismiss="modal">평가창 닫기</button>
                                    </div>
                                  </div>
                                </div>
                              </div>






                            </tbody>
                          </table>
                          <!-- /Referred Users-->


                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="container text-center"  style="text-align: center;">
                <div class="row">
                  <div class="col-md-12">
                    <ul class="pagination lms-page">
                      <li v-for="index in totalPages" class="page-item first-page active">
                        <button class="page-link" @click="fetchData(index,10)">{{index}}</button>
                      </li>

                    </ul>
                  </div>
                </div>
              </div>

						</div>	
						<!-- /Instructor Dashboard -->
						
					</div>
				</div>
			</div>	
			<!-- /Page Wrapper -->
        <layouts1></layouts1>
       
    </div>
    <!-- /Main Wrapper -->
</template>
<script>

import {apiInstance} from "../../../api";
import {useStore} from "vuex";
import axios from "axios";
export default {
 components:{
 },

  data() {
    const store = useStore();
    return{
      userEmail : store.getters["userStore/checkUserInfo"].userEmail,
      username : store.getters["userStore/checkUserInfo"].name,
      showModal: false,
      file: null,
      fileData: null,
      check: false,
      history: {},
      category : '회원 기능',
      contents: [],
      pageable: {
        number: 0,
        size: 10,
      },
      totalElements: 0,
      totalPages: 0,
      debate_id : 1,
      vote_result : {
        id_1 : "상호평가",
        id_3 : "분석력"
      },
      questions : {}, // 평가 문항.
      point:[],
    }

  },
  computed: {

    canPrev() {
      return this.page.number > 0;
    },
    canNext() {
      return this.page.number < this.page.totalPages - 1;
    }
  },
      methods: {

        async certification() {
          const http = axios.create({
            // baseURL: process.env.VUE_APP_API_BASE_URL,
            baseURL: "https://i8c205.p.ssafy.io/api/v1",
            headers: {'Content-Type': 'application/pdf',
              'charset': 'utf-8'},
          });

          try {
            console.log(this.userEmail)
            const response = await http.get('/certification/'+ this.userEmail);
            console.log(response)
            this.fileData = response.data;
            const file = new Blob([this.fileData]);
            const fileURL = URL.createObjectURL(file);
            const link = document.createElement('a');
            link.href = fileURL;
            link.setAttribute('download', this.username + '_토론 활동 증명서.pdf');
            document.body.appendChild(link);
            link.click();
            link.remove();
          } catch (error) {
            console.error(error);
          }
        }, prevPage() {
          if (this.canPrev) {
            this.fetchData(this.page.number - 1, this.page.size);
          }
        },
        nextPage() {
          if (this.canNext) {
            this.fetchData(this.page.number + 1, this.page.size);
          }
        },
        async fetchData(pageNumber, pageSize) {
          const api = apiInstance();
          api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
          await api.get('/userDebates/pageable', { params: { page: pageNumber-1, size: pageSize } })
              .then(response => {
                console.log(response);
                if (response.data.totalElements > 0) {
                  this.check = true;
                }
                this.contents = response.data.content;
                this.pageable = response.data.pageable;
                this.last = response.data.last;
                this.totalPages = response.data.totalPages;
                console.log(response.data)
              });
        },
        async getVoteResult(id){
          console.log(id)
          const api = apiInstance();
          api.get('/debates/'+id)
              .then((response) => {
                if(response.status == 200){
                  console.log(response);
                  this.vote_result = response.data.data;
                  console.log('특정 토론 청중 평가 결과 로드 완료');
                  api.defaults.headers["Authorization"] = "Bearer " + sessionStorage.getItem("access-token");
                  api.get('/evaluations/debates/'+id)
                      .then((response) => {
                        if(response.status == 200){
                          console.log(response);
                          for(const item of response.data.contentTotal){
                            this.point.push(item.point)
                          }
                          console.log('평가 결과 로드 완료');
                          console.log(this.point)
                        } else if(response.status == 204){
                          console.log('현재 로그인 유저에 대한 평가 정보 없음.');
                        } else {
                          console.log(response);
                          console.log('정상 조회 실패');
                        }
                      }).catch((error)=>{
                    console.log(error);
                  });
                } else {
                  console.log(response);
                  console.log('정상 조회 실패')
                }



              }).catch((error)=>{
            console.log(error);
          });
        },
      },

        async mounted(){
          await this.fetchData(0,10)
          // const store = useStore();
          // this.userEmail = store.getters["userStore/checkUserInfo"].userEmail;
          // this.username = store.getters["userStore/checkUserInfo"].name;
        }


    }
</script>
