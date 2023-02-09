<template>

    <!-- Main Wrapper -->
    <div class="main-wrapper">
        
        <layoutslogin></layoutslogin>
       
        <blogdetails></blogdetails>

        <!-- Blog Details -->
        <section class="course-content">
                    

            <div class="container">
                <div class="row">
                    <div class="col-lg-9 col-md-12">

                        <!-- Blog Post -->
                        <div class="blog">
                            <div class="blog-image">
                                <router-link to="blog-details"><img class="img-fluid" src="../../../assets/img/blog-banner.jpg" alt="Post Image"></router-link>
                            </div>
                            <div class="blog-info clearfix">
                                <div class="post-left">
                                    <ul>
                                        <li>
                                            <div class="post-author">
                                                <router-link to="instructor-profile"><img src="../../../assets/img/user/user.jpg" alt="Post Author"> <span>Ruby Perrin</span></router-link>
                                            </div>
                                        </li>
                                        <li><img class="img-fluid" src="../../../assets/img/icon/icon-22.svg" alt="">April 20, 2022</li>
                                        <li><img class="img-fluid" src="../../../assets/img/icon/icon-23.svg" alt="">Programming, Web Design</li>
                                    </ul>
                                </div>
                            </div>
                            <h3 class="blog-title"><router-link to="blog-details">Hi! I'm Temp Page!!!!!</router-link></h3>
                            <div class="blog-content">
                                
                                <div class="all-btn all-category d-flex align-items-center">
                                    <div class="btn btn-primary" @click="this.getEval"> 특정 유저에 대한 평가 취합 결과 조회하기 </div>
                                </div>
                                <br/>
                                <div class="all-btn all-category d-flex align-items-center" >
                                    <div class="btn btn-primary" @click="this.sendVote"> 청중 투표 하기 </div>
                                </div>
                                <br/>
                                <div class="all-btn all-category d-flex align-items-center">
                                    <div class="btn btn-primary" @click="this.getVoteResult(`${this.debate_id}`)"> 토론의 청중 투표 결과 확인하기 </div>
                                </div>
                                <br/>
                                <br/>

                                <div class="ticket-btn-grp">
									<a href="javascript:;" data-bs-toggle="modal" data-bs-target="#depositMethod">평가 생성하기</a>
								</div>
                                <br/>
                                <div class="ticket-btn-grp">
									<a href="javascript:;" data-bs-toggle="modal" data-bs-target="#voteRegisterMethod">투표 생성하기</a>
								</div>
                                <br/>
                                <div class="ticket-btn-grp">
									<a href="javascript:;" data-bs-toggle="modal" data-bs-target="#voteResultMethod">투표 결과 확인하기</a>
								</div>
                                <br/>
                                <div class="ticket-btn-grp">
									<a href="javascript:;" data-bs-toggle="modal" data-bs-target="#evaluationTotalMethod">평가 확인하기</a>
								</div>
                            </div>
                        </div>
                        <!-- /Blog Post -->
                        
                    </div>
                    
                    <!-- <blogsidebar></blogsidebar> -->
                    
                </div>
            </div>
        </section>
        <!-- /Blog Details -->
        
      <layouts1></layouts1>
       
    </div>
    <!-- /Main Wrapper -->
    <blogtempmodal></blogtempmodal>
    <voteregistermodal></voteregistermodal>
    <evaluationtotalresult></evaluationtotalresult>
    <voteresult></voteresult>
</template>


<script>
import { computed } from "vue";
import { useStore } from "vuex";


export default {
    setup(){
        const debate_id = 2;
        const store = useStore();
        const registerEvaluation = (evaluation) => store.dispatch("registerEval", evaluation);
        const getEvaluations = () => store.dispatch("getEval")

        const sendVote = (vote) => store.dispatch("sendVote", vote);
        const getVote = (debate_id) => store.dispatch("getVote", debate_id);

        const userEvaluations = computed( () => store.state.userEvaluations);
        return {registerEvaluation, getEvaluations, userEvaluations, sendVote, getVote, debate_id};
    },
    methods: {
        alert(str) {
            alert(str)
        },
        closeEvaluation(){
            this.evaluated_selected = false;
            this.evaluation_open = false;
        },
        async getEval(){
            await this.getEvaluations()
            .then((response) => {
                console.log(response);
                console.log(response.status);
            })
            .catch((error) => {
                console.log(error);
                console.log(error.status);
            });
            this.alert(this.userEvaluations);
        },
        async getVoteResult(debate_id){
            await this.getVote(debate_id)
            .then((response) => {
                console.log(response);
            })
            .catch((error) => {
                console.log(error);
            });
        }

    }
}
</script>

<style>
.evaluationmodal {
  position: fixed;
  z-index: 999;
  top: 20%;
  left: 40%;
  width: 500px;
  margin-left: -150px;
  background-color:blanchedalmond;
}
.selectedmodal {
  position: fixed;
  z-index: 999;
  top: 50%;
  left: 40%;
  width: 500px;
  margin-left: -150px;
  background-color:blanchedalmond;
}
</style>
