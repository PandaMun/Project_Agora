<template>
  <div class="main-wrapper">
    <layoutsinstructor></layoutsinstructor>
    <div class="page-content">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="filter-grp ticket-grp tiket-suport d-flex align-items-center justify-content-between">
              <div>
                <h3>토론 생성</h3>
              </div>
              <div class="ticket-btn-grp">
                <router-link to="index">Back to Home</router-link>
              </div>
            </div>
          </div>
          <div class="settings-widget">
            <div class="settings-inner-blk add-course-info new-ticket-blk p-0">
              <div class="comman-space">
                <h4>토론 설정</h4>
                <div>
                  <div class="form-group">
                    <label for="title" class="form-control-label">토론 제목</label>
                    <input type="text" class="form-control" v-model="state.debate.title">
                  </div>
                  <div class="form-group">
                    <label class="add-course-label">토론 설명</label>
                    <div>
                      <textarea class="form-control" v-model="state.debate.description" placeholder="Write down here"
                                rows="4"></textarea>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-label">토론 카테고리</label>
                    <div>
                      <select class="form-control" v-model="state.debate.category" name="sellist1">
                        <option value="">Choose Category</option>
                        <option v-for="(item, index) in state.option.categories"
                                :key="index"
                                :value="item.id">{{ item.codeName }}
                        </option>
                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="form-label">토론 모드</label>
                    <select class="form-control" v-model="state.debate.mode" name="sellist2">
                      <option value="">Choose Category</option>
                      <option v-for="(item) in state.option.modes"
                              :value="item">{{ item }}
                      </option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label class="form-label">사회자 여부</label>
                    <div>
                      <input type="radio" name="select_specialist" v-model="state.debate.moderateOnOff" value="true">
                      <span class="checkmark"></span> 사회자 참여
                      <input type="radio" name="select_specialist" v-model="state.debate.moderateOnOff" value="false">
                      <span class="checkmark"></span> 사회자 불참
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="start-time">토론 시작 시간</label>
                    <div>
                      <input id="start-time" type="datetime-local" v-model="state.debate.callStartTime"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="end-time">토론 종료 시간</label>
                    <div>
                      <input id="end-time" type="datetime-local" v-model="state.debate.callEndTime"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <label for="thumbnail">토론 썸네일</label>
                    <div>
                      <input type="file" id="thumbnail" class="btn btn-outline-dark">
                    </div>
                  </div>
                </div>
                <div class="submit-ticket">
                  <button type="button" class="btn btn-primary" @click.prevent="saveDebateConfig">생성</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {onMounted, reactive} from "vue";
import {useRouter, useRoute} from 'vue-router';
import {useStore} from 'vuex';
import SummernoteEditor from 'vue3-summernote-editor';
import {apiInstance} from "../../../api/index";

export default {
  name: 'debateConfiguration',
  components: {SummernoteEditor},
  setup() {
    const router = useRouter();
    const store = useStore();
    const api = apiInstance();
    api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
    const state = reactive({
      debate: {
        title: '',
        description: '',
        category: '',
        mode: '',
        moderateOnOff: '',
        callStartTime: '',
        callEndTime: '',
        perspectiveNames: ["찬성", "반대"]
      },
      option: {
        categories: null,
        modes: ['CEDA', '시간총량제'],
      },
    });

    onMounted(() => {
      api.get(`/codes/category`)
          .then((data) => {
            let result = data["data"].data;
            console.log(result);
            state.option.categories = result;
          })
          .catch((error) => {
            console.log(error);
            alert("error : " + error.code);
          })
    })

    const uploadImg = async (event) => {
      const file = event.target.files[0];
      const formData = new FormData();
      formData.append('file', file);
      const response = await api.post('/debates/thumbnail/', formData);
    }

    const saveDebateConfig = () => {
      const file = document.getElementById("thumbnail");
      const formData = new FormData();
      formData.append("ownerId", store.getters["userStore/checkUserInfo"].userEmail);
      formData.append("category", state.debate.category);
      formData.append("callStartTime", state.debate.callStartTime);
      formData.append("callEndTime", state.debate.callEndTime);
      formData.append("title", state.debate.title);
      formData.append("description", state.debate.description);
      formData.append("state", "비활성화");
      formData.append("debateMode", state.debate.mode);
      formData.append("moderatorOnOff", state.debate.moderateOnOff);
      formData.append("perspectiveNames", state.debate.perspectiveNames);
      formData.append("file", file.files[0]);
      api.post(`/debates`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(data => {
        router.push("/")
      }).catch((error) => {
        console.log(error)
      })
    }

    return {state, saveDebateConfig, uploadImg}
  },
  // methods: {
  //   // saveDebateConfig() {
  //   //   // Implement logic to save the debate configuration
  //   //   console.log('Debate Configuration:', this.debateTitle, this.debateDescription, this.debateState, this.debateCategory, this.debateMode, this.debateOption);
  //   // }
  // }
};
</script>


<style scoped>

</style>