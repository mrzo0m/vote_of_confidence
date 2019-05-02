package io.voteofconf.frontend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

@EnableEurekaClient
@EnableDiscoveryClient
//@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication(exclude = {
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
@Slf4j
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }


    /**
     * for more details see https://github.com/okta/samples-java-spring/blob/master/resource-server/src/main/java/com/okta/spring/example/ResourceServerExampleApplication.java
     */
//    @Configuration
//    @EnableConfigurationProperties
//    @ConfigurationProperties
    static class OktaOAuth2WebSecurityConfigurerAdapter extends ResourceServerConfigurerAdapter {

        @Autowired
        private ClientRegistrationRepository clientRegistrationRepository;

        @Autowired
        private JwtDecoder jwtDecoder;

//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//            ClientRegistration okta = clientRegistrationRepository.findByRegistrationId("okta");
//            RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
//            remoteTokenServices.setClientId(okta.getClientId());
//            remoteTokenServices.setClientSecret(okta.getClientSecret());
//            remoteTokenServices.setCheckTokenEndpointUrl(okta.getProviderDetails().getTokenUri());
//            remoteTokenServices.setTokenName("voc-token");
//            resources.tokenServices(remoteTokenServices);
//        }

        @Bean
        public JwkTokenStore jwkTokenStore() {
            ClientRegistration okta = clientRegistrationRepository.findByRegistrationId("okta");
            return new JwkTokenStore(okta.getProviderDetails().getJwkSetUri());
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(jwkTokenStore());
            return defaultTokenServices;
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenServices(tokenServices());
            resources.resourceId("frontend-api"); // synonym for audience
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/")
                    .permitAll()
                    .antMatchers("/**")
                    .authenticated();
//                    .and().oauth2ResourceServer().jwt();
        }


    }

    @Configuration
    public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {

        @Override
        @Order(Ordered.HIGHEST_PRECEDENCE)
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/api/**").permitAll()
                    .anyRequest().authenticated()
                    .and().oauth2Login();;
        }

    }
}

@RestController
class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @GetMapping("/jwkme")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @RequestMapping("/returnme")
    @PreAuthorize("hasAuthority('SCOPE_email')")
    public String returnMe() {
        return "It's me!";
    }

    @GetMapping("/api/userProfile")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public Map<String, Object> getUserDetails(JwtAuthenticationToken authentication) {
        return authentication.getTokenAttributes();
    }

}