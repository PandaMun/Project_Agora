<template>
    <!-- Main Wrapper -->
    <div class="main-wrapper">

      <layouts></layouts>
       
        <!-- Page Wrapper -->
			<div class="page-content instructor-page-content">
				<div class="container">
					<div class="row">

            <studentsidebar></studentsidebar>
						
						<!-- Instructor Dashboard -->
						<div class="col-xl-9 col-lg-8 col-md-12">	

							<div class="row">

							</div>
							<div class="row">
								<div class="col-md-12">
									<div class="card instructor-card">
										<div class="card-header">
											<h4>토론 역할 비율 </h4>
										</div>
										<div class="card-body">
                      <apexchart :options="circleOption" :series="circleSeries" type="pie" height="350" ></apexchart>
										</div>
									</div>
								</div>
							</div>
              <div class="row">
                <div class="col-md-12">
                  <div class="card instructor-card">
                    <div class="card-header">
                      <h4>토론 참여 추이 </h4>
                    </div>
                    <div class="card-body">
                      <apexchart :options="chartOptions" :series="chartSeries" type="bar" height="350" ></apexchart>
                    </div>
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
import { ref, onMounted } from 'vue';
import VueApexCharts  from 'vue3-apexcharts';

export default {
  components: {
    apexchart: VueApexCharts,
  },
  data() {
    return {
      check : false,
      file : null,
      fileData : null,
      history:{},
      chartOptions:{
        chart :{
          id : "토론 참여 추이"
        },
        xaxis : {
          categories :[]
        }
      },
      circleOption :{
        chart: {
          width: 380,
          type: 'pie',
        },
        labels: []
      },
      circleSeries: [],
      chartSeries: [{
        name: 'Discussions',
        data: []
      }],
    };
  },
  methods: {

  },
  async mounted() {
    const api = apiInstance();
    api.defaults.headers["authorization"] = "Bearer " +  sessionStorage.getItem("access-token");
    const response =  await api.get('/userDebates');
    this.history = response;
    const data = response.data.histories;
    console.log(data)
    let roles = {};
    let date = {};
    for (const item of data) {
      if (!roles[item.role]) {
        roles[item.role] = 1;
      } else {
        roles[item.role] += 1;
      }
      if(!date[item.date]) {
        date[item.date] = 1;
      }else{
        date[item.date] += 1;
      }
    }
    console.log(date)
    for(const text of Object.keys(roles)){
      this.circleOption.labels.push(text);
    }
    for(const text of Object.keys(date)){
      this.chartOptions.xaxis.categories.push(text);
    }

    this.circleSeries = Object.values(roles);

    this.chartSeries[0].data = Object.values(date);
    this.history = response.data.histories;
    console.log(this.chartSeries)
    console.log(this.chartOptions)
  }

};
</script>