package com.ssafy.api.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
public class EmailAuthPasswordReq {

    private String password;
}
