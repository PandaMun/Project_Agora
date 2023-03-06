import axios from "axios";

// local vue api axios instance
export function apiInstance() {
    const instance = axios.create({
        // baseURL: process.env.VUE_APP_API_BASE_URL,
        baseURL: "https://i8c205.p.ssafy.io/api/v1",
        headers: {
            "Content-Type": "application/json;charset=utf-8",
        },
    });
    return instance;
}
