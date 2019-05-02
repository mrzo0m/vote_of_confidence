package io.voteofconf.communications.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable().build();
//                .authorizeExchange()
//                .anyExchange().authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt().and().and().build();

    }
}