package com.esports.sanction_service;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableFeignClients
@SpringBootApplication
public class SanctionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SanctionServiceApplication.class, args);
	}

}
