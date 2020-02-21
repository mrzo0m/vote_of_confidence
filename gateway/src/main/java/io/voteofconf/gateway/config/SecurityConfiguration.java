package io.voteofconf.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {


    @Profile("!dev")
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .authorizeExchange()
                .anyExchange().permitAll().and().build();
    }

    @Profile("dev")
    @Bean
    public SecurityWebFilterChain securityDevWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .cors().disable()
                .authorizeExchange()
                .anyExchange().permitAll().and().build();
    }
}