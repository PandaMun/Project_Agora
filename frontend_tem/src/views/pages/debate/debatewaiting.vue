<template>
  <div class="main-wrapper">
    <div style="height: 10vh;"></div>
    <div class="logo-box">
      <img class="img-main" src="../../../assets/img/Agora3.png">
    </div>
    <div style="height: 5vh"></div>
    <h5>n명 중 m명 참가 대기중입니다</h5>
    <router-link to="/debatemain">
      <button>참가완</button>

    </router-link>
  </div>
</template>

<script>

export default {
  name: "debatewaiting",

}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

@keyframes example {
  from {background-image: url("../../../assets/img/banner.png");}
  to {background-image: url("../../../assets/img/bg-banner.png");}
  50% {background-image: url("../../../assets/img/bg-banner-01.png");}
  100% {background-image: url("../../../assets/img/bg-banner-02.png");}
}

@keyframes example2 {
  0%   {background-color: red;}
  25%  {background-color: yellow;}
  50%  {background-color: blue;}
  100% {background-color: green;}
}

.main-wrapper {
  background-size: cover;
  height: 100vh;
  justify-content: center;
  align-items: center;
  animation-name: example;
  animation-duration: 3s;
  animation-iteration-count: infinite;
}

.img-main {
  display: block;
  margin: auto;
}

h5 {
  text-align: center;
  color: purple;
  font-family: 'Nanum Gothic', sans-serif;
}

</style>