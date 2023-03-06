<template>
<!-- Login Banner -->
<div class="col-md-6 login-bg">
    <div class="owl-carousel login-slide owl-theme">
        <div class="welcome-login">
            <div class="login-banner">
                <img src="../assets/img/login-img.png" class="img-fluid" alt="Logo">
            </div>
            <Carousel>
                <Slide v-for="slide in 3" :key="slide">
                    <div class="mentor-course text-center">
                        <h2>Welcome to Agora! </h2>
                        <p> 온라인 토론 플랫폼 아고라에 어서오세요!<br/>
                            다양한 주제를 바탕으로 많은 사람들과 이야기를 나눌 수 있습니다. </p>
                    </div>
                </Slide>
                <template #addons>
                    <Pagination />
                </template>
            </Carousel>
        </div>
    </div>
</div>
<!-- /Login Banner -->
</template>
<script>
    import { defineComponent } from 'vue'
    import { Carousel, Pagination, Slide } from 'vue3-carousel';
    
    import 'vue3-carousel/dist/carousel.css';
    
    export default defineComponent({
    name: 'Autoplay',
    components: {
    Carousel,
    Slide,
    Pagination,
    },
});
</script>