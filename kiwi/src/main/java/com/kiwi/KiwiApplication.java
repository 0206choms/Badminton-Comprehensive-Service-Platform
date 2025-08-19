package com.kiwi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication
public class KiwiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiwiApplication.class, args);
    }

}
