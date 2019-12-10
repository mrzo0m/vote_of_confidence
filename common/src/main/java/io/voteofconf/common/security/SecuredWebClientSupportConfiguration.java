package io.voteofconf.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class SecuredWebClientSupportConfiguration {

    private ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    WebClient webClient() {
        ClientRegistration registration = clientRegistrationRepository.findByRegistrationId("okta2");//oktaClientCredentialsRegistration();
        ClientRegistrationRepository clientRegistrations = new InMemoryClientRegistrationRepository(registration);
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrations,
                        new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(
                                new InMemoryOAuth2AuthorizedClientService(
                                        clientRegistrations)));
        // (optional) explicitly opt into using the oauth2Login to provide an access token implicitly
        // oauth.setDefaultOAuth2AuthorizedClient(true);
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        oauth2.setDefaultClientRegistrationId("okta2");
        return WebClient.builder()
                .apply(oauth2.oauth2Configuration())
                .build();
    }
}
