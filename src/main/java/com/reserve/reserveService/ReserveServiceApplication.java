package com.reserve.reserveService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ReserveServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReserveServiceApplication.class, args);
	}

}
