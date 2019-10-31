package io.voteofconf.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;

@Configuration
@Slf4j
public class RouteConfiguration {

//    private ReactiveOAuth2AuthorizedClientService clientService;
//
//    public RouteConfiguration(ReactiveOAuth2AuthorizedClientService clientService, ApplicationContext applicationContext, ServerOAuth2AuthorizedClientRepository clientRepository, InMemoryReactiveClientRegistrationRepository registrationRepository) {
//        this.clientService = clientService;
//    }

    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("history-microservice", r -> r
                        .path("/history-microservice/**")
                        .filters(f -> f
                                .rewritePath("/history-microservice/(?<path>.*)", "/$\\{path}")
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
                                        .addRequestHeader("BASE_REDIRECT_URI", "http://localhost:8080")
                                        .filter((exchange, chain) ->{
                                            ServerHttpRequest request = exchange.getRequest();

                                            URI uri = exchange.getRequest().getURI();
                                            String headerValue = uri.toString().replace(uri.getPath(), "");

                                            log.info("SERVER_URI = " + headerValue);
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
