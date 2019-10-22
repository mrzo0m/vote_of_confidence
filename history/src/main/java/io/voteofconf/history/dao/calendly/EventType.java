package io.voteofconf.history.dao.calendly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @see https://github.com/coffeechris/calendly-api.git
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class EventType {
    @JsonProperty("event_type")
    private EventType eventType;
    private Event event;
    private Invitee invitee;
    @JsonProperty("questions_and_answers")
    private List<QuestionAnswer> questionsAndAnswers;
    @JsonProperty("questions_and_responses")
    private QuestionResponses questionResponses;
    private Tracking tracking;
    @JsonProperty("old_event")
    private Event oldEvent;
    @JsonProperty("old_invitee")
    private Invitee oldInvitee;
    @JsonProperty("new_event")
    private Event newEvent;
    @JsonProperty("new_invitee")
    private Invitee newInvitee;

}
