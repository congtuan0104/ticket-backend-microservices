package com.ticket.gateway.configs;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    // list public route: các route không cần xác thực token
    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",
            "/gallery/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
