package com.ssafy.api.service;

import com.ssafy.api.response.UserDebateHistory;
import com.ssafy.common.util.PdfGenerator;
import com.ssafy.common.util.ThymeleafParser;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.User;
import com.ssafy.entity.rdbms.UserDebate;
import com.ssafy.repository.UserDebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
//    @Value("${file.path-certificate-dev}")
    @Value("${file.path-certificate}")
    private String directoryPath;

    private final UserDebateRepository userDebateRepository;

    @Override
    public InputStreamResource issueCertification(User user) {
        String templateFileName = "certificate.html";

        List<UserDebate> histories = userDebateRepository.findAllByUserId(user.getId());
        Map<String, Object> variables = getVariables(user, histories);

        // 테스트용
//        List<UserDebateHistory> histories = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            histories.add(UserDebateHistory.builder()
//                                           .debateId(1l)
//                                           .title("토론 주제 " + i)
//                                           .date(LocalDateTime.now())
//                                           .activeTime(30l)
//                                           .build());
//        }
//
//        variables.put("histories", histories);

        try {
            String html = ThymeleafParser.parseHtmlFileToString(templateFileName, variables);
            String fileName = user.getName() + "_토론_활동_증명서";
            PdfGenerator.generateFromHtml(directoryPath, fileName, html);
            Path filePath = Paths.get((directoryPath + "/static/temp"+ java.io.File.separator + fileName + ".pdf")).toAbsolutePath().normalize();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(filePath.toString()));

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException(templateFileName + " 파일을 찾을 수 없습니다.");
            }
        }catch (FileNotFoundException e) {
            throw new RuntimeException(templateFileName + " 파일을 찾을 수 없습니다.", e);
        }
    }

    private Map<String, Object> getVariables(User user, List<UserDebate> histories) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getName());
        variables.put("email", user.getUserEmail());
        variables.put("histories",
                histories.stream()
                         .map(x -> {
                             Debate debate = x.getDebate();
                             long activeTime = Duration.between(debate.getCallStartTime(), debate.getCallEndTime()).toMinutes();
                             return UserDebateHistory.builder()
                                                     .date(debate.getInsertedTime())
                                                     .title(debate.getTitle())
                                                     .role(x.getRole())
                                                     .debateId(debate.getId())
                                                     .activeTime(activeTime)
                                                     .build();
                         }).collect(Collectors.toList()));
        variables.put("totalTime", histories.stream().mapToLong(x -> {
                                                Debate debate = x.getDebate();
                                                return Duration.between(debate.getCallStartTime(), debate.getCallEndTime()).toMinutes();
                                            }).
                                            sum());
        return variables;
    }
}
