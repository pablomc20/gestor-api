package com.gestor.dominator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DominatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DominatorApplication.class, args);
	}

}
