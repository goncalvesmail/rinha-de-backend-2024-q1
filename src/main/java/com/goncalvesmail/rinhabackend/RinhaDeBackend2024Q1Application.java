package com.goncalvesmail.rinhabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@EnableWebMvc
public class RinhaDeBackend2024Q1Application {
	public static void main(String[] args) {
		SpringApplication.run(RinhaDeBackend2024Q1Application.class, args);
	}
}
