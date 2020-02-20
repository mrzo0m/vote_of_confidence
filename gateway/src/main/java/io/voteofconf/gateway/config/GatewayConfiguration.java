package io.voteofconf.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Configuration
public class GatewayConfiguration {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    public DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator(DiscoveryClient discoveryClient,
                                                                             DiscoveryLocatorProperties properties) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient,
                properties);
    }

    @Bean
    @Profile("!dev")
    public WebFilter httpsRedirectFilter() {
        return new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                URI originalUri = exchange.getRequest().getURI();

                //here set your condition to http->https redirect
                List<String> forwardedValues = exchange.getRequest().getHeaders().get("x-forwarded-proto");
                if (forwardedValues != null && forwardedValues.contains("http")) {
                    try {
                        URI mutatedUri = new URI("https",
                                originalUri.getUserInfo(),
                                originalUri.getHost(),
                                originalUri.getPort(),
                                originalUri.getPath(),
                                originalUri.getQuery(),
                                originalUri.getFragment());
                        ServerHttpResponse response = exchange.getResponse();
                        response.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
                        response.getHeaders().setLocation(mutatedUri);
                        return Mono.empty();
                    } catch (URISyntaxException e) {
                        throw new IllegalStateException(e.getMessage(), e);
                    }
                }
                return chain.filter(exchange);
            }
        };
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
