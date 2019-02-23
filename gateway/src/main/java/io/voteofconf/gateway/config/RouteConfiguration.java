package io.voteofconf.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {
    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("frontend", r -> r
                        .path("/**")
                        .filters(f -> f
                                .hystrix(config ->
                                        config
                                                .setName("frontend")
                                                .setFallbackUri("forward:/fallback/frontend")
                                )
                        )
                        .uri("lb://frontend"))
                .route("trace", r -> r
                        .path("/trace/**")
                        .uri("lb://trace")
                )
                .build();
    }
}