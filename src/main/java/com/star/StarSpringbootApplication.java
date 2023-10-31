package com.star;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 博客启动类
 */
@SpringBootApplication
@MapperScan("com.star.mapper")
public class StarSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarSpringbootApplication.class, args);
    }
}
