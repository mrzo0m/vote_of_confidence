package io.voteofconf.frontend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationCodeGrantFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String AUTHORIZATION = "Authorization";

    private ClientRegistrationRepository clientRegistrationRepository;

    private OAuth2AuthorizedClientRepository clientRepository;


    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository clientRepository) {
        super();
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.clientRepository = clientRepository;
    }

    private JwkTokenStore jwkTokenStore() {
        ClientRegistration okta = clientRegistrationRepository.findByRegistrationId("okta");
        return new JwkTokenStore(okta.getProviderDetails().getJwkSetUri());
    }

    private DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwkTokenStore());
        return defaultTokenServices;
    }

    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected void configure(HttpSecurity http) throws Exception {
        OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
        authenticationManager.setClientDetailsService(new InMemoryClientDetailsService());
        authenticationManager.setTokenServices(tokenServices());
        authenticationManager.setResourceId("frontend");
        http

                .addFilterAfter(new VocOAuth2AuthorizationRequestRedirectFilter(
                        new VocDefaultOAuth2AuthorizationRequestResolver(
                                clientRegistrationRepository,
                                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI)), OAuth2AuthorizationRequestRedirectFilter.class)
                .addFilterAfter(new VocOAuth2AuthorizationCodeGrantFilter(
                        clientRegistrationRepository,
                        clientRepository,
                        authenticationManager), OAuth2AuthorizationCodeGrantFilter.class)
                .authorizeRequests()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and().oauth2ResourceServer()
                .authenticationEntryPoint(new VocLoginUrlAuthenticationEntryPoint("/login"))
                .jwt()
                .and()
                .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(
                        new VocLoginUrlAuthenticationEntryPoint("/oauth2/authorization/okta"),
                        new AntPathRequestMatcher("**"));
    }

    private ClientRegistration oktaClientCredentialsRegistration() {
        ClientRegistration okta = clientRegistrationRepository.findByRegistrationId("okta");
        return ClientRegistration
                .withRegistrationId("okta2")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .registrationId(okta.getRegistrationId())
                .clientSecret(okta.getClientSecret())
                .clientId(okta.getClientId())
                .clientName(okta.getClientName())
//                    .providerConfigurationMetadata(okta.getProviderDetails().getConfigurationMetadata())
                .authorizationUri(okta.getProviderDetails().getAuthorizationUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
                .tokenUri(okta.getProviderDetails().getTokenUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
                .jwkSetUri(okta.getProviderDetails().getJwkSetUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
                .scope(Collections.singletonList("history"))
                .redirectUriTemplate(okta.getRedirectUriTemplate())
                .clientAuthenticationMethod(okta.getClientAuthenticationMethod())
                .userInfoUri(okta.getProviderDetails().getUserInfoEndpoint().getUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
                .userNameAttributeName(okta.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName())
                .userInfoAuthenticationMethod(okta.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod())
                .build();
    }

    @Bean
    WebClient webClient() {
        ClientRegistration registration = oktaClientCredentialsRegistration();
        ClientRegistrationRepository clientRegistrations = new InMemoryClientRegistrationRepository(registration);
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrations,
                        new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(
                                new InMemoryOAuth2AuthorizedClientService(
                                        clientRegistrations)));
        // (optional) explicitly opt into using the oauth2Login to provide an access token implicitly
        // oauth.setDefaultOAuth2AuthorizedClient(true);
        // (optional) set a default ClientRegistration.registrationId
        // oauth.setDefaultClientRegistrationId("client-registration-id");
        oauth2.setDefaultOAuth2AuthorizedClient(true);
        oauth2.setDefaultClientRegistrationId("okta");
        return WebClient.builder()
                .apply(oauth2.oauth2Configuration())
                .build();
    }


    @Bean
    @ConfigurationProperties(prefix = "okta-api-token")
    public OktaApiToken oktaApiToken() {
        return new OktaApiToken();
    }

    @Bean
    WebClient oktaApiWebClient(OktaApiToken oktaApiToken) {
        return WebClient.builder()
                .defaultHeader(AUTHORIZATION, String.format("%s %s", oktaApiToken.getType(), oktaApiToken.getValue()))
                .build();
    }
}
