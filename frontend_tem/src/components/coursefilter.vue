<template>
    <!-- Filter -->
    <div class="showing-list">
      <div class="row">
        <div class="col-lg-6">
          <div class="d-flex align-items-center">
            <div class="view-icons">
              <router-link :class="currentPath == 'course-grid' ? 'active' : 'notactive'" to="course-grid"
                           class="grid-view"><i class="feather-grid"></i></router-link>
              <router-link :class="currentPath == 'course-list' ? 'active' : 'notactive'" to="course-list"
                           class="list-view"><i class="feather-list"></i></router-link>
            </div>
            <div class="show-result">
              <h4 v-if = "store.state.debate.totalElements == 0">Showing 0 - 0 of 0 results </h4>
              <h4 v-else >Showing  {{store.state.debate.offset + 1}} - {{ store.state.debate.offset + 1 + store.state.debate.numberOfElements}} of {{ store.state.debate.totalElements }} results</h4>
            </div>
          </div>
        </div>
        <div class="col-lg-6">
          <div class="show-filter add-course-info">
            <form onsubmit="return false;">
              <div class="row gx-2 align-items-center">
                <div class="col-md-3 col-lg-3 col-item">
                  <div class="form-group select-form mb-0">
                    <select class="form-control" v-model="data.condition">
                        <option value="" selected>검색 조건 선택</option>
                      <option v-for="(item, index) in conditions"
                              :key="index"
                              :value="item.name">{{ item.text }}
                      </option>
                    </select>
  
                  </div>
                </div>
                <div class="col-md-7 col-item">
                  <div class=" search-group">
                    <i class="feather-search"></i>
                    <input type="text" class="form-control" v-model = "data.keyword" placeholder="제목 혹은 작성자 명으로 검색">
                  </div>
                </div>
                <div class="btn btn-primary col-md-2  col-item"  @click ="loadDebateList" > 검색 </div>
              </div>
            </form>
            
          </div>
        </div>
      </div>
    </div>
    <!-- /Filter -->
  </template>
  <script>
  import {onMounted, reactive} from 'vue'
  import {useStore} from "vuex";
  
  export default {
    components: {
      vueSelect: window["vue-select"]
    },
    setup() {
      const store = useStore();
      const data = reactive({
        keyword : store.state.debate.keyword,
        condition : store.state.debate.condition,
        totalElements : store.state.debate.totalElements,
        pageNumber : store.state.debate.pageNumber, 
        numberOfElements : store.state.debate.numberOfElements,
      })
      onMounted(() => {
        loadDebateList();
      })

      const loadDebateList = async () => {
        store.commit('debate/SET_KEYWORD', data.keyword);
        store.commit('debate/SET_CONDITION', data.condition);
        await store.dispatch("debate/searchDebateList", {})
      }

      return {data, loadDebateList, store};
    },
  
    computed: {
      currentPath() {
        return this.$route.name;
      }
    },
    data() {
      return {
        conditions: [{name: "owner", text: "개설자"}, {name: "title", text: "제목"}],
      }
    },
    mounted() {
  
    }
  }

  </script>
  