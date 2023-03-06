package com.ssafy.api.controller;

import com.ssafy.api.service.CertificateService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Api(value = "활동증명서 발급 API", tags = {"Certification"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CertificateController {

    private final UserService userService;
    private final CertificateService certificateService;

    @ApiOperation(value = "토론 증명서 발급")
    @GetMapping("/certification/{userEmail}")
    public ResponseEntity<?> issueCertification(HttpServletRequest request, @PathVariable String userEmail){
        User user = userService.getUserByUserEmail(userEmail);

        try {
            InputStreamResource resource = certificateService.issueCertification(user);

            String fileName = URLEncoder.encode(user.getName() + "_토론_활동_증명서.pdf", "UTF-8");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .cacheControl(CacheControl.noCache())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .body(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.status(509).body(BaseResponseBody.of(509, "해당하는 파일이 존재하지 않습니다"));
        } catch (Exception e) {
            return ResponseEntity.status(509).body(BaseResponseBody.of(500, "서버에러"));
        }
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            log.info("Could not determine file type.");
//        }
//
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }
}
