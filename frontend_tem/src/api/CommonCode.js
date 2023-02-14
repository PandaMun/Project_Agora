import { apiInstance } from "./index.js";

const api = apiInstance();

async function getCommonCodes(){
    await api.get(`codes/category`);
}

export { getCommonCodes };