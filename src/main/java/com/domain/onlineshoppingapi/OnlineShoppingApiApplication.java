package com.domain.onlineshoppingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.domain.onlineshoppingapi")
public class OnlineShoppingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingApiApplication.class, args);
	}
}
