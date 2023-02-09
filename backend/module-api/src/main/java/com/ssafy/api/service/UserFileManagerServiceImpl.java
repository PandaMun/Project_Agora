package com.ssafy.api.service;

import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.entity.rdbms.User;
import com.ssafy.repository.FileManagerRepository;
import com.ssafy.repository.FileRepository;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserFileManagerServiceImpl implements UserFileManagerService{

    @Value("${file.path}")
    private String filePath;
    private final UserRepository userRepository;

    private final FileManagerRepository fileManagerRepository;

    private final FileRepository fileRepository;

    @Override
    public FileManager getFileManager(String userEmail) throws IOException {

        User user = userRepository.findByUserEmail(userEmail).orElseThrow(NoSuchElementException::new);

        //user에서 fileManager 뽑아오기
        if (user.getFileManager() == null) {

            FileManager fileManager = FileManager.builder()
                    .registTime(Date.from(Instant.now()))
                    .user(user)
                    .files(new ArrayList<>())
                    .build();
            user.createFileManager(fileManager);
            fileManagerRepository.save(fileManager);
            userRepository.save(user);
        }
       return user.getFileManager();
    }

    @Override
    public File getProfileUrl(FileManager fileManager) {
        File profileImgUrl = fileRepository.findProfileImgUrl(fileManager.getId());
        return profileImgUrl;
    }

    @Override
    public File saveFile(MultipartFile file, FileManager fileManager) throws IOException {

        if(fileManager.getFiles().size() >= 1) {
            File profilefile = getProfileUrl(fileManager);
            profilefile.updateDeleted();
            fileRepository.save(profilefile);
        }

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

        return newFile;
    }

}
