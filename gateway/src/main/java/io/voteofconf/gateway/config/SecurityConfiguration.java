package io.voteofconf.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    @Order(1)
    public SecurityWebFilterChain securityWebFilterChainFirst() {

        return ServerHttpSecurity
                .http()
                .securityMatcher(new OrServerWebExchangeMatcher(
                        new PathPatternParserServerWebExchangeMatcher("/history/**"),
                        new PathPatternParserServerWebExchangeMatcher("/tracker/**")))
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain securityWebFilterChainSecond(ServerHttpSecurity http) {
        return http
                .securityMatcher(new OrServerWebExchangeMatcher(
                        new PathPatternParserServerWebExchangeMatcher("/history/**"),
                        new PathPatternParserServerWebExchangeMatcher("/tracker/**")))
                .authorizeExchange()
                .anyExchange().authenticated()
                .and().logout().logoutUrl("/")
                .and().oauth2Login()
                .and().build();
    }
}