package com.reddit;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p -> p.path("/login")
                        .uri("lb://login-service"))
                .route(p -> p.path("/login/*")
                        .uri("lb://login-service"))
                .route(p -> p.path("/logout")
                        .uri("lb://login-service"))
                .route(p -> p.path("/registration")
                        .uri("lb://registration-service"))
                .route(p -> p.path("/activate/*")
                        .uri("lb://registration-service"))
                .route(p -> p.path("/profile")
                        .uri("lb://profile-service"))
                .route(p -> p.path("/user")
                        .uri("lb://user-service"))
                .route(p -> p.path("/user/*")
                        .uri("lb://user-service"))
                .route(p -> p.path("/user-messages/**")
                        .uri("lb://user-message-service"))
                .route(p -> p.path("/**")
                    .uri("lb://main-service"))
                .build();
    }
}
