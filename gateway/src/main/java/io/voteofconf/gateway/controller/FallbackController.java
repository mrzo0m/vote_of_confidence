package io.voteofconf.gateway.controller;

import io.voteofconf.gateway.config.RouteConfiguration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    @GetMapping("login")
    public Mono<String> login(ServerWebExchange exchange,
                              @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient) {
        RouteConfiguration.TOKEN = authorizedClient.getAccessToken().getTokenValue();
        return Mono.just("Frontend temporarily unavailable. Please try again later!");
    }

    @GetMapping("frontend")
    public Mono<String> getFrontendFallback(ServerWebExchange exchange,
                                            @RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient authorizedClient) {
        RouteConfiguration.TOKEN = authorizedClient.getAccessToken().getTokenValue();
        return Mono.just("Frontend temporarily unavailable. Please try again later!");
    }

}
