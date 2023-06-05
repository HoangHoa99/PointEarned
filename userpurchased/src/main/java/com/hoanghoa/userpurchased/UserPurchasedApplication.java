package com.hoanghoa.userpurchased;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class UserPurchasedApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserPurchasedApplication.class, args);
	}

}
