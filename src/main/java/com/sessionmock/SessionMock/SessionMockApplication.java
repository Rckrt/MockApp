package com.sessionmock.SessionMock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SessionMockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionMockApplication.class, args);
	}
}
