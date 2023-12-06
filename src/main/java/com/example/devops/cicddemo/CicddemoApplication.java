package com.example.devops.cicddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CicddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CicddemoApplication.class, args);
		System.out.println("Donot want this verion to be sent to prod");
	}
	}
