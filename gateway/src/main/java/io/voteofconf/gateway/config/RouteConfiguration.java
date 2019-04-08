package io.voteofconf.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

@Configuration
public class RouteConfiguration {

    public static String TOKEN;

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
                                .filter((exchange, chain) -> ReactiveSecurityContextHolder.getContext()
                                        .map(SecurityContext::getAuthentication)
                                        .map(authentication -> (OAuth2AuthenticationToken)authentication)
                                        .doOnNext(oAuth2AuthenticationToken ->
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
                                .hystrix(config ->
                                        config
                                                .setName("frontend")
                                                .setFallbackUri("forward:/fallback/frontend")
                                )
                        )
                        .uri("lb://frontend"))
                .route("trace", r -> r
                        .path("/trace/**")
                        .filters(f -> f
                                .filter((exchange, chain) -> {
                                    Authentication authentication =
                                            SecurityContextHolder
                                                    .getContext()
                                                    .getAuthentication();

                                    OAuth2AuthenticationToken oauthToken =
                                            (OAuth2AuthenticationToken) authentication;
                                    OAuth2AuthorizedClient client =
                                            clientService.loadAuthorizedClient(
                                                    oauthToken.getAuthorizedClientRegistrationId(),
                                                    oauthToken.getName()).block();

                                    String accessToken = client.getAccessToken().getTokenValue();
                                    exchange.getResponse()
                                            .getHeaders()
                                            .add("", "");
                                    return chain.filter(exchange);
                                }))
                        .uri("lb://trace")
                )
                .build();
    }
}
