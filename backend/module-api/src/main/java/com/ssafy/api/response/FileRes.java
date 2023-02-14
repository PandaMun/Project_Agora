package com.ssafy.api.response;

import com.ssafy.entity.rdbms.File;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@ApiModel("FileResponse")
public class FileRes {
    @ApiModelProperty(name = "파일 고유 아이디")
    private long fileId;

    @ApiModelProperty(name = "파일 매니저 아이디")
    private long fileManagerId;

    @ApiModelProperty(name = "파일 원본 이름")
    private String originFileName;

    @ApiModelProperty(name = "파일 저장 이름")
    private String savedFileName;

    @ApiModelProperty(name = "파일 저장 경로")
    private String savedPath;

    @ApiModelProperty(name = "파일 사이즈")
    private long size;

    @ApiModelProperty(name = "파일 확장자")
    private String extension;

    @ApiModelProperty(name = "파일 배포자")
    private String userEmail;

    public static FileRes of(File file) {
        FileRes res = new FileRes();
        res.setFileId(file.getId());
        res.setFileManagerId(file.getFileManager().getId());
        res.setOriginFileName(file.getOriginFileName());
        res.setSavedFileName(file.getSavedFileName());
        res.setSavedPath(file.getSavedPath());
        res.setSize(file.getSize());
        res.setExtension(file.getExtension());
        res.setUserEmail(file.getUserEmail());
        return res;
    }

    public List<FileRes> toDtoList(List<File> files) {
        List<FileRes> fileResList = files.stream()
                .map(file -> {
                    FileRes res = FileRes.of(file);
                    return res;
                }).collect(Collectors.toList());
        return fileResList;
    }
}
