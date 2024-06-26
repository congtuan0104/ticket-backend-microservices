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
                .route("gallery-service", r -> r.path("/api/gallery/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://gallery-service"))
                .route("auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://auth-service"))
                .route("statistic-service", r -> r.path("/api/test/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://statistic-service"))
                .route("demo-express-service", r -> r.path("/api/express/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://demo-express-service"))
                .route("ticket-service", r-> r.path("/api/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ticket-service"))
                .build();
    }
}