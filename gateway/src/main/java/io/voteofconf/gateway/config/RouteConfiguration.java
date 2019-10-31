package io.voteofconf.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;

@Configuration
public class RouteConfiguration {

    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("frontend", r -> r
                        .path("/frontend/**")
                        .filters(f -> f
                                        .addRequestHeader("BASE_REDIRECT_URI", "http://frontend/localhost:8080")
                                        .filter((exchange, chain) ->{
                                            ServerHttpRequest request = exchange.getRequest();

                                            URI uri = exchange.getRequest().getURI();
                                            String headerValue = uri.toString().replace(uri.getPath(), "");

                                            exchange.mutate().request(request
                                                    .mutate()
                                                    .header("SERVER_URI", headerValue)
                                                    .build()).build();
                                            return chain.filter(exchange);
                                        })
//                                .filter((exchange, chain) -> ReactiveSecurityContextHolder.getContext()
//                                        .map(SecurityContext::getAuthentication)
//                                        .map(authentication -> (OAuth2AuthenticationToken)authentication)
//                                        .map(oAuth2AuthenticationToken ->
//                                                clientService
//                                                        .loadAuthorizedClient(
//                                                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
//                                                                oAuth2AuthenticationToken.getName())
//                                                        .subscribe(oAuth2AuthorizedClient ->
//                                                                exchange.getRequest()
//                                                                        .mutate()
//                                                                        .header("Authorization", "Bearer " +
//                                                                                oAuth2AuthorizedClient
//                                                                                        .getAccessToken()
//                                                                                        .getTokenValue())
//                                                                        .build()))
//                                        .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
//                                        .then(chain.filter(exchange)))
//                                .hystrix(config ->
//                                        config
//                                                .setName("frontend")
//                                                .setFallbackUri("forward:/fallback/frontend")
//                                )
                        )
                        .uri("lb://frontend"))
//                .route("payments", r -> r
//                        .path("/payments/**")
////                        .filters(f -> f
////                                .filter((exchange, chain) -> ReactiveSecurityContextHolder.getContext()
////                                        .map(SecurityContext::getAuthentication)
////                                        .map(authentication -> (OAuth2AuthenticationToken)authentication)
////                                        .map(oAuth2AuthenticationToken ->
////                                                clientService
////                                                        .loadAuthorizedClient(
////                                                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
////                                                                oAuth2AuthenticationToken.getName())
////                                                        .subscribe(oAuth2AuthorizedClient ->
////                                                                exchange.getRequest()
////                                                                        .mutate()
////                                                                        .header("Authorization", "Bearer " +
////                                                                                oAuth2AuthorizedClient
////                                                                                        .getAccessToken()
////                                                                                        .getTokenValue())
////                                                                        .build()))
////                                        .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
////                                        .then(chain.filter(exchange)))
////                                .hystrix(config ->
////                                        config
////                                                .setName("frontend")
////                                                .setFallbackUri("forward:/fallback/frontend")
////                                )
////                        )
//                        .uri("lb://payments")
//                )
                .build();
    }
}
