package com.ssafy.api.service;

import com.ssafy.entity.rdbms.FileManager;

import java.io.IOException;

public interface FileManagerService {
    public FileManager getFileManager(long debateId);
}
