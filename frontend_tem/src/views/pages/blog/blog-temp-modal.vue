<template>
    <!-- Deposit Modal -->
    <div class="modal-styles modal fade" id="depositMethod" tabindex="-1" aria-labelledby="addpaymentMethod" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h5 class="modal-title" id="addpaymentMethod"> {{ this.debate_info.debate_title }} 토론 상호평가</h5>
                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close"><i class="fa-regular fa-circle-xmark"></i></button>
                </div>
                <div class="modal-body">
                    <div class="addpaymethod-form add-course-info">
                        <form action="#" id="evaluation_modal_form"  name = "content">
                            <div class="row">
                                <div class="col-lg-12">
                                
                                    <!-- Deposit Method -->
                                    <div class="radio-with-img p-2">
                                        <h4> 평가 대상  </h4>
                                        <div class = "radio-wrapper text-center">
                                            <p class="radio-deposit-item" v-for= "(member, index) in debate_info.pannel[0].memberList" :key="index" :id = "'evaluated_user_email_'+member.user_email">
                                                <input type="radio" name="evaluated_id" :id="'deposit-type-'+index" :value="member.user_email" class="ng-valid ng-dirty ng-touched ng-empty" aria-invalid="false" v-model="evaluated_id">
                                                <label :for="'deposit-type-'+index">
                                                <img src="../../../assets/img/profile-01.jpg" alt="" class="img-fluid" >
                                                {{ member.user_name }}
                                                </label>
                                            </p>
                                        </div>
                                    </div>
                                    <!-- /Deposit Method -->
                                    
                                    <div class="form-group mb-0" v-for=" member in debate_info.pannel[0].memberList">
                                        
                                        <div class="from-group" v-for="division1 in questions.children" v-show ="evaluated_id == member.user_email">
                                            <div>
                                                <h4>{{ division1.codeName }}</h4>
                                                <div class="from-group" v-for="division2 in division1.children">
                                                    <label class="form-control-label" >{{ division2.codeName }}</label>
                                                    <div class="from-group row" v-for="division3 in division2.children">
                                                        <input type="hidden" :name = "'parent_id_'+member.user_email" :value="division2.id" >
                                                        <input type="hidden" :name = "'id_'+member.user_email" :value="division3.id">
                                                        <label class="col-10 form-control-label" >{{ division3.codeName }}</label>
                                                        <div class="col col-item">
                                                            <vue-select :options="point" placeholder="Choose" :name = "'point_'+member.user_email"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <hr>
                                        </div>
                                    </div>
                                    <div class = "text-center" v-if="evaluated_id==''">
                                        <h2> 평가 대상을 선택해주세요 </h2>
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
            point : [1, 2, 3, 4, 5],
            evaluated_id : '',
            questions : {}, // 평가 문항.
        }
    },
    mounted(){
        this.getQuetions();
    },
    methods: {
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
        async handleClick () {
            let form = {
                debate_id : '',
                evaluated_id : '',
                content : [
                ],
            };

            let formData = new FormData($("#evaluation_modal_form")[0]);

            // formData 보내는 방법(그대로, serialize, stringify 등)이 안통함. 기존의 객체 양식과 전송시 형태가 다름. 아래는 임시 방편.
            form.debate_id = this.debate_info.debate_id;
            form.evaluated_id = this.evaluated_id;
            let parent_id_list = formData.getAll('parent_id_'+this.evaluated_id);
            let id_list = formData.getAll('id_'+this.evaluated_id);
            let point_list = formData.getAll('point_'+this.evaluated_id);
            console.log(parent_id_list);
            console.log(id_list);
            console.log(point_list);

            if(this.evaluated_id == ''){
                alert('평가 대상을 선택해주세요');
                return;
            } else if(parent_id_list.length != point_list.length){
                alert('모든 평가 점수를 입력해야합니다.')
                return;
            }

            for(var i = 0; i < parent_id_list.length; i++){
                let parent_id = parent_id_list[i];
                let id = id_list[i];
                let point = point_list[i];
                form.content[i] = {parent_id, id, point};
            }
            console.log(form);


            api.defaults.headers["Authorization"] = "Bearer " + sessionStorage.getItem("access-token");
            await api.post(`/evaluations`, form)
            .then((response) => {
                console.log(response);
                alert("유저 평가 생성 완료");
                // $('input[name="evaluated_id"]:checked').prop('checked', false); // 전송이끝나면 평가가 끝난 대상을 비활성화 시킨다.
                // $('input[name="evaluated_id"]:checked').removeAttr("checked");  // 전송이 끝나면 자동으로 unchecked가 된다.
                // $('input[name="evaluated_id"]:checked').prop('disabled', true); // 라디오 버튼 비활성화
                // $('input[name="evaluated_id"]:checked').css('display', 'none'); // 라디오 버튼 안보이기
                document.getElementById('evaluated_user_email_'+this.evaluated_id).style.display = 'none'; // p태그 통째로 지워버리기 된당!!
                
                this.evaluated_id = '';
            })
            .catch((error)=>{
                console.log(error);
            });
        }
    },
    
}
</script>