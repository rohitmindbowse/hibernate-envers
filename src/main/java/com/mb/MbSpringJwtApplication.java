package com.mb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:common.properties")
@PropertySource("classpath:exception-message.properties")
@PropertySource("classpath:response-message.properties")
@PropertySource("classpath:profiles/${spring.profiles.active}/application.properties")
public class MbSpringJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(MbSpringJwtApplication.class, args);
	}
}
