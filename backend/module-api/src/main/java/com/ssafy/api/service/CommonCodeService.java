package com.ssafy.api.service;

import com.ssafy.entity.rdbms.CommonCode;

import java.util.List;

public interface CommonCodeService {
    CommonCode getQuestionList();

    List<CommonCode> getCategoryList();
}
