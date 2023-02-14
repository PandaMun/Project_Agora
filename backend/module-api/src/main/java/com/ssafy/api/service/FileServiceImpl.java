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

    private final FileRepository fileRepository;

    private final FileManagerRepository fileManagerRepository;

    @Value("${file.path}")
    private String filePath;

    @Override
    @Transactional
    public File saveDebateThumbnail(MultipartFile file, FileManager fileManager, String ownerEmail) throws IOException {

        if (fileManager.getFiles().size() >= 1) {
            fileRepository.updateThumbnailState(fileManager.getId());
        }

        String fileName = UUID.randomUUID().toString();
        String saveFileName = fileName + "_thumbnail";
        Path path = Paths.get(filePath + java.io.File.separator + fileName);
        long size = file.getSize();

        File savedFile = saveFile(saveFileName, path, size,ownerEmail, file, fileManager);

        fileManager.getFiles().add(savedFile);
        fileManagerRepository.save(fileManager);

        return savedFile;
    }

    @Override
    @Transactional
    public File saveDebateFile(MultipartFile file, FileManager fileManager, String role, String ownerEmail) throws IOException {
        String fileName = UUID.randomUUID().toString();
        String saveFileName = fileName + "_" + role;
        Path path = Paths.get(filePath + java.io.File.separator + fileName);
        long size = file.getSize();

        File savedFile = saveFile(saveFileName, path, size,ownerEmail, file, fileManager);

        fileManager.getFiles().add(savedFile);
        fileManagerRepository.save(fileManager);
        return savedFile;
    }

    private File saveFile(String saveFileName, Path path, long size, String ownerEmail, MultipartFile file, FileManager fileManager) throws IOException {

        File newFile = File.builder()
                .originFileName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")))
                .extension(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")))
                .fileManager(fileManager)
                .userEmail(ownerEmail)
                .savedFileName(saveFileName)
                .savedPath(path.toString())
                .deleted(false)
                .size(size)
                .build();

        file.transferTo(path);

        return newFile;
    }


}
