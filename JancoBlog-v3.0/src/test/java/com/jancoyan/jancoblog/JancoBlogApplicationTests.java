package com.jancoyan.jancoblog;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MapperScan("com.jancoyan.jancoblog.mapper")
class JancoBlogApplicationTests {

    @Test
    void contextLoads() {
    }

}
