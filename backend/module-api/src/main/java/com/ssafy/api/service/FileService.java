package com.ssafy.api.service;

import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.entity.rdbms.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService{
    void saveFile(MultipartFile file, Long fileManagerId) throws IOException;

}
