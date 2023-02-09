<template>
    <!-- Deposit Modal -->
    <div class="modal-styles modal fade" id="voteRegisterMethod" tabindex="-1" aria-labelledby="addpaymentMethod" aria-hidden="true" data-backdrop="static">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h5 class="modal-title" id="addpaymentMethod">{{ this.debate_info.debate_title }} 투표 상호평가</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="fa-regular fa-circle-xmark"></i></button>    
            </div>
                    <div class="modal-body">
                        <div class="addpaymethod-form add-course-info">
                            <form action="#">
                                <div class="row">
                                    <div class="col-lg-12">
                                    
                                        <!-- Deposit Method -->
                                        <div class="radio-with-img">
                                            <h4 class="modal-title"> 찬성하는 의견을 선택해주세요 </h4>
                                            <div class="radio-deposit-item " v-for="(pannel, index) in debate_info.pannel" :key="index">
                                                <input type="radio" name="perspective_id" :id="'perspective-id-'+index" :value="pannel.perspective_id" class="ng-valid ng-dirty ng-touched ng-empty" aria-invalid="false" v-model="perspective_id">
                                                <label :for="'perspective-id-'+index">
                                                {{pannel.prespective_name}}
                                                </label>
                                            </div>
                                        </div>
                                        <!-- /Deposit Method -->
                                        
                                    </div>
                                    <div class="form-group mb-0">

                                        <div class=" radio-with-img">
                                            <h4 class="modal-title"> 이 토론의 MVP를 선택해주세요. </h4>
                                            <div class="radio-deposit-item" v-for="(pannel, p_index) in debate_info.pannel" :key="p_index">
                                                <div v-for="(member, m_index) in pannel.memberList" :key="m_index">
                                                    <input type="radio" name="mvp_id" :id="'p'+p_index+'_mvp-index-'+m_index" :value="member.user_email" class="ng-valid ng-dirty ng-touched ng-empty" aria-invalid="false" v-model="mvp_id">
                                                    <label :for="'p'+p_index+'_mvp-index-'+m_index">
                                                    <img src="../../../assets/img/deposit-03.jpg" alt="" class="img-fluid" >
                                                    {{member.user_name}}
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /Deposit Method -->

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer me-auto  text-center">
                        <button type="button" class="btn btn-modal-style btn-theme" @click ="registerVote()" >Submit</button>
                    <button type="button" class="btn btn-modal-style btn-cancel" data-bs-dismiss="modal"> 투표하기 창 닫기</button>
                    </div>
            </div>
        </div>
    </div>	   
    <!-- Deposit Modal -->
    </template>

<script>

import { apiInstance } from "/api/index.js";    

export default {
    setup(){
        // TODO : debateStore에서 현재 토론 정보 받아오기. 토론 참여시 GET 요청으로 받아와 state로 저장되어 있어야한다.
        const debate_info = {
            debate_id : "1",
            debate_title : "반려동물 보유세 필요한가?",
            pannel : [
                {
                    perspective_id : 1,
                    prespective_name : "반려동물 보유세는 필요하다.",
                    memberList : [
                        {user_email : "agree1@naver.com", user_name : "김찬성1"},
                        {user_email : "agree2@naver.com", user_name : "김찬성2"},
                        {user_email : "agree3@naver.com", user_name : "김찬성3"},
                    ]
                },
                {
                    perspective_id : 2,
                    prespective_name : "반려동물 보유세는 필요하지 않다.",
                    memberList : [
                        {user_email : "ssafy@naver.com", user_name : "김싸피"},
                        {user_email : "human@naver.com", user_name : "박사람"},
                        {user_email : "puppy@naver.com", user_name : "김뽀삐"},
                    ]
                },
            ],
        };
        

        return {debate_info};
    },
    data(){
        return {
            mvp_id : '',
            perspective_id : '',
            isVoteSubmitted : false,
        }
    },
    methods: {
        async registerVote() {
            const api = apiInstance();
            
            api.defaults.headers["Authorization"] = "Bearer " + sessionStorage.getItem("access-token");
            if(this.perspective_id == ''){ 
                alert('찬성 의견을 선택해주세요')
                return;
            }
            if(this.mvp_id == '') {
                alert("mvp를 선택해주세요")
                return;    
            }
            
            
            await api.post(`/vote`, {
                debate_id : this.debate_info.debate_id,
                mvp_id : this.mvp_id,
                perspective_id : this.perspective_id,
            })
            .then((response) => {
                console.log(response);
                alert("청중 투표 완료");
                this.isVoteSubmitted = true;
                // TODO : 투표 완료 후 창 닫기
                // $('#voteRegisterMethod').modal("hide"); //닫기 
            })
            .catch((error)=>{
                console.log(error);
                // 사용자를 선택하지 않거나, 없는 사용자를 선택하거나 등.
                alert("입력값을 확인해주세요")
            });

        }
    },
}
</script>