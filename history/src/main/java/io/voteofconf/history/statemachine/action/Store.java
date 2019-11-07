package io.voteofconf.history.statemachine.action;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.dao.CalendlyInvitee;
import io.voteofconf.history.dao.CalendlyInviteeKey;
import io.voteofconf.history.dao.WebhookRepository;
import io.voteofconf.history.statemachine.Events;
import io.voteofconf.history.statemachine.States;
import io.voteofconf.history.statemachine.Variables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Component
public class Store implements Action<States, Events> {

    @Autowired
    WebhookRepository repository;

    @Autowired
    ObjectMapper mapper;

    private static final String CALENDLY_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DAY = "dd";
    private static final String RU = "ru";

    private static Integer getBucket() {
        int min = 1; //TODO: Кол-во узлов в кластере кассандры поулчать из конфигмапы куба
        int max = 1;
        return new Random().ints(min, (max + 1)).limit(1).findFirst().getAsInt();
    }

    public void execute(StateContext<States, Events> stateContext) {
        WebhookSubscription invitee = (WebhookSubscription) stateContext.getMessageHeaders().get(Variables.INVITE.toString());
        log.warn("Бросаем в кассандру {}", Objects.requireNonNull(invitee).getEvent());
        CalendlyInviteeKey key = new CalendlyInviteeKey();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(CALENDLY_DATETIME_PATTERN, Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(DAY, new Locale(RU));
        LocalDate date = LocalDate.parse(invitee.getTime(), inputFormatter);
        String formattedDate = outputFormatter.format(date);
        key.setInviteeEmail(invitee.getPayload().getInvitee().getEmail());
        key.setDay(formattedDate);
        Integer bucket = getBucket();
        key.setBucket(bucket);
        key.setTs(UUIDs.timeBased());
        CalendlyInvitee calendlyInvitee = new CalendlyInvitee();
        calendlyInvitee.setCalendlyInviteeKey(key);
        calendlyInvitee.setEvent(invitee.getEvent());
        try {
            calendlyInvitee.setPayload(Objects.requireNonNull(mapper).writeValueAsString(invitee.getPayload()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(repository).save(calendlyInvitee).subscribe();
    }
}
