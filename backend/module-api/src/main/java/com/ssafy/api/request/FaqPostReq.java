package com.ssafy.api.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqPostReq {
    private String category;
    private String content;

    private String comment;


}
