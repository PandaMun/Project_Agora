import {router} from "../../router";
import {getCommonCodes} from "../../api/CommonCode";
// import {apiInstance} from "./index.js";
import axios from "axios";

const commonCodeStore = {
    namespaced: true,
    state: {
        categories: null,
    },
    getters: {
        checkCategories: function (state) {
            console.log(state.categories);
            return state.categories;
        }
    },
    mutations: {
        SET_CATEGORIES: (state, categories) => {
            state.categories = categories;
            console.log(state.categories);
        }
    },
    actions: {
        getCategories({commit}) {
            // getCommonCodes().then((data) => {
            //     let categories = data["data"]
            //     commit("SET_CATEGORIES", categories);
            //     sessionStorage.setItem("categories", categories);
            //     return categories;
            // }).catch((error) => {
            //     alert("error");
            // })
            axios.defaults.baseURL = "http://i8c205.p.ssafy.io:8082/api/v1";
            axios.get(`codes/category`)
                .then((data) => {
                    let result = data["data"];
                    commit("SET_CATEGORIES", result);
                    return result;
                })
                .catch((error) => {
                    alert("error" + error.code);
                })
        },
    }
};

export default commonCodeStore;
