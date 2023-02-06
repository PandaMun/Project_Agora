package com.ssafy.modulechat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message implements Serializable {

    private String author;
    private String content;
    private String timestamp;
    private String roomId;


}
