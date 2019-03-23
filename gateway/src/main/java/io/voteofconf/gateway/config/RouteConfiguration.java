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
                .route("history-microservice", r -> r
                        .path("/history-microservice")
                        .filters(f -> f
                                .hystrix(config ->
                                        config
                                                .setName("history-microservice")
                                                .setFallbackUri("forward:/fallback/history")
                                )
                        )
                        .uri("http://history-microservice"))
                .route("frontend-microservice", r -> r
                        .path("/**")
                        .filters(f -> f
                                .hystrix(config ->
                                        config
                                                .setName("frontend-microservice")
                                                .setFallbackUri("forward:/fallback/frontend")
                                )
                        )
                        .uri("http://frontend-microservice"))

                .build();
    }
}
