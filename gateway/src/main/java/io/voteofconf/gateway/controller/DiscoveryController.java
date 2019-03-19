package io.voteofconf.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("discovery")
public class DiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("getServices")
    public Mono<List<String>> getServices() {
        return Flux.fromIterable(discoveryClient.getServices()).collectList();
    }

    @GetMapping("hi")
    public Mono<String> getHi() {
        return Mono.just("hi");
    }

    @GetMapping("getInfo")
    public Mono<String> getInfo() {
        return Mono.just(discoveryClient.description());
    }
}
