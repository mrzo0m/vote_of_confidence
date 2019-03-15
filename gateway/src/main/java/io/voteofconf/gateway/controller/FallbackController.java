package io.voteofconf.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.gateway.route.RouteDefinition;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    @Autowired
    private DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator;

    @Bean
    public FallbackController(DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator) {
        this.discoveryClientRouteLocator = discoveryClientRouteLocator;
    }

    @GetMapping("frontend")
    public Mono<String> getFrontendFallback() {
        for (RouteDefinition rd: discoveryClientRouteLocator.getRouteDefinitions()) {
            System.out.println(rd.getId() + " -> " + rd.getUri());
        }
        return Mono.just("Frontend temporarily unavailable. Please try again later!");
    }

}
