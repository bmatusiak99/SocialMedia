package com.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserEditApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserEditApplication.class, args);
    }

}