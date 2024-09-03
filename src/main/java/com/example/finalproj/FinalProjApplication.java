package com.example.finalproj;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example", annotationClass = Mapper.class)
public class FinalProjApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjApplication.class, args);
    }

}
