package com.ssafy.api.controller;

import com.ssafy.api.service.CommonCodeService;
import com.ssafy.api.service.CommonCodeServiceImpl;
import com.ssafy.common.model.response.BaseResponseDataBody;
import com.ssafy.entity.rdbms.CommonCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "공통 코드 API", tags = {"CommonCode"})
@RestController
@RequestMapping("/api/v1/codes")
@RequiredArgsConstructor
public class CommonCodeController {

    private final CommonCodeService commonCodeService;

    @GetMapping("/category")
    @ApiOperation(value = "카테고리 조회")
    public ResponseEntity<BaseResponseDataBody<List<CommonCode>>> searchCategory(){
        List<CommonCode> categories = commonCodeService.getCategoryList();
        BaseResponseDataBody<List<CommonCode>> response = BaseResponseDataBody.of("Success", 200, categories);
        return ResponseEntity.status(201).body(response);
    }
}
