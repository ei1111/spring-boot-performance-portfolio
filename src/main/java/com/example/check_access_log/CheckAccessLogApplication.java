package com.example.check_access_log;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class CheckAccessLogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckAccessLogApplication.class, args);
	}

}
