package com.likelion.besession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BeSessionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeSessionApplication.class, args);
	}

}
