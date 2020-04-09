package com.igomall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IgomallEduBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgomallEduBackApplication.class, args);
	}

}
