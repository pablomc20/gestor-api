package com.gestor.dominator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GestorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorApiApplication.class, args);
	}

}
