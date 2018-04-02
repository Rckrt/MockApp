package com.sessionmock.SessionMock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan
@EnableMongoRepositories
public class SessionMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionMockApplication.class, args);
	}
}
