package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringStart033Application {

	String[][] data = {
			{"noname", "no mail address", "0"},
			{"taro", "taro@yamada", "39"},
			{"hanako", "hanako@flower", "28"},
			{"sachiko", "sachiko@happy", "17"},
			{"jiro", "jiro@change", "6"}
	};

	public static void main(String[] args) {
		SpringApplication.run(SpringStart033Application.class, args);
	}

	@RequestMapping("/helloSpring3/{num}")
	public String index(@PathVariable int num) {
		//三項演算子 (条件 ? A : B)
		int n = num < 0 ? 0 : num >= data.length ? 0 : num;
		String[] item = data[n];
		String msg = "ID=%s. {name: %s, mail: %s, age: %s}";
		return String.format(msg, num, item[0], item[1], item[2]);
	}

}
