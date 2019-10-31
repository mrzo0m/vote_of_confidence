package io.voteofconf.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterChainPostProcessor myBeanPostProcessor() {
        return new FilterChainPostProcessor();
    }
}
