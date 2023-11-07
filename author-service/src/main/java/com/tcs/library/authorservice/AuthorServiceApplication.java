package com.tcs.library.authorservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Author Microservice", version="1.0", description="Author Microservice"))
public class AuthorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorServiceApplication.class, args);
	}

}
