package com.jancoyan.jancoblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jancoyan.jancoblog.mapper")
public class JancoBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JancoBlogApplication.class, args);
    }

}
