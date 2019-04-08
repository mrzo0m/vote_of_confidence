package io.voteofconf.gateway.config;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

//@Component
public class RequestTokenFilter implements WebFilter {

    private ReactiveOAuth2AuthorizedClientService clientService;

    private ApplicationContext applicationContext;

    private ServerOAuth2AuthorizedClientRepository clientRepository;

    private InMemoryReactiveClientRegistrationRepository registrationRepository;

    public RequestTokenFilter(ReactiveOAuth2AuthorizedClientService clientService, ApplicationContext applicationContext, ServerOAuth2AuthorizedClientRepository clientRepository, InMemoryReactiveClientRegistrationRepository registrationRepository) {
        this.clientService = clientService;
        this.applicationContext = applicationContext;
        this.clientRepository = clientRepository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
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
                                                .header("Authorization", "Bearer " + oAuth2AuthorizedClient.getAccessToken().getTokenValue())
                                                .build()))
                .then(chain.filter(exchange));
//        Hooks.onEachOperator(objectPublisher -> {
//            System.out.println(objectPublisher);
//            return objectPublisher;
//        });
//        exchange.getSession()
//                .subscribe(webSession -> clientRepository.loadAuthorizedClient("okta",
//                        ((SecurityContext)webSession.getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication(),
//                        exchange)
//                .subscribe(oAuth2AuthorizedClient ->
//                        exchange.getRequest()
//                                .mutate()
//                                .header("Authorization", "Bearer " + oAuth2AuthorizedClient.getAccessToken().getTokenValue())
//                                .build()));
//        Hooks.resetOnEachOperator();
    }
}
