package io.voteofconf.history.statemachine.action;

import com.datastax.driver.core.utils.UUIDs;
import io.voteofconf.history.controller.dto.WebhookSubscription;
import io.voteofconf.history.dao.Backlog;
import io.voteofconf.history.dao.BacklogKey;
import io.voteofconf.history.dao.BacklogRebository;
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

import static io.voteofconf.history.utils.Constants.*;

@Slf4j
@Component
public class AddTask implements Action<States, Events> {

    @Autowired
    BacklogRebository backlogRebository;

    @Override
    public void execute(StateContext<States, Events> context) {
        log.warn("Кладем в общий пул задач экспертов (можно инмеори)");
        WebhookSubscription invitee = (WebhookSubscription) context.getMessageHeaders().get(Variables.INVITE.toString());
        BacklogKey key = new BacklogKey();
        key.setId(UUIDs.timeBased());
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(CALENDLY_DATETIME_PATTERN, Locale.ENGLISH);
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(DAY, new Locale(RU));
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern(MONTH, new Locale(RU));
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern(YEAR, new Locale(RU));
        LocalDate date = LocalDate.parse(Objects.requireNonNull(invitee).getPayload().getEvent().getStartTime(), inputFormatter);
        key.setInviteDay(dayFormatter.format(date));
        key.setInviteMonth(monthFormatter.format(date));
        key.setInviteYear(yearFormatter.format(date));
        Backlog backlog = new Backlog();
        backlog.setKey(key);
        backlog.setEventUuid(invitee.getPayload().getEvent().getUuid());
        backlog.setEventCreatedAt(invitee.getPayload().getEvent().getCreatedAt());
        backlog.setEventCanceledAt(invitee.getPayload().getEvent().getCanceledAt());
        backlog.setEventCancelerName(invitee.getPayload().getEvent().getCancelerName());
        backlog.setEventCancelReason(invitee.getPayload().getEvent().getCancelReason());
        backlog.setStartTime(invitee.getPayload().getEvent().getStartTime());
        backlog.setStartTimePretty(invitee.getPayload().getEvent().getStartTimePretty());
        backlog.setEndTime(invitee.getPayload().getEvent().getEndTime());
        backlog.setEndTimePretty(invitee.getPayload().getEvent().getEndTimePretty());
        backlog.setInviteUuid(invitee.getPayload().getInvitee().getUuid());
        backlog.setInviteEmail(invitee.getPayload().getInvitee().getEmail());
        backlog.setInviteName(invitee.getPayload().getInvitee().getName());
        backlog.setInviteTimezone(invitee.getPayload().getInvitee().getTimezone());
        backlogRebository.save(backlog).subscribe();
    }

}
