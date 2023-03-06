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
                  <li><a href="https://www.hani.co.kr/arti/animalpeople/companion_animal/1055181.html">[한겨레] “반려동물 보유세
                    어떻게 생각하시나요” 국민에 묻는다</a></li>
                  <li><a href="https://www.sejungilbo.com/news/articleView.html?idxno=39319"> [세정일보] [칼럼] ‘반려동물 보유세’ 도입
                    시급하다</a></li>
                  <li class="mb-0"><a
                      href="https://namu.wiki/w/%EB%B0%98%EB%A0%A4%EB%8F%99%EB%AC%BC%20%EB%B3%B4%EC%9C%A0%EC%84%B8">[나무위키]
                    반려동물 보유세</a></li>
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
                    <router-link to="instructor-profile"><img src="../../../../assets/img/user/temp_user1.png" alt="img"
                                                              class="img-fluid"></router-link>
                    <!-- <router-link to="instructor-profile"><img :src="vote_result.mvp_profile" alt="img" class="img-fluid"></router-link> -->
                  </div>
                  <div class="instructor-detail">
                    <h5>
                      <router-link to="instructor-profile">{{ data.vote_result.mvp_name }}</router-link>
                    </h5>
                  </div>
                </div>
                <br/>

                <h6> 청중 투표 결과 </h6>
                <p v-for="perspective in vote_result.perspective_list">
                  {{ perspective.perspective_name }} : {{ Math.round(perspective.percent * 1000) / 10 }} %
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
                      <img class="" :src="data.debate.thumbnailUrl" alt="">
                      <!-- <img class="" :src="debate.thumbnail_url" alt=""> -->
                    </a>
                    <div class="video-details">
                      <!-- <div class="row gx-2">
                        <div class="col-md-6">
                          <router-link to="course-wishlist" class="btn btn-wish w-100"><i class="feather-heart"></i> 위시리스트에 넣기 </router-link>
                        </div>
                        <div class="col-md-6">
                          <a href="javascript:;" class="btn btn-wish w-100"><i class="feather-share-2"></i> 공유하기 </a>
                        </div>
                      </div> -->
                      <button class="btn btn-primary w-100 mb-2" v-if="data.debate.state == 'active'"
                              @click="setDebateLink('찬성')"> 찬성측으로 입장하기
                      </button>
                      <button class="btn btn-info w-100 mb-2" v-if="data.debate.state == 'active'"
                              @click="setDebateLink('반대')"> 반대측으로 입장하기
                      </button>
                      <button class="btn btn-dark w-100 mb-2" v-if="data.debate.state == 'active'"
                              @click="setDebateLink('사회자')"> 사회자로 입장하기
                      </button>
                      <router-link to="checkout" class="btn btn-enroll w-100 disabled"
                                   v-else-if="data.debate.state == 'inactive'"> 아직 시작되지 않은 토론이에요
                      </router-link>
                      <router-link to="checkout" class="btn btn-enroll w-100"
                                   v-else-if="data.debate.state == 'in ready'"> 토론이 곧 시작됩니다
                      </router-link>
                      <router-link to="checkout" class="btn btn-dark w-100 disabled" v-else> 종료됨</router-link>
                      <!-- state : closed -->
                    </div>
                  </div>
                </div>
              </div>
              <!-- /Video -->


              <!-- Features -->
              <div class="card feature-sec" v-if="data.fileList != null">
                <div class="card-body">
                  <div class="cat-title">
                    <h4> 사용된 파일 </h4>
                  </div>
                  <div class="perspective wrapper pb-2" v-for="file in data.fileList">
                    <ul class="mb-0">
                      <li><a :href="file.fileDownloadUri">{{ file.fileName }}</a></li>
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
import {apiInstance} from "/api/index.js";
import {useStore} from "vuex";
import {onMounted, reactive} from 'vue'
import {useRouter} from 'vue-router'

const api = apiInstance();

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const data = reactive({
      // debateId : store.getters["debate/getDebateId"],
      debateId: store.state.debate.debateId,
      debate: {},
      fileList: null,

      // TODO : 현재 페이지 진입시 debateId를 통해서 상세정보 요청. 혹은 기존 토론 정보 조회 개선.
      debate_detail: {
        category: "정치",
        enroll_count: 6,
      },

      // TODO : 종료된 토론(state==closed)에 대해서만 데이터를 로드한다. 일부 API 구현 필요.
      vote_result: {},
      debate_files: [],
      summary: '',
    });


    onMounted(() => {
      // data.debateId = store.getters["debate/getDebateId"];
      getDebateInfo();
    })

    onMounted(() => {
      api.get("/files/list/" + data.debateId)
          .then((file) => {
            data.fileList = file.data;
            console.log(data.fileList);
          }).catch((error) => {
        console.log(error);
      })
    })

    const getDebateInfo = async () => {
      console.log(data.debateId);
      await api.get('/debates/' + data.debateId)
          .then((response) => {
            if (response.status == 200) {
              data.debate = response.data.data;
              data.debate.callEndTime = data.debate.callEndTime.substr(0, 19);
              data.debate.callStartTime = data.debate.callStartTime.substr(0, 19);
              data.debate.insertedTime = data.debate.insertedTime.substr(0, 19);
              store.commit('debate/SET_DEBATE_INFO', response.data.data);
              console.log('특정 토론 정보 조회 완료');
              checkState();
            } else {
              console.log(response);
              console.log('정상 조회 실패')
            }
          }).catch((error) => {
            console.log(error);
          });
    }

    const setDebateLink = async (position) => {
      //TODO : 입장시 user_debate 테이블에 입장 정보 생성.
      console.log('찬성측 입장합니다.');
      await api.post('/userDebates', {
        debateId: data.debateId,
        userEmail: store.state.userStore.userInfo.userEmail,
        role: position,
      })
          .then((response) => {
            if (response.status == 201) {
              console.log(response);
              console.log('토론 참가 신청 완료');
              store.commit('debate/SET_MYTEAM', position);
              moveToDebateMain(position);
            } else {
              console.log(response);
              console.log('정상 조회 실패')
            }
          }).catch((error) => {
            console.log('  참가 신청중 에러 발생  ');
            console.log(error);
          });

    }

    const moveToDebateMain = (position) => {
      const debate_link = {
        name: 'debatemain',
        query: {
          debateId: data.debateId,
          name: store.getters["user/checkUserInfo"],
          title: data.debate.title,
          position: position,
          roomType: data.debate.debateMode,
          time: 100,
        },
      }
      router.push(debate_link);
    }

    const checkState = async () => {
      if (data.debate.state == 'closed') {
        getVoteResult();
        getDebateFiles();
        getSummary();
      }
    }

    const getVoteResult = async () => {
      api.get('/vote/debates/' + data.debateId)
          .then((response) => {
            if (response.status == 200) {
              console.log(response);
              data.vote_result = response.data;
              console.log('특정 토론 청중 평가 결과 로드 완료');

            } else {
              console.log(response);
              console.log('정상 조회 실패')
            }
          }).catch((error) => {
        console.log(error);
      });
    }

    const getDebateFiles = async () => {
      // 토론의 각 패널에서 사용된(업로드된) 파일들
      data.debate_files = [
        {
          perspective_id: 143,
          perspective_name: "반려동물 보유세는 필요하다.",
          files: [
            {file_name: "143번측 파일명 1", file_url: "../../../../assets/img/user/temp_user2.png"},
            {file_name: "143번측 파일명 2", file_url: "../../../../assets/img/user/temp_user3.png"}
          ]
        },
        {
          perspective_id: 144,
          perspective_name: "반려동물 보유세는 필요하지않다.",
          files: [
            {file_name: "144번측 파일명 1", file_url: "../../../../assets/img/user/temp_user2.png"},
            {file_name: "144번측 파일명 2", file_url: "../../../../assets/img/user/temp_user3.png"}
          ]
        },

      ];
    }

    const getSummary = async () => {
      data.summary = `
      반려동물 보유세 도입 [ YES ]
      “보호인 책임 의식 높여 동물 유기 방지”
      우리나라에 유기되는 동물의 수는 한 해 10만 마리가 넘는다. 유기 동물을 구조하고 보호하는 동물센터 운영비가 연간 200억 원에 달하는 것으로 알려졌다.
      이 금액은 지자체에서 대부분을 부담하고 있는데, 반려동물을 키우지 않는 시민의 세금이 유기 동물 관리 비용 충당에 쓰이는 것은 부당하다.
      “동물 복지에 활용하면 긍정적 효과”
      우리나라는 동물권 선진국에 비해 반려동물 돌봄 인프라가 부족해 많은 보호인들이 비반려인들의 눈치를 보며 양육을 이어가고 있다.
      정부의 의도대로 중장기적 관점에서 세금을 어떻게 활용할지 구체적으로 논의해봐야 하겠으나, 반려동물 보유세를 동물 복지에 활용하면 긍정적인 효과를 거둘 수 있다.

      반려동물 보유세 도입 [ NO ]
      “부담 증가로 유기 동물 더 늘어날 것”
      반려동물을 기르기 위해서는 사료부터 간식, 미용, 의료비 등 생각보다 많은 돈을 지출해야 한다. 부담스러운 관리 비용을 이유로 동물을 유기하는 보호인들도 많다.
      가뜩이나 많은 지출을 감당하고 있는 보호인들에게 보유세까지 부과하면 동물을 버리는 범죄가 더 잦아질 수 있다
      “모호한 과세 근거”
      세금은 수입이 발생하는 곳에 부과하는 것이 상식이다. 더욱이 보호인들은 반려동물을 양육하는 데 필요한 물품을 구매하고 동물병원에 병원비를 내는 등 이미 부가세를 실질적으로 지불하고 있다.
      `;
    }


    return {store, data, setDebateLink};
  },
}
</script>