<template>
    <div class="col-lg-3 theiaStickySidebar">
        <div class="stickysidebar">
            <div class="filter-clear">
                <div class="clear-filter d-flex align-items-center">
                    <h4><i class="feather-filter"></i>Filters</h4>
                    <!-- <div class="clear-text">
                        <p>CLEAR</p>
                    </div> -->
                </div>
                
                <!-- Search Filter -->
                <div class="card search-filter">
                    <div class="card-body">
                        <div class="filter-widget mb-0">
                            <div class="categories-head d-flex align-items-center">
                                <h4> 토론 카테고리 </h4>
                                <i class="fas fa-angle-down"></i>
                            </div>
                            <div v-for="(category, index) in store.state.debate.categoryList">
                                <label class="custom_check">
                                    <input type="checkbox" name="select_specialist" :checked="store.state.debate.selectedCategoryIdList.includes(category.id)" :value="category.id" @click="setSelectedCategories()">
                                    <span class="checkmark"></span> {{ category.codeName }}
                                </label>
                            </div>
                            
                        </div>
                    </div>
                </div>
                <!-- /Search Filter -->
            
            </div>
        </div>
    </div>
</template>

<script>    

import { apiInstance } from "/api/index.js";      
import {useStore} from "vuex";
import { onMounted} from "vue";

const api = apiInstance();

export default {
    setup(){
        const store = useStore();
        
        // 처음 페이지로드시, 전체 카테고리를 선택 카테고리로 지정
        // 만약 이미 선택된 카테고리 정보가 있다면, 해당 정보를 바탕으로 radiobox 지정.
        onMounted(() => {
            getCategories();
        })
        const getCategories = async ()=> {
            await store.dispatch("debate/getCategoryList");
            
            // 정해진 카테고리 목록을 바탕으로 토론 조회
           await store.dispatch("debate/searchDebateList", {}); 
        }

        const setSelectedCategories = ()=>{

            let check_val = [];
            $("input:checkbox[name=select_specialist]:checked").each(function(i, iVal){
                check_val.push(iVal.value*1);
            });

            store.commit("debate/SET_SELECTED_CATEGORY_LIST", check_val);
            
            store.dispatch("debate/searchDebateList", {});

        }
        
        return {store, setSelectedCategories}
    },
}
</script>