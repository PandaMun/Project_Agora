package com.ssafy.api.response;

import com.ssafy.entity.rdbms.File;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ApiModel("FileDownloadResponse")
public class FileDownloadRes {

    @ApiModelProperty(name = "파일 원본 이름")
    private String fileName;

    @ApiModelProperty(name = "파일 고유 아이디")
    private String fileId;

    @ApiModelProperty(name = "파일 다운로드 URI")
    private String fileDownloadUri;

    @ApiModelProperty(name = "파일 사이즈")
    private long size;

    public static FileDownloadRes of(FileRes fileRes) {
        FileDownloadRes res = new FileDownloadRes();
        int savedFileNameLength = fileRes.getSavedFileName().length();
        String fileName = fileRes.getOriginFileName() + "_" + fileRes.getSavedFileName().substring(savedFileNameLength - 2, savedFileNameLength) + fileRes.getExtension();
        String id = Long.toString(fileRes.getFileId());
        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/downloads/")
                .path(id)
                .toUriString();
        res.setFileName(fileName);
        res.setFileId(Long.toString(fileRes.getFileId()));
        res.setFileDownloadUri(downloadUri);
        res.setSize(fileRes.getSize());
        return res;
    }

    public List<FileDownloadRes> toDtoList(List<FileRes> fileRes) {
        List<FileDownloadRes> fileDownloadResList = fileRes.stream()
                .map(file -> {
                    FileDownloadRes res = FileDownloadRes.of(file);
                    return res;
                }).collect(Collectors.toList());
        return fileDownloadResList;
    }
}
