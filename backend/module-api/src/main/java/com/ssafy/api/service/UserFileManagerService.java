package com.ssafy.api.service;

import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserFileManagerService {
   FileManager getFileManager(String userEmail) throws IOException;

   File getProfileUrl(FileManager fileManager);

   File saveFile(MultipartFile file, FileManager fileManager) throws IOException;
}
