<template>
  <ul class="nav header-navbar-rht">
    <li class="nav-item user-nav">
      <a href="javascript:void(0)" class="dropdown-toggle" data-bs-toggle="dropdown">
                <span class="user-img">
                    <img :src="'https://i8c205.p.ssafy.io/api/v1/users/images/' + data.userEmail" alt="">
                    <span class="status online"></span>
                </span>
      </a>
      <div class="users dropdown-menu dropdown-menu-right" data-popper-placement="bottom-end" >
        <div class="user-header">
          <div class="avatar avatar-sm">
            <img :src="'https://i8c205.p.ssafy.io/api/v1/users/images/' + data.userEmail" alt="User Image" class="avatar-img rounded-circle">
          </div>
          <div class="user-text">
            <h6>{{data.username}}</h6>
            <p class="text-muted mb-0">{{data.position}}</p>
          </div>
        </div>
        <router-link class="dropdown-item" to="setting-edit-profile"><i class="feather-user me-1" ></i> MyPage</router-link>
        <router-link class="dropdown-item" to="setting-student-subscription"><i class="feather-star me-1"></i> Subscription</router-link>
        <router-link class="dropdown-item" to="/" @click="logout" ><i class="feather-log-out me-1"></i>Logout</router-link>
      </div>
    </li>
  </ul>
</template>
<script>
import Vue, {reactive} from 'vue'
import {useStore} from "vuex";
import {useRouter} from "vue-router";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const data = reactive({
      username: store.getters["userStore/checkUserInfo"].name,
      userEmail:store.getters["userStore/checkUserInfo"].userEmail,
      position: store.getters["userStore/checkUserInfo"].position,
      profile : store.state.userStore.isLogin,
    });
    const logout = async () => {
      await store.dispatch("userStore/userLogout");

      moveHome();
    }

    const moveHome = () => {
      router.push('/login');
    }
    return {data,logout};
  },

  components: {

  },

}
</Script>
