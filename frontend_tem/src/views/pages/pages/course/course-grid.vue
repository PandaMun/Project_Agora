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
							
							<div class="row" v-if="store.state.debate.howToShow=='grid'">
								<div class="col-lg-4 col-md-6 d-flex" v-for="(debate, index) in store.state.debate.debateList">
									<div class="course-box course-design d-flex " >
										<div class="product">
											<div class="product-img">
												<router-link to="course-details">
													<img class="img-fluid" alt="" src="../../../../assets/img/course/testImg.jpg">
                                                </router-link>
												<div class="price">
													<h3 :class="debate.state">{{ debate.state }}</h3>
												</div>
											</div>
											<div class="product-content">
												<div class="course-group d-flex">
													<div class="course-group-img d-flex">
														<router-link to="instructor-profile"><img src="../../../../assets/img/user/user1.jpg" alt="" class="img-fluid"></router-link>
														<div class="course-name">
															<h4><router-link to="instructor-profile">{{ debate.ownerName }}</router-link></h4>
															<p>{{ debate.ownerDepartment }}</p>
														</div>
													</div>
													<div class="course-share d-flex align-items-center justify-content-center">
														<a href="javascript:;"><i class="fa-regular fa-heart"></i></a>
													</div>
												</div>
												<h3 class="title"><router-link to="course-details">[No.{{debate.debateId}}] <br/> {{ debate.title }}</router-link></h3>
												<div class="course-info align-items-center">
													<div class="rating-img d-flex align-items-center pb-2">
														<img src="../../../../assets/img/icon/icon-01.svg" alt="">
														<p> {{ debate.debateMode }}</p>
													</div>
													<div class="course-view d-flex align-items-center  pl-2">
														<img src="../../../../assets/img/icon/icon-02.svg" alt="">
														<p>{{ debate.callStartTime }} ~ {{ debate.callEndTime }}</p>
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
import {useStore} from "vuex";
import {useRouter} from "vue-router";
import {onMounted, reactive} from "vue";

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const data = reactive({
      debate_list: store.getters["debate/getDebateList"],
    })
    onMounted(() => {
      loadDebateList();
    })

    const loadDebateList = async () => {
      await store.dispatch("debate/searchDebateList", {
        condition: "title",
        keyword: "반려동물",
      })
    }
    return {store, data}
  },
}
</script>
