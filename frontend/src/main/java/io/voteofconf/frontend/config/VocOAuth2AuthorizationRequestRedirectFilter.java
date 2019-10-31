package io.voteofconf.frontend.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.*;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VocOAuth2AuthorizationRequestRedirectFilter extends OAuth2AuthorizationRequestRedirectFilter {
    /**
     * The default base {@code URI} used for authorization requests.
     */
    public static final String DEFAULT_AUTHORIZATION_REQUEST_BASE_URI = "/oauth2/authorization";
    private final ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();
    private final RedirectStrategy authorizationRedirectStrategy = new DefaultRedirectStrategy();
    private OAuth2AuthorizationRequestResolver authorizationRequestResolver;
    private AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository =
            new HttpSessionOAuth2AuthorizationRequestRepository();
    private RequestCache requestCache = new HttpSessionRequestCache();


    public VocOAuth2AuthorizationRequestRedirectFilter(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizationRequestResolver authorizationRequestResolver, AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository, RequestCache requestCache) {
        super(clientRegistrationRepository);
        this.authorizationRequestResolver = authorizationRequestResolver;
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.requestCache = requestCache;
    }

    public VocOAuth2AuthorizationRequestRedirectFilter(ClientRegistrationRepository clientRegistrationRepository, String authorizationRequestBaseUri, OAuth2AuthorizationRequestResolver authorizationRequestResolver, AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository, RequestCache requestCache) {
        super(clientRegistrationRepository, authorizationRequestBaseUri);
        this.authorizationRequestResolver = authorizationRequestResolver;
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.requestCache = requestCache;
    }

    public VocOAuth2AuthorizationRequestRedirectFilter(OAuth2AuthorizationRequestResolver authorizationRequestResolver, OAuth2AuthorizationRequestResolver authorizationRequestResolver1, AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository, RequestCache requestCache) {
        super(authorizationRequestResolver);
        this.authorizationRequestResolver = authorizationRequestResolver1;
        this.authorizationRequestRepository = authorizationRequestRepository;
        this.requestCache = requestCache;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            OAuth2AuthorizationRequest authorizationRequest = this.authorizationRequestResolver.resolve(request);
            if (authorizationRequest != null) {
                this.sendRedirectForAuthorization(request, response, authorizationRequest);
                return;
            }
        } catch (Exception failed) {
            this.unsuccessfulRedirectForAuthorization(request, response, failed);
            return;
        }

        try {
            filterChain.doFilter(request, response);
        } catch (IOException ex) {
            throw ex;
        } catch (Exception ex) {
            // Check to see if we need to handle ClientAuthorizationRequiredException
            Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(ex);
            ClientAuthorizationRequiredException authzEx = (ClientAuthorizationRequiredException) this.throwableAnalyzer
                    .getFirstThrowableOfType(ClientAuthorizationRequiredException.class, causeChain);
            if (authzEx != null) {
                try {
                    OAuth2AuthorizationRequest authorizationRequest = this.authorizationRequestResolver.resolve(request, authzEx.getClientRegistrationId());
                    if (authorizationRequest == null) {
                        throw authzEx;
                    }
                    this.sendRedirectForAuthorization(request, response, authorizationRequest);
                    SavedRequest newSavedRequest = new VocDefaultSavedRequest(request,  new PortResolverImpl());
                    request.getSession().setAttribute( "SPRING_SECURITY_SAVED_REQUEST", newSavedRequest);
                } catch (Exception failed) {
                    this.unsuccessfulRedirectForAuthorization(request, response, failed);
                }
                return;
            }

            if (ex instanceof ServletException) {
                throw (ServletException) ex;
            } else if (ex instanceof RuntimeException) {
                throw (RuntimeException) ex;
            } else {
                throw new RuntimeException(ex);
            }
        }
    }

    private void sendRedirectForAuthorization(HttpServletRequest request, HttpServletResponse response,
                                              OAuth2AuthorizationRequest authorizationRequest) throws IOException, ServletException {

        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(authorizationRequest.getGrantType())) {
            this.authorizationRequestRepository.saveAuthorizationRequest(authorizationRequest, request, response);
        }
        this.authorizationRedirectStrategy.sendRedirect(request, response, authorizationRequest.getAuthorizationRequestUri());
    }

    private void unsuccessfulRedirectForAuthorization(HttpServletRequest request, HttpServletResponse response,
                                                      Exception failed) throws IOException, ServletException {

        if (logger.isErrorEnabled()) {
            logger.error("Authorization Request failed: " + failed.toString(), failed);
        }
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
        protected void initExtractorMap() {
            super.initExtractorMap();
            registerExtractor(ServletException.class, throwable -> {
                ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
                return ((ServletException) throwable).getRootCause();
            });
        }
    }
}
