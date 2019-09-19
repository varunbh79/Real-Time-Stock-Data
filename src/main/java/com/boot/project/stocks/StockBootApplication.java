package com.boot.project.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StockBootApplication {


	public static void main(String[] args) {
		SpringApplication.run(StockBootApplication.class, args);
	}



}
