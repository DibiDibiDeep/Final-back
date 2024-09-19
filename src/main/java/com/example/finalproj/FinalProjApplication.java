package com.example.finalproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FinalProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProjApplication.class, args);
	}

}