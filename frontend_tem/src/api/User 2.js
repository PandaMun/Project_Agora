import { apiInstance } from "./index.js";

const api = apiInstance();

function login(user, success, fail) {
    api.post(`/auth/login`, user).then(success).catch(fail);
}

function findById(userid, success, fail) {
    api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
    api.get(`/users/me`).then(success).catch(fail);
}

function tokenRegeneration(user, success, fail) {
    api.defaults.headers["refreshToken"] = sessionStorage.getItem("refresh-token"); //axios header에 refresh-token 셋팅
    api.post(`/auth/reissue`, user).then(success).catch(fail);
}

function logout(userid, success, fail) {
    api.get(`/auth/auth`).then(success).catch(fail);
}

export { login, findById, tokenRegeneration, logout };
