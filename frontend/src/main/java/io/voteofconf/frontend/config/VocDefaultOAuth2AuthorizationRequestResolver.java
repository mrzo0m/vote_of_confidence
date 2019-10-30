package io.voteofconf.frontend.config;

import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class VocDefaultOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private static final String REGISTRATION_ID_URI_VARIABLE_NAME = "registrationId";
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final AntPathRequestMatcher authorizationRequestMatcher;
    private final StringKeyGenerator stateGenerator = new Base64StringKeyGenerator(Base64.getUrlEncoder());

    /**
     * Constructs a {@code DefaultOAuth2AuthorizationRequestResolver} using the provided parameters.
     *
     * @param clientRegistrationRepository the repository of client registrations
     * @param authorizationRequestBaseUri the base {@code URI} used for resolving authorization requests
     */
    public VocDefaultOAuth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository,
                                                     String authorizationRequestBaseUri) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        Assert.hasText(authorizationRequestBaseUri, "authorizationRequestBaseUri cannot be empty");
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizationRequestMatcher = new AntPathRequestMatcher(
                authorizationRequestBaseUri + "/{" + REGISTRATION_ID_URI_VARIABLE_NAME + "}");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        String registrationId = this.resolveRegistrationId(request);
        String redirectUriAction = getAction(request, "login");
        return resolve(request, registrationId, redirectUriAction);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String registrationId) {
        if (registrationId == null) {
            return null;
        }
        String redirectUriAction = getAction(request, "authorize");
        return resolve(request, registrationId, redirectUriAction);
    }

    private String getAction(HttpServletRequest request, String defaultAction) {
        String action = request.getParameter("action");
        if (action == null) {
            return defaultAction;
        }
        return action;
    }

    private OAuth2AuthorizationRequest resolve(HttpServletRequest request, String registrationId, String redirectUriAction) {
        if (registrationId == null) {
            return null;
        }

        ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(registrationId);
        if (clientRegistration == null) {
            throw new IllegalArgumentException("Invalid Client Registration with Id: " + registrationId);
        }

        OAuth2AuthorizationRequest.Builder builder;
        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(clientRegistration.getAuthorizationGrantType())) {
            builder = OAuth2AuthorizationRequest.authorizationCode();
        } else if (AuthorizationGrantType.IMPLICIT.equals(clientRegistration.getAuthorizationGrantType())) {
            builder = OAuth2AuthorizationRequest.implicit();
        } else {
            throw new IllegalArgumentException("Invalid Authorization Grant Type ("  +
                    clientRegistration.getAuthorizationGrantType().getValue() +
                    ") for Client Registration with Id: " + clientRegistration.getRegistrationId());
        }

        String redirectUriStr = this.expandRedirectUri(request, clientRegistration, redirectUriAction);

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put(OAuth2ParameterNames.REGISTRATION_ID, clientRegistration.getRegistrationId());

        OAuth2AuthorizationRequest authorizationRequest = builder
                .clientId(clientRegistration.getClientId())
                .authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri())
                .redirectUri(redirectUriStr)
                .scopes(clientRegistration.getScopes())
                .state(this.stateGenerator.generateKey())
                .additionalParameters(additionalParameters)
                .build();

        return authorizationRequest;
    }

    private String resolveRegistrationId(HttpServletRequest request) {
        if (this.authorizationRequestMatcher.matches(request)) {
            return this.authorizationRequestMatcher
                    .extractUriTemplateVariables(request).get(REGISTRATION_ID_URI_VARIABLE_NAME);
        }
        return null;
    }

    private String expandRedirectUri(HttpServletRequest request, ClientRegistration clientRegistration, String action) {
        // Supported URI variables -> baseUrl, action, registrationId
        // Used in -> CommonOAuth2Provider.DEFAULT_REDIRECT_URL = "{baseUrl}/{action}/oauth2/code/{registrationId}"
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("registrationId", clientRegistration.getRegistrationId());
        String baseUrl = null;// UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request))
//                .replaceQuery(null)
//                .replacePath(request.getContextPath())
//                .build()
//                .toUriString();

        // override baseUri
//        if (request.getHeader("BASE_REDIRECT_URI") != null) {
//            baseUrl = request.getHeader("BASE_REDIRECT_URI");
            baseUrl = "http://XN--B1AAFFPUNCUOL5M.XN--P1AI";
//        }

        uriVariables.put("baseUrl", baseUrl);
        if (action != null) {
            uriVariables.put("action", action);
        }
        return UriComponentsBuilder.fromUriString(clientRegistration.getRedirectUriTemplate())
                .buildAndExpand(uriVariables)
                .toUriString();
    }
}
