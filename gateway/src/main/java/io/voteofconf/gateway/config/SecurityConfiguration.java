package io.voteofconf.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.header.HttpHeaderWriterWebFilter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import sun.security.krb5.PrincipalName;

import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

//    @Bean
//    @Order(1)
//    WebFilterChainProxy webFilterChainProxy() {
//        return new WebFilterChainProxy(Collections.emptyList());
//    }

    @Bean
    @Order(3)
    public SecurityWebFilterChain securityWebFilterChainSecond(ServerHttpSecurity http) {
//        return http
//                .authorizeExchange()
//                .anyExchange().authenticated()
//                .and()
//                .oauth2Login()
//                .and()
//                .oauth2ResourceServer()
//                .jwt().and().and().build();

        return http.securityMatcher(new NegatedServerWebExchangeMatcher(new PathPatternParserServerWebExchangeMatcher("/payments/**")))
                .authorizeExchange()
                // allow antonymous access to the root page
//                .pathMatchers("/api/**").permitAll()
//                .pathMatchers("/payments/**").permitAll()
                // all other requests
                .anyExchange().authenticated()
                // set logout URL
                .and().logout().logoutUrl("/")
//                .and().formLogin()

                // enable OAuth2/OIDC
//                .and().oauth2Client()
                .and().oauth2Login()
                .and().build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain securityWebFilterChainFirst() {
//        return http
//                .authorizeExchange()
//                .anyExchange().authenticated()
//                .and()
//                .oauth2Login()
//                .and()
//                .oauth2ResourceServer()
//                .jwt().and().and().build();

        return ServerHttpSecurity
                .http()
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/payments/**"))
                .authorizeExchange()
                // allow antonymous access to the root page
                .anyExchange().permitAll()
                // all other requests
                .and()
                .build();
    }
}

