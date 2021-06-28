package com.jancoyan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jancoyan.mapper")
@SpringBootApplication
public class JancoBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JancoBlogApplication.class, args);
    }

}
