<template>
<!-- Inner Banner -->
<div class="inner-banner">
    <div class="container">
        <div class="row">
            <div class="col-lg-8">
                <div class="instructor-wrap border-bottom-0 m-0">
                    <div class="about-instructor align-items-center">
                        <div class="abt-instructor-img">
                            <router-link to="instructor-profile"><img src="../../../../assets/img/user/user1.jpg" alt="img" class="img-fluid"></router-link>
                        </div>
                        <div class="instructor-detail me-3">
                            <h5><router-link to="instructor-profile">{{ debate.ownerName }}</router-link></h5>
                            <p>{{debate.ownerDepartment}}</p>
                        </div>									
                        <!-- <div class="rating mb-0">							
                            <i class="fas fa-star filled me-1"></i>
                            <i class="fas fa-star filled me-1"></i>
                            <i class="fas fa-star filled me-1"></i>
                            <i class="fas fa-star filled me-1"></i>
                            <i class="fas fa-star me-1"></i>
                            <span class="d-inline-block average-rating"><span>4.5</span> (15)</span>
                        </div> -->
                    </div>
                    <span class="web-badge mb-3">  category : {{ debate_detail.category }}</span>
                </div>
                <h2>{{ debate.title }}</h2>
                <!-- <p>토론 내용 한줄 요약 - (현재 적당한 데이터 없음)</p> -->
                <div class="course-info d-flex align-items-center border-bottom-0 m-0 p-0">
                    <!-- <div class="cou-info">
                        <img src="../../../../assets/img/icon/icon-01.svg" alt="">
                        <p>12+ Lesson</p>
                    </div> -->
                    <div class="cou-info">
                        <img src="../../../../assets/img/icon/timer-icon.svg" alt="">
                        <p>{{ debate.callStartTime }} ~ {{ debate.callEndTime }}</p>
                    </div>
                    <div class="cou-info">
                        <img src="../../../../assets/img/icon/people.svg" alt="">
                        <p>{{ debate_detail.enroll_count }} 명 참여중</p>
                    </div>
                </div>							
            </div>
        </div>
    </div>
</div>
<!-- /Inner Banner -->
</template>
<script>
    export default {
        props: ["debate", "debate_detail"],
    };
</script>