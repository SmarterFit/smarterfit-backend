package com.smarterfit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class SmarterFitApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(SmarterFitApplication.class, args);
	}
	@Override
	public void run(String... args){
		System.out.println("SmarterFit API is running");
		System.out.println("Acesse: http://localhost:8081/");
	}
}

