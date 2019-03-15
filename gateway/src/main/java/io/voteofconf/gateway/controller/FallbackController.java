package io.voteofconf.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.gateway.route.RouteDefinition;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;

@RestController
@RequestMapping("fallback")
public class FallbackController {

    private DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator;

    @Autowired
    public FallbackController(DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator) {
        this.discoveryClientRouteLocator = discoveryClientRouteLocator;
    }

    @GetMapping("frontend")
    public Mono<String> getFrontendFallback() {
        discoveryClientRouteLocator.getRouteDefinitions()
            .subscribe(rd -> System.out.println(rd.getId() + " -> " + rd.getUri()));
        return Mono.just("Frontend temporarily unavailable. Please try again later!");
    }

}
