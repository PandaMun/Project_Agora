package com.ssafy.api.response;

import com.ssafy.entity.rdbms.Faq;
import com.ssafy.entity.rdbms.UserDebate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class UserDebateHistory {

    private final String date;

    private final String title;

    private final long debateId;

    private final String role;

    private final long activeTime;

    @Builder
    public UserDebateHistory(LocalDateTime date, String title, Long debateId, String role, long activeTime) {
        this.date = date.format(DateTimeFormatter.ISO_DATE);
        this.title = title;
        this.debateId = debateId;
        this.role = role;
        this.activeTime = activeTime;
    }

}
