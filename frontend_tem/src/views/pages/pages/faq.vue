<template>
  <!-- Main Wrapper -->
  <div class="main-wrapper">

    <layoutsloginborder></layoutsloginborder>

    <faqbreadcrumb></faqbreadcrumb>

    <!-- Help Details -->
    <div class="help-sec">

      <div class="container">
        <div class="row">
          <div class="col-lg-6">
            <div class="help-title">
              <h1>FAQ</h1>
              <p>자주 묻는 질문</p>
            </div>
          </div>
        </div>
        <div class="category-tab">
          <ul class="nav nav-justified">
            <li class="nav-item" ><a class="nav-link" data-bs-toggle="tab" @click="changeCategory('회원 기능')">회원 기능</a></li>
            <li class="nav-item" ><a class="nav-link" data-bs-toggle="tab" @click="changeCategory('토론 기능')">토론 기능</a></li>
            <li class="nav-item" ><a class="nav-link" data-bs-toggle="tab" @click="changeCategory('증명서 발급 기능')">증명서 발급 기능</a></li>
          </ul>
        </div>


        <div class="faq-card" v-for="(content,index) in contents" >
          <h6 class="faq-title">
            <a class="collapsed" data-bs-toggle="collapse" :href="'#faq'+index" aria-expanded="false">{{content.content}}</a>
          </h6>
          <div :id="'faq'+index" class="collapse">
            <div class="faq-detail">
              <p>{{content.comment}}</p>
            </div>
          </div>
        </div>

        <div class="container" style="text-align: center;">
          <div class="row">
            <div class="col-md-12">
              <ul class="pagination lms-page">
                <li v-for="index in totalPages" class="page-item first-page active">
                  <button class="page-link" @click="fetchData(index,10)">{{index}}</button>
                </li>

              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- /Help Details -->

    <layouts1></layouts1>

  </div>
  <!-- /Main Wrapper -->
</template>
<script>
import { apiInstance } from "../../../api/index.js";

export default {

  data() {

    return {
      category : '회원 기능',
      contents: [],
      pageable: {
        number: 0,
        size: 10,
      },
      totalElements: 0,
      totalPages: 0
    };
  },
  computed: {

    // canPrev() {
    //   return this.page.number > 0;
    // },
    // canNext() {
    //   return this.page.number < this.page.totalPages - 1;
    // }
  },
  methods: {
    prevPage() {
      if (this.canPrev) {
        this.fetchData(this.page.number - 1, this.page.size);
      }
    },
    nextPage() {
      if (this.canNext) {
        this.fetchData(this.page.number + 1, this.page.size);
      }
    },
    changeCategory(category){
      this.category = category;
      this.fetchData(0,10);
    },
    async fetchData(pageNumber, pageSize) {
      const api = apiInstance();
      await api.get("/faq/list/"+this.category, { params: { page: pageNumber-1, size: pageSize } })
          .then(response => {
            this.contents = response.data.content;
            this.pageable = response.data.pageable;
            this.last = response.data.last;
            this.totalPages = response.data.totalPages;
            console.log(response.data)
          });
    }
  },
  mounted() {
    this.fetchData(1, 10);
  }

}

</script>
