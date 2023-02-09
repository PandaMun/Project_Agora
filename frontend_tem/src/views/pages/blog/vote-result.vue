<template>
    <!-- Deposit Modal -->
    <div class="modal-styles modal fade" id="voteResultMethod" tabindex="-1" aria-labelledby="addpaymentMethod" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h5 class="modal-title" id="addpaymentMethod"> {{ vote_result.debate_title }} 토론</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="fa-regular fa-circle-xmark"></i></button>
                </div>
                <div class="modal-body">
                    <div class="addpaymethod-form add-course-info">
                        <form action="#" id="evaluation_modal_form"  name = "content">

                            <div class="form-group mb-0">
                                
                                <h4>토론 "{{ vote_result.debate_title }}" 투표 결과</h4>
                                <label class="form-control-label" >mvp name : {{ vote_result.mvp_name }}</label>

                                <div class="from-group col-md-10" v-for="perspective in vote_result.perspective_list">
                                    <div class = "row">
                                            <label class="form-control-label" >{{ perspective.perspective_name }}</label>
                                            <label class="form-control-label" >{{ perspective.percent*100 }} %</label>
                                        </div>
                                    </div>
                                </div>

                        </form>
                    </div>
                </div>
                <div class="modal-footer me-auto">
                    <!-- <button type="button" class="btn btn-modal-style btn-theme" @click ="handleClick()" >Submit</button> -->
                    <button type="button" class="btn btn-modal-style btn-theme" @click ="handleClick()" >평가 전송</button>
                    <button type="button" class="btn btn-modal-style btn-cancel" data-bs-dismiss="modal">평가창 닫기</button>
                </div>
            </div>
        </div>
    </div>	   
    <!-- Deposit Modal -->
</template>

<script>    

import { apiInstance } from "/api/index.js";      

const api = apiInstance();

export default {
    setup(){

    },
    data(){
        return {
            debate_id : 1,
            vote_result : {
                debate_id : 1,
                debate_title : "반려동물 보유세 필요한가?",
                mvp_id : "ssafy@naver.com",
                mvp_name : "김싸피",
                perspective_list : [
                    {perspective_name : "반려동물 보유세 필요하다.", percent : "0.3"},
                    {perspective_name : "반려동물 보유세 필요없다.", percent : "0.7"},
                ],
            }
        }
    },
    mounted(){
        this.getVoteResult();
    },
    methods: {
        async getVoteResult(){
            api.get('/vote/debates/'+this.debate_id)
            .then((response) => {
                if(response.status == 200){
                    console.log(response);
                    this.vote_result = response.data;
                    console.log('특정 토론 청중 평가 결과 로드 완료');
                    
                } else {
                    console.log(response);
                    console.log('정상 조회 실패')
                }
            }).catch((error)=>{
                console.log(error);
            });
        },
    },
    
}
</script>