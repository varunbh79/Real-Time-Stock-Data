package com.boot.project.stocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:messaging_queues.properties")
@ConfigurationProperties(prefix = "app")
public class StockBootApplication {


	public static void main(String[] args) {
		SpringApplication.run(StockBootApplication.class, args);
	}



}
