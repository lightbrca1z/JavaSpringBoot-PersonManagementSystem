package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringStart032Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringStart032Application.class, args);
	}

	@RequestMapping("/helloSpring2")
	public String index() {
		return "Hello, Spring Boot 3!!!";
	}

}
