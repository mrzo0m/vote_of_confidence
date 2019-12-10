package io.voteofconf.gateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
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
                .route("tracker-microservice", r -> r
                        .path("/tracker-microservice/**")
                        .filters(f -> f
                                .rewritePath("/tracker-microservice/(?<path>.*)", "/$\\{path}")
                                .hystrix(config ->
                                        config
                                                .setName("tracker-microservice")
                                                .setFallbackUri("forward:/fallback/history")
                                )
                        )
                        .uri("http://history-microservice"))
                .route("frontend-microservice", r -> r
                        .path("/**")
                        .filters(f -> f
                                .filter(getFilterSecurityFunction())
                                .rewritePath("/tracker-microservice/(?<path>.*)", "/$\\{path}")
                                .hystrix(config ->
                                        config
                                                .setName("frontend-microservice")
                                                .setFallbackUri("forward:/fallback/frontend")
                                )
                        )
                        .uri("http://frontend-microservice"))

                .build();
    }

    private GatewayFilter getFilterSecurityFunction() {
        return (exchange, chain) -> ReactiveSecurityContextHolder.getContext()
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
                .then(chain.filter(exchange));
    }
}
