import {createStore} from 'vuex';
import createPersistedState from "vuex-persistedstate";

// import userStore from "@/Store/modules/userStore";
import userStore from "./modules/userStore";
import commonCodeStore from "./modules/commonCodeStore";
import debate from './modules/debate';



// [store 데이터 설정 실시]
const store = createStore({

    modules: {userStore, commonCodeStore, debate},
    state: { // [변수들의 집합]

    },
    getters: { // [state의 변수들을 get 호출]

    },
    mutations: { // [변수들을 조작하는 함수들]

    },
    actions: { // [비동기 처리를 하는 함수들]
    },
    plugins: [
        createPersistedState({
            // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
            storage: sessionStorage,
        }),
    ],
});
export default store;