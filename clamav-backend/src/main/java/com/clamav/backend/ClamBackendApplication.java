package com.clamav.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.clamav.backend.mapper")
public class ClamBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClamBackendApplication.class, args);
    }

}