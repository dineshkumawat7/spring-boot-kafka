package com.service.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankTransactionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransactionServiceApplication.class, args);
	}

}
