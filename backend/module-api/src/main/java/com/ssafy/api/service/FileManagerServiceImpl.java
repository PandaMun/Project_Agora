package com.ssafy.api.service;

import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.FileManager;
import com.ssafy.repository.DebateRepository;
import com.ssafy.repository.FileManagerRepository;
import com.ssafy.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FileManagerServiceImpl implements FileManagerService{

    @Value("${file.path}")
    private String filePath;

    private final DebateRepository debateRepository;

    private final FileManagerRepository fileManagerRepository;

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileManager getFileManager(long debateID){
        Debate debate = debateRepository.findById(debateID).orElseThrow(NoSuchElementException::new);

        if(debate.getFileManager() == null) {
            FileManager fileManager = FileManager.builder()
                    .registTime(Date.from(Instant.now()))
                    .files(new ArrayList<>())
                    .build();

            fileManagerRepository.save(fileManager);
            debate.setFileManager(fileManager);
            Debate savedDebate = debateRepository.save(debate);
            return savedDebate.getFileManager();
        }
        return debate.getFileManager();
    }
}
