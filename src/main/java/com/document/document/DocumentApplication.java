package com.document.document;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DocumentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DocumentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
