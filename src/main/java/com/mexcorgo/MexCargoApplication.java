package com.mexcorgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync // here i enable async in my spring boot application
public class MexCargoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MexCargoApplication.class, args);
	}

}		

