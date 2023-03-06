package com.ssafy.api.controller;

import com.ssafy.api.response.DebateRes;
import com.ssafy.api.response.FileDownloadRes;
import com.ssafy.api.response.FileRes;
import com.ssafy.api.service.DebateService;
import com.ssafy.api.service.FileManagerService;
import com.ssafy.api.service.FileService;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 토론 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "파일 API", tags = {"File"})
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileManagerService fileManagerService;

    private final FileService fileService;

    private final DebateService debateService;

    @Value("${file.default-thumbnail-path}")
    private String defaultThumbnail;

    @PatchMapping("/thumbnail/{debateId}")
    @ApiOperation(value = "토론 썸네일 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 509, message = "토론 없음"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 511, message = "파일 입출력 오류")
    })
    public ResponseEntity<?> modifyThumbnail(@PathVariable long debateId, MultipartFile file, @ApiIgnore Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        DebateRes debateRes = debateService.search(debateId);
        String checkUserEmail = debateRes.getOwnerEmail();

        if (!checkUserEmail.equals(userEmail)) {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "접근 권한이 없습니다."));
        }
        try {
            FileManager fileManager = fileManagerService.getFileManager(debateId);
            fileService.saveDebateThumbnail(file, fileManager, userEmail);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(509).body(BaseResponseBody.of(509, "해당하는 토론이 존재하지 않습니다"));
        } catch (IOException e) {
            return ResponseEntity.status(511).body(BaseResponseBody.of(511, "잘못된 파일 입니다"));
        }
        return ResponseEntity.status(201).body(BaseResponseBody.of(200, "썸네일 업로드 성공"));
    }

    @GetMapping("/downloads/{fileId}")
    @ApiOperation(value = "토론 파일 다운로드")
    public ResponseEntity<?> downloadDebateFile(@PathVariable long fileId) {

        File file = fileService.searchById(fileId);

        Path path = Paths.get(file.getSavedPath()).toAbsolutePath().normalize();

        try {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toString()));
            String fileName = file.getOriginFileName() + file.getExtension();
            fileName = URLEncoder.encode(fileName, "UTF-8");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .cacheControl(CacheControl.noCache())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .body(resource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(509).body(BaseResponseBody.of(509, "해당하는 파일이 존재하지 않습니다"));
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(509).body(BaseResponseBody.of(500, "서버에러"));
        }
    }

    @PatchMapping("/uploads/{debateId}")
    @ApiOperation(value = "토론 파일 업로드")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 509, message = "토론 없음"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 511, message = "파일 입출력 오류")
    })
    public ResponseEntity<?> uploadDebateFile(@PathVariable long debateId, MultipartFile file, @RequestParam String role, @ApiIgnore Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();

        DebateRes debateRes = debateService.search(debateId);
        String checkUserEmail = debateRes.getOwnerEmail();

        if (!checkUserEmail.equals(userEmail)) {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "접근 권한이 없습니다."));
        }
        try {
            FileManager fileManager = fileManagerService.getFileManager(debateId);
            File newFile = fileService.saveDebateFile(file, fileManager, role, userEmail);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(509).body(BaseResponseBody.of(509, "해당하는 토론이 존재하지 않습니다"));
        } catch (IOException e) {
            return ResponseEntity.status(511).body(BaseResponseBody.of(511, "잘못된 파일 입니다"));
        }
        return ResponseEntity.status(201).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/list/{debateId}")
    @ApiOperation(value = "토론 파일 목록")
    public ResponseEntity<?> searchAll(@PathVariable long debateId) {

        DebateRes debates = debateService.search(debateId);
        if (debates.getFileList() == null) {
            return ResponseEntity.status(200).body(null);
        }
        List<FileRes> files = debates.getFileList()
                .stream()
                .filter(fileRes -> !fileRes.getSavedFileName().contains("thumbnail") && !fileRes.isDeleted())
                .collect(Collectors.toList());
        List<FileDownloadRes> fileList = new FileDownloadRes().toDtoList(files);
        return ResponseEntity.status(200).body(fileList);

    }

    @GetMapping("/images/{debateId}")
    @ApiOperation(value = "이미지 파일")
    public Resource showImage(@PathVariable long debateId) throws MalformedURLException {

        DebateRes debate = debateService.search(debateId);
        Path path = null;
        String fileName = null;
        if (debate.getFileList() != null) {
            List<FileRes> files = debate.getFileList()
                    .stream()
                    .filter(file -> file.getSavedFileName().contains("thumbnail") && !file.isDeleted())
                    .collect(Collectors.toList());
            if (files.size() != 0) {
                FileRes file = files.get(0);
                path = Paths.get(files.get(0).getSavedPath()).toAbsolutePath().normalize();
                fileName = file.getOriginFileName() + file.getExtension();
            } else {
                path = Paths.get(defaultThumbnail).toAbsolutePath().normalize();
                fileName = "agora.png";
            }
        } else {
            path = Paths.get(defaultThumbnail).toAbsolutePath().normalize();
            fileName = "agora.png";
        }
        return new UrlResource("file:" + path);
    }

}
