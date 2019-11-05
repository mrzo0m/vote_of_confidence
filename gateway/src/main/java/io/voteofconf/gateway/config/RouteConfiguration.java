package io.voteofconf.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import reactor.core.publisher.Mono;

@Configuration
public class RouteConfiguration {

    private ReactiveOAuth2AuthorizedClientService clientService;

    private ApplicationContext applicationContext;

    private ServerOAuth2AuthorizedClientRepository clientRepository;

    private InMemoryReactiveClientRegistrationRepository registrationRepository;

    public RouteConfiguration(ReactiveOAuth2AuthorizedClientService clientService, ApplicationContext applicationContext, ServerOAuth2AuthorizedClientRepository clientRepository, InMemoryReactiveClientRegistrationRepository registrationRepository) {
        this.clientService = clientService;
        this.applicationContext = applicationContext;
        this.clientRepository = clientRepository;
        this.registrationRepository = registrationRepository;
    }

    @Bean
    public RouteLocator gatewayRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("frontend", r -> r
                        .path("/frontend/**")
                        .filters(f -> f
                                        .addRequestHeader("BASE_REDIRECT_URI", "http://frontend/localhost:8080")
//                                        .filter((exchange, chain) ->{
//                                            ServerHttpRequest request = exchange.getRequest();
//
//                                            URI uri = exchange.getRequest().getURI();
//                                            String headerValue = uri.toString().replace(uri.getPath(), "");
//
//                                            exchange.mutate().request(request
//                                                    .mutate()
//                                                    .header("SERVER_URI", headerValue)
//                                                    .build()).build();
//                                            return chain.filter(exchange);
//                                        })
                                .filter((exchange, chain) -> ReactiveSecurityContextHolder.getContext()
                                        .map(SecurityContext::getAuthentication)
                                        .map(authentication -> (OAuth2AuthenticationToken)authentication)
                                        .map(oAuth2AuthenticationToken ->
                                                clientService
                                                        .loadAuthorizedClient(
                                                                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                                                                oAuth2AuthenticationToken.getName())
                                                        .subscribe(oAuth2AuthorizedClient ->
                                                                exchange.getRequest()
                                                                        .mutate()
                                                                        .header("Authorization", "Bearer " +
                                                                                oAuth2AuthorizedClient
                                                                                        .getAccessToken()
                                                                                        .getTokenValue())
                                                                        .build()))
                                        .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
                                        .then(chain.filter(exchange)))
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
