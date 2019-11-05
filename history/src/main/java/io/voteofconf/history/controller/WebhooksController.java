package io.voteofconf.history.controller;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.dao.CalendlyInvitee;
import io.voteofconf.history.dao.CalendlyInviteeKey;
import io.voteofconf.history.dao.WebhookRepository;
import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

@Log
@RestController
@RequestMapping("webhooks")
public class WebhooksController {

    @Autowired
    WebhookRepository repository;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;



    @ApiOperation(value = "Post to invitee_created")
    @PostMapping("/invitee_created")
    public Mono<CalendlyInvitee> inviteeCreated(@RequestBody WebhookSubscription invitee) throws JsonProcessingException {
        log.info("webhooks event while invitee_created");
        CalendlyInviteeKey key = new CalendlyInviteeKey();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd", new Locale("ru"));
        LocalDate date = LocalDate.parse(invitee.getTime(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        key.setInviteeEmail(invitee.getPayload().getInvitee().getEmail());
        key.setDay(formattedDate);
        int min = 1; //TODO: Кол-во узлов в кластере кассандры поулчать из конфигмапы куба
        int max = 1;
        Integer bucket = new Random().ints(min, (max + 1)).limit(1).findFirst().getAsInt();
        key.setBucket(bucket);
        key.setTs(UUIDs.timeBased());
        CalendlyInvitee calendlyInvitee = new CalendlyInvitee();
        calendlyInvitee.setCalendlyInviteeKey(key);
        calendlyInvitee.setEvent(invitee.getEvent());
        calendlyInvitee.setPayload(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invitee.getPayload()));
        return repository.save(calendlyInvitee);
    }

    @ApiOperation(value = "Post to invitee_canceled")
    @PostMapping("/invitee_canceled")
    public Mono<Void> inviteeCanceled(@RequestBody WebhookSubscription invitee) {
        log.info("webhooks event while invitee_canceled");
        return Mono.empty();
    }
}
