package com.ssafy.api.service;

import com.ssafy.entity.rdbms.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public interface CertificateService {
    InputStreamResource issueCertification(User user);
}
