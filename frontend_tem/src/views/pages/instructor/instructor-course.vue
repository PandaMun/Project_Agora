<template>
    <!-- Main Wrapper -->
    <div class="main-wrapper">

      <layouts></layouts>
       
            <!-- Page Wrapper -->
			<div class="page-content">
				<div class="container">
					<div class="row">

            <studentsidebar></studentsidebar>
						
						<!-- Instructor Dashboard -->
						<div class="col-xl-9 col-lg-8 col-md-12">

              <div class="row">
                <div class="col-md-12">
                  <div class="settings-widget">
                    <div class="settings-inner-blk p-0">
                      <div class="sell-course-head comman-space">
                        <div style="text-align: start">
                          <h3>이전 참여 목록</h3>
                        </div>
                        <div v-if="check" style="text-align: end">
                          <button class="btn btn-primary" @click="certification">증명서 발급하기</button>
                        </div>
                      </div>
                      <div class="comman-space pb-0">
                        <div class="settings-tickets-blk course-instruct-blk table-responsive">

                          <!-- Referred Users-->
                          <table class="table table-nowrap mb-0">
                            <thead>
                            <tr>
                              <th>날짜</th>
                              <th>주제</th>
                              <th>역활</th>
                              <th>소요 시간</th>
                            </tr>
                            </thead>
                            <tbody v-for="item in contents">
                            <tr>
                              <td>{{item.date}}</td>
                              <td>{{ item.title }}</td>
                              <td>{{item.role}}</td>
                              <td>{{item.activeTime}}</td>
                            </tr>
                            </tbody>
                          </table>
                          <!-- /Referred Users-->


                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="container text-center"  style="text-align: center;">
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
						<!-- /Instructor Dashboard -->
						
					</div>
				</div>
			</div>	
			<!-- /Page Wrapper -->
        
        <layouts1></layouts1>
       
    </div>
    <!-- /Main Wrapper -->
</template>
<script>

import {apiInstance} from "../../../api";
export default {

  data() {
    return {
      file: null,
      fileData: null,
      check: false,
      history: {},
      category : '회원 기능',
      contents: [],
      pageable: {
        number: 0,
        size: 10,
      },
      totalElements: 0,
      totalPages: 0
    }
  },
  computed: {

    canPrev() {
      return this.page.number > 0;
    },
    canNext() {
      return this.page.number < this.page.totalPages - 1;
    }
  },
      methods: {
        async certification() {
          const api = apiInstance();
          try {
            api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
            const response = await api.get('/certification');
            console.log(response)
            this.fileData = response.data;
            const file = new Blob([this.fileData], {type: 'application/octet-stream'});
            const fileURL = URL.createObjectURL(file);
            const link = document.createElement('a');
            link.href = fileURL;
            link.setAttribute('download', '토론 활동 증명서.pdf');
            document.body.appendChild(link);
            link.click();
            link.remove();
          } catch (error) {
            console.error(error);
          }
        }, prevPage() {
          if (this.canPrev) {
            this.fetchData(this.page.number - 1, this.page.size);
          }
        },
        nextPage() {
          if (this.canNext) {
            this.fetchData(this.page.number + 1, this.page.size);
          }
        },
        async fetchData(pageNumber, pageSize) {
          const api = apiInstance();
          api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
          await api.get('/userDebates/pageable', { params: { page: pageNumber-1, size: pageSize } })
              .then(response => {
                console.log(response);
                if (response.data.totalElements > 0) {
                  this.check = true;
                }
                this.contents = response.data.content;
                this.pageable = response.data.pageable;
                this.last = response.data.last;
                this.totalPages = response.data.totalPages;
                console.log(response.data)
              });
        }
      },

        async mounted(){
          await this.fetchData(0,10)
        }


    }
</script>
