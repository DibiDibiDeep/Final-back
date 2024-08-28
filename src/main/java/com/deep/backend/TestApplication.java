package com.deep.backend;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.deep", annotationClass = Mapper.class)
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.deep.backend.TestApplication.class, args);
	}

}
