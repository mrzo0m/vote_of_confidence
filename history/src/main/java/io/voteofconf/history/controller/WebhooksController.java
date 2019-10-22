package io.voteofconf.history.controller;

import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.controller.dto.WebhookSubscription;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log
@RestController
@RequestMapping("webhooks")
public class WebhooksController {


    @ApiOperation(value = "Post to invitee_created")
    @PostMapping("/invitee_created")
    public Mono<Void> inviteeCreated(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_created");
        return Mono.empty();
    }

    @ApiOperation(value = "Post to invitee_canceled")
    @PostMapping("/invitee_canceled")
    public Mono<Void> inviteeCanceled(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_canceled");
        return Mono.empty();
    }
}
