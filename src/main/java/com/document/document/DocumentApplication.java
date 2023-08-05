package com.document.document;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DocumentApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DocumentApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("HI FRANS I AM RIPON BIDIU");
	}
}
