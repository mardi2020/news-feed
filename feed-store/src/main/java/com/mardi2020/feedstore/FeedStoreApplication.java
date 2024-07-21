package com.mardi2020.feedstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FeedStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedStoreApplication.class, args);
	}

}
