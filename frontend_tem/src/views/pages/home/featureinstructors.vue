<template>
 <!-- Feature Instructors -->
 <div class="feature-instructors">
    <div class="section-header aos" data-aos="fade-up">
        <div class="section-sub-head feature-head text-center">
            <h2>Featured Instructor</h2>
            <div class="section-text aos" data-aos="fade-up">
                <p class="mb-0">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Eget aenean accumsan bibendum gravida maecenas augue elementum et neque. Suspendisse imperdiet.</p>
            </div>
        </div>
    </div>
    <Carousel :settings="settings" :breakpoints="breakpoints">
        <Slide v-for="item in feature" :key="item.id" class="owl-carousel instructors-course owl-theme">
            <div class="instructors-widget">
                <div class="instructors-img ">
                    <router-link to="instructor-list">
                        <img class="img-fluid" alt="" :src="loadImg(item.img)">
                    </router-link>
                </div>
                <div class="instructors-content text-center">
                    <h5><router-link to="instructor-profile">{{item.name}}</router-link></h5>
                    <p>{{item.Developer}}</p>
                    <div class="student-count d-flex justify-content-center">
                        <i class="fa-solid fa-user-group"></i>
                        <span>{{item.Students}}</span>
                    </div>
                </div>
            </div>
        </Slide>
        <template #addons>
            <Pagination />
        </template>
    </Carousel>
</div>
<!-- /Feature Instructors -->
</template>
<script>
    import AOS from 'aos';
    import feature from '../../../assets/json/feature.json'
    import { defineComponent } from 'vue'
    import { Carousel, Pagination, Slide } from 'vue3-carousel';
    const images = require.context('../../../assets/img/user', false, /\.png$|\.jpg$/)
    import 'vue3-carousel/dist/carousel.css';
    export default {
        name: 'Autoplay',
            components: {
            Carousel,
            Slide,
            Pagination,
            },
        data() {
            return {
                feature : feature
            }
        },
        methods:{
        loadImg(imgPath) {
            return images('./' + imgPath).default
        },
    },
    setup() {
		return {
			// carousel settings
			settings: {
				itemsToShow: 1,
				snapAlign: "center"
			},
			
			breakpoints: {
				// 700px and up
				700: {
					itemsToShow: 2,
					snapAlign: "center"
				},
				// 1024 and up
				1024: {
					itemsToShow: 4,
					snapAlign: "start"
				}
			}
		};
	},

        mounted() {
            if($('.main-wrapper .aos').length > 0) {
            AOS.init({
              duration: 1200,
              once: true,
            });
        }
       
        },
       
    }
    </script>