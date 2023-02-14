package com.ssafy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class ModuleApiApplicationTests {

    @Test
    void contextLoads() {

        String org = "2023-02-12T20:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(org, formatter);
        System.out.println(dateTime);

    }

}
