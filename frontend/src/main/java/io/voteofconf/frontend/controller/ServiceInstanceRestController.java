package io.voteofconf.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

    @RestController
public class ServiceInstanceRestController {
    @Autowired
    WebClient webClient;

    @Autowired
    WebClient oktaApiWebClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/jwkme")
    public String index(JwtAuthenticationToken authentication) {

        Object token = webClient
                .get()
                .uri("http://localhost:8080/payments/freeCharge")
                .retrieve()
                .bodyToMono(Object.class)
                .block(Duration.ofSeconds(10L));
        return String.format("Hello, %s!", token);
//
//            ClientRegi    stration okta = clientRegistrationRepository.findByRegistrationId("okta");
//            ClientRegistration oktaCredentials = ClientRegistration
//                    .withRegistrationId("okta2")
//                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                    .registrationId(okta.getRegistrationId())
//                    .clientSecret(okta.getClientSecret())
//                    .clientId(okta.getClientId())
//                    .clientName(okta.getClientName())
////                    .providerConfigurationMetadata(okta.getProviderDetails().getConfigurationMetadata())
//                    .authorizationUri(okta.getProviderDetails().getAuthorizationUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
//                    .tokenUri(okta.getProviderDetails().getTokenUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
//                    .jwkSetUri(okta.getProviderDetails().getJwkSetUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
//                    .scope(Collections.singletonList("history"))
//                    .redirectUriTemplate(okta.getRedirectUriTemplate())
//                    .clientAuthenticationMethod(okta.getClientAuthenticationMethod())
//                    .userInfoUri(okta.getProviderDetails().getUserInfoEndpoint().getUri().replace("default", "auskpi1fnzgPD6i6N0h7"))
//                    .userNameAttributeName(okta.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName())
//                    .userInfoAuthenticationMethod(okta.getProviderDetails().getUserInfoEndpoint().getAuthenticationMethod())
//                    .build();
//
//            OidcUser oidcUser = (OidcUser) oAuth2AuthenticationToken.getPrincipal();
//            OAuth2AccessTokenResponse tokenResponse = clientCredentialsClient
//                    .getTokenResponse(
//                            new OAuth2ClientCredentialsGrantRequest(oktaCredentials));
//            oidcUser.getAttributes().put(CLIENT_CREDENTIALS_TOKEN, tokenResponse.getAccessToken());
//            return String.format("Hello, %s!", oidcUser.getName());
    }

//        @RequestMapping("/returnMe")
//        @PreAuthorize("hasAuthority('SCOPE_openid')")
//        public String returnMe(@AuthenticationPrincipal OidcUser oidcUser) {
//            oidcUser.getAttributes().get(CLIENT_CREDENTIALS_TOKEN);
//            return "It's me!";
//        }

    @RequestMapping("/returnThem")
    @PreAuthorize("hasAuthority('SCOPE_email')")
    public String returnThem() {
        return "It's they!";
    }

    @RequestMapping("/returnHim")
    @PreAuthorize("hasAuthority('experts_commitee')")
    public Flux<Object> returnHim() {
        return oktaApiWebClient
                .method(HttpMethod.GET)
                .uri("https://dev-388275.oktapreview.com//api/v1/groups")
                .retrieve()
                .bodyToFlux(Object.class);
    }

    @GetMapping("/api/userProfile")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public Map<String, Object> getUserDetails(JwtAuthenticationToken authentication) {
        return authentication.getTokenAttributes();
    }
}
