package com.microservice.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients(basePackages = "com.microservice.order_service")
@RestController
@SpringBootApplication
public class OrderServiceApplication {

//	Dotenv dotenv = Dotenv.load();
//
//	Set environment variables for Spring Boot to read (these will override application.properties values)
//	System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL", "jdbc:mysql://localhost:3306/defaultdb"));
//	System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME", "defaultUser"));
//	System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD", "defaultPassword"));
//	System.setProperty("PRODUCT_SERVICE_URL", dotenv.get("PRODUCT_SERVICE_URL", "http://localhost:5000"));

	@GetMapping("/")
	public String helloWorld(){
		return "Hello World";
	}

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
