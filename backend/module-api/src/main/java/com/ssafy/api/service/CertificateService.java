package com.ssafy.api.service;

import com.ssafy.entity.rdbms.User;
import org.springframework.core.io.Resource;

public interface CertificateService {
    Resource issueCertification(User user);
}
