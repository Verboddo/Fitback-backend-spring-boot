package com.smeekens.fitback.fitback.fitback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FitbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(com.smeekens.fitback.fitback.FitbackApplication.class, args);
	}

}
