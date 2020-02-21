package io.voteofconf.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Profile("!dev")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .httpBasic().disable()
                .requiresChannel().anyRequest().requiresSecure();
    }

    @Profile("!dev")
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
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