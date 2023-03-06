import { apiInstance } from "./index.js";

const api = apiInstance();

async function searchAll(search, success, fail) {
    await api.get(`/debates?keyword=${search.keyword}&condition=${search.condition}&categoryList=${search.categoryList}&page=${search.page}`).then(success).catch(fail);
}

async function getCategoryList(success, fail){
    await api.get(`/codes/category`).then(success).catch(fail);  
}

export { searchAll, getCategoryList };
