package com.example.elasticsearchtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.example.elasticsearchtest.*"})
public class ElasticsearchtestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchtestApplication.class, args);
	}

}
