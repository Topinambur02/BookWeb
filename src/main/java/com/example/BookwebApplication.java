package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class BookwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookwebApplication.class, args);
	}

}
