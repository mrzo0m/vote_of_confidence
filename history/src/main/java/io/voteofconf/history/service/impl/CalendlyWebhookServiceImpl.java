package io.voteofconf.history.service.impl;

import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.service.CalendlyWebhookService;
import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import io.voteofconf.history.statemachine.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class CalendlyWebhookServiceImpl implements CalendlyWebhookService {

    @Autowired
    StateMachineFactory<States, Events> stateMachineFactory;

    @Override
    public void inviteeCreated(WebhookSubscription invitee) {
        Mono.just(stateMachineFactory)
                .publishOn(Schedulers.elastic())
                .map(StateMachineFactory::getStateMachine)
                .map(stateMachine ->
                        stateMachine.sendEvent(
                                Mono.just(MessageBuilder
                                        .withPayload(Events.STORE)
                                        .setHeader(Variables.INVITE.toString(), invitee)
                                        .build()))
                                .doOnComplete(() -> {
                                    System.out.println("Event handling complete");
                                    Message<Events> addTaskEvent = MessageBuilder
                                            .withPayload(Events.ADD_TASK).build();
                                    stateMachine
                                            .sendEvent(
                                                    Mono.just(addTaskEvent))
                                            .doOnComplete(() -> {
                                                stateMachine
                                                        .sendEvent(Mono.just(MessageBuilder
                                                                .withPayload(Events.PLAN_INTERVIEW).build()))
                                                        .doOnComplete(() -> {
                                                            stateMachine
                                                                    .sendEvent(Mono.just(MessageBuilder
                                                                            .withPayload(Events.FINISH_INTERVIEW).build())
                                                                    ).subscribe();

                                                        }).subscribe();
                                            }).subscribe();
                                }).subscribe()
                ).subscribe();
    }
}
