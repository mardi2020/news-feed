package com.mardi2020.feedeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class FeedEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedEurekaApplication.class, args);
    }

}
