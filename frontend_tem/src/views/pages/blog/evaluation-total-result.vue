<template>
    <!-- Deposit Modal -->
    <div class="modal-styles modal fade" id="evaluationTotalMethod" tabindex="-1" aria-labelledby="addpaymentMethod" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h5 class="modal-title" id="addpaymentMethod"> {{ debate.title }} 토론 상호평가</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="fa-regular fa-circle-xmark"></i></button>
                </div>
                <div class="modal-body">
                    <div class="addpaymethod-form add-course-info">
                        <form action="#" id="evaluation_modal_form"  name = "content">
                            <div class="row">
                                <div class="col-lg-12">
                                    <div v-if="Object.keys(total_result).length === 0">
                                        <h4> 토론 "{{debate.title}}"와 현재 유저에 대한 평가 정보가 없습니다. </h4>
                                    </div>
                                    <div class="form-group mb-0" v-else>
                                        
                                        <div class="from-group col-md-10" v-for="division1 in questions.children">
                                            <div>
                                                <h4>{{ division1.codeName }}</h4>
                                                <div class="from-group" v-for="division2 in division1.children">
                                                    <label class="form-control-label" >{{ division2.codeName }}</label>
                                                    <div class="from-group row" v-for="(division3, index) in division2.children" >
                                                        <label class="col-10 form-control-label" >{{ division3.codeName }}</label>

                                                        <!-- 수정 필요 -->
                                                        <div class="col col-item">
                                                            <!-- {{index}} ==> {{ total_result[index].point }}  -->
                                                            {{ this.getQuestionPoint(division3.id) }}
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer me-auto">
                    <!-- <button type="button" class="btn btn-modal-style btn-theme" @click ="handleClick()" >Submit</button> -->
                    <button type="button" class="btn btn-modal-style btn-cancel" data-bs-dismiss="modal">평가 결과창 닫기</button>
                </div>
            </div>
        </div>
    </div>	   
    <!-- Deposit Modal -->
</template>

<script>    
import { apiInstance } from "/api/index.js";  
import {useStore} from "vuex";    

const api = apiInstance();

export default {
    setup(){
        const store = useStore();
        var debate = store.state.debate.debateInfo; // 현재 참여중인 토론. 필요에 따라 편집 사용.
        return {debate};
    },
    data(){
        return {
            point : [1, 2, 3, 4, 5],
            debate_title : '반려동물 어쩌구 제목',
            questions : {}, // 평가 문항.
            total_result : [ 
            ],
        }
    },
    mounted(){
        this.getQuetions();
        this.getTotalResult(this.debate.debateId);
    },
    methods: {
        // 기존 Back API를 그대로 사용하기 위한 quetion-point 매칭 함수. id에 해당하는 점수를 돌려준다.
        getQuestionPoint(id){
            for(var i = 0; i < this.total_result.length; i++){
                if(id != this.total_result[i].id) continue;
                return this.total_result[i].point;
            }
        },
        async getQuetions(){
        
        api.get('/evaluations/questions')
        .then((response) => {
            if(response.status == 200){
                console.log(response);
                this.questions = response.data;
                console.log('평가 문항 로드 완료');
            }
        }).catch((error)=>{
            console.log(error);
        });
        },
        async getTotalResult(debateId){
            api.defaults.headers["Authorization"] = "Bearer " + sessionStorage.getItem("access-token");
            api.get('/evaluations/debates/'+debateId)
            .then((response) => {
                if(response.status == 200){
                    console.log(response);
                    this.total_result = response.data.contentTotal;
                    console.log('평가 결과 로드 완료');
                    
                } else if(response.status == 204){
                    console.log('현재 로그인 유저에 대한 평가 정보 없음.');
                } else {
                    console.log(response);
                    console.log('정상 조회 실패');
                }
            }).catch((error)=>{
                console.log(error);
            });
        },
    },
    
}
</script>