package com.ssafy.api.service;

import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.entity.rdbms.User;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.repository.FileManagerRepository;
import com.ssafy.repository.FileRepository;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileManagerRepository fileManagerRepository;

    @Value("${file.path}")
    private String filePath;

    @Override
    public void saveFile(MultipartFile file, Long fileManagerId) throws IOException {

        FileManager fileManager = fileManagerRepository.findById(fileManagerId).orElseThrow(() -> new IllegalArgumentException("fileManager가 존재하지 않습니다."));

        String fileName = UUID.randomUUID().toString();
        Path path = Paths.get(filePath + java.io.File.separator + fileName);
        long size = file.getSize();

        File newFile = File.builder()
                .originFileName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")))
                .extension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))
                .fileManager(fileManager)
                .savedFileName(fileName)
                .savedPath(path.toString())
                .deleted(false)
                .size(size)
                .build();

        file.transferTo(path);

        fileManager.getFiles().add(newFile);
        fileManagerRepository.save(fileManager);
    }
}
