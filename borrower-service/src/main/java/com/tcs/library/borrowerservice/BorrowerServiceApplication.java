package com.tcs.library.borrowerservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Borrower Microservice", version="1.0", description="Borrower Microservice"))
public class BorrowerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowerServiceApplication.class, args);
	}

}
