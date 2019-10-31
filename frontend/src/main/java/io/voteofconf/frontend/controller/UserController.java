package io.voteofconf.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
public class UserController {

    @Autowired
    private WebClient oktaApiWebClient;

    @RequestMapping(value = "/users/getUserWithLogin", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('experts_commitee')")
    public Flux<Object> getUserWithLogin(@RequestParam String login) {
        return oktaApiWebClient
                .method(HttpMethod.GET)
                .uri("https://dev-388275.oktapreview.com/api/v1/users/" + login )
                .retrieve()
                .bodyToFlux(Object.class);
    }
}
