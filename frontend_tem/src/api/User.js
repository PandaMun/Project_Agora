import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
    await api.post(`/auth/login`, user).then(success).catch(fail);
}

function findById(success, fail) {
    api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
    api.get(`/users/me`).then(success).catch(fail);
}

function tokenRegeneration(success, fail) {
    const jwt = {
        authorization : sessionStorage.getItem('access-token'),
        refreshToken : sessionStorage.getItem('refresh-token')
    }
    api.post(`/auth/reissue`, jwt).then(success).catch(fail);
}


function logout(success, fail) {
    api.defaults.headers["authorization"] = "Bearer " + sessionStorage.getItem("access-token");
    api.get(`/auth/auth`).then(success).catch(fail);
}

export { login, findById, tokenRegeneration, logout };
