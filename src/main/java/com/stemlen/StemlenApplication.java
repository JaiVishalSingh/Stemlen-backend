package com.stemlen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StemlenApplication {

	public static void main(String[] args) {
		SpringApplication.run(StemlenApplication.class, args);
	}

}
