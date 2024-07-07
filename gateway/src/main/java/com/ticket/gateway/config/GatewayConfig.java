package com.ticket.gateway.config;

import com.ticket.gateway.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("event-service", r -> r.path("/api/event/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://event-service"))
                .route("organization-service", r -> r.path("/api/organization/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://organization-service"))
                .route("upload-service", r -> r.path("/api/upload/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://upload-service"))
                .route("payment-service", r -> r.path("/api/payment/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://payment-service"))
                .route("promotion-service", r -> r.path("/api/promotion/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://payment-service"))
                .build();
    }
}