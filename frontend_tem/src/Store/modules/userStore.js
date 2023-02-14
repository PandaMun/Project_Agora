import jwtDecode from "jwt-decode";
import {router} from "../../router";
import {login, findById, tokenRegeneration, logout} from "../../api/User";

const userStore = {
    namespaced: true,
    state: {
        isLogin: false,
        isLoginError: false,
        // userInfo : null,
        // TODO : 임시 데이터 삭제
        userInfo: {
            // 
            name : "김임시",
            position : "임시고등학교"
        },
        isValidToken: false,
    },
    getters: {
        checkUserInfo: function (state) {
            return state.userInfo;
        },
        getIsLogin: function (state) {
            return state.isLogin;
        },
        checkToken: function (state) {
            return state.isValidToken;
        },
    },
    mutations: {
        SET_IS_LOGIN: (state, isLogin) => {
            console.log("check  " + isLogin);
            state.isLogin = isLogin;
        },
        SET_IS_LOGIN_ERROR: (state, isLoginError) => {
            state.isLoginError = isLoginError;
        },
        SET_IS_VALID_TOKEN: (state, isValidToken) => {
            state.isValidToken = isValidToken;
        },
        SET_USER_INFO: (state, userInfo) => {
            state.userInfo = userInfo;
        },
    },
    actions: {
         async userConfirm({commit, dispatch}, user) {
             await login(user, async ({data}) => {
                    if (data.message === "Success") {
                        let accessToken = data["tokenInfo"].authorization;
                        let refreshToken = data["tokenInfo"].refreshToken;
                        // console.log("login success token created!!!! >> ", accessToken, refreshToken);
                        commit("SET_IS_LOGIN", true);
                        commit("SET_IS_LOGIN_ERROR", false);
                        commit("SET_IS_VALID_TOKEN", true);
                        sessionStorage.setItem("access-token", accessToken);
                        sessionStorage.setItem("refresh-token", refreshToken);
                        await findById(({data}) => {
                            let userInfo = {
                                userEmail: data.userEmail,
                                name: data.name,
                                department: data.department,
                                position: data.position,
                                grade: data.grade,
                                classNum: data.classNum,
                                profile: data.profileUrl
                            }
                            if (data.message === "Success") {
                                commit("SET_USER_INFO", userInfo);
                                // console.log("3. getUserInfo data >> ", data);
                            } else {
                                console.log("유저 정보 없음!!!!");
                            }
                        }, async (error) => {
                            console.log("getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ", error.response.status);
                            commit("SET_IS_VALID_TOKEN", false);
                            await dispatch("tokenRegeneration");
                        });
                    } else {
                        commit("SET_IS_LOGIN", false);
                        commit("SET_IS_LOGIN_ERROR", true);
                        commit("SET_IS_VALID_TOKEN", false);
                    }
                },
                (error) => {
                    if(error.response.status==500){
                        alert("존재하지 않은 아이디 입니다.")
                    }
                }
            )
        },
        getUserInfo({commit, dispatch}) {
            findById(({data}) => {
                if (data.message === "Success") {
                    console.log(data)
                } else {
                    console.log("유저 정보 없음!!!!");
                }
            }, async (error) => {
                console.log("getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ", error.response.status);
                commit("SET_IS_VALID_TOKEN", false);
                await dispatch("tokenRegeneration");
            });
        },
        async tokenRegeneration({commit, state}) {
            console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("access-token"));
            await tokenRegeneration(
                ({data}) => {
                    if (data.message === "Success") {
                        let accessToken = data["access-token"];
                        console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
                        sessionStorage.setItem("access-token", accessToken);
                        commit("SET_IS_VALID_TOKEN", true);
                    }
                },
                async (error) => {
                    // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
                    if (error.response.status === 401) {
                        console.log("갱신 실패");
                        // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
                        await logout(
                            ({data}) => {
                                if (data.message === "Success") {
                                    console.log("리프레시 토큰 제거 성공");
                                } else {
                                    console.log("리프레시 토큰 제거 실패");
                                }
                                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                                commit("SET_IS_LOGIN", false);
                                commit("SET_USER_INFO", null);
                                commit("SET_IS_VALID_TOKEN", false);
                                router.push({name: "login"});
                            },
                            (error) => {
                                console.log(error);
                                commit("SET_IS_LOGIN", false);
                                commit("SET_USER_INFO", null);
                            }
                        );
                    }
                }
            );
        },


        async userLogout({commit}) {
            await logout(
                ({data}) => {
                    if (data.message === "Success") {
                        commit("SET_IS_LOGIN", false);
                        commit("SET_USER_INFO", null);
                        commit("SET_IS_VALID_TOKEN", false);
                    } else {
                        console.log("유저 정보 없음!!!!");
                    }
                },
                (error) => {
                    console.log(error);
                }
            );
        },
    },
};

export default userStore;
