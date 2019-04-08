package io.voteofconf.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableResourceServer
//@EnableWebSecurity
@SpringBootApplication(exclude = {
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
public class FrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }


    /**
     * for more details see https://github.com/okta/samples-java-spring/blob/master/resource-server/src/main/java/com/okta/spring/example/ResourceServerExampleApplication.java
     */
    @Configuration
//    @EnableConfigurationProperties
//    @ConfigurationProperties
    static class OktaOAuth2WebSecurityConfigurerAdapter extends ResourceServerConfigurerAdapter {

//        @Autowired
//        private ClientRegistrationRepository clientRegistrationRepository;

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

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers().antMatchers("/api/**").and()
                    .authorizeRequests().antMatchers("/").permitAll();
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
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public String returnMe() {
        return "It's me!";
    }

    @GetMapping("/api/userProfile")
    @PreAuthorize("hasAuthority('SCOPE_profile')")
    public Map<String, Object> getUserDetails(JwtAuthenticationToken authentication) {
        return authentication.getTokenAttributes();
    }

}