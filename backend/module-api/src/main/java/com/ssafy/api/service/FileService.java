package com.ssafy.api.service;

import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.entity.rdbms.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService{
    File saveDebateThumbnail(MultipartFile file, FileManager fileManager, String ownerEmail) throws IOException;

    File saveDebateFile(MultipartFile file, FileManager fileManager, String role, String ownerEmail) throws IOException;
}
