import axios from "axios";

// axios 객체 생성
export default axios.create({
    // baseURL: "http://localhost:8082/api/v1",
    baseURL: "http://i8c205.p.ssafy.io:8082/api/v1",
    headers: {
        "Content-Type": "application/json;charset=utf-8",
    },
});
