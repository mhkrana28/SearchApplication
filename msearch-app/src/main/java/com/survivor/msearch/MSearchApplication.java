package com.survivor.msearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(MSearchApplication.class, args);
	}
}
