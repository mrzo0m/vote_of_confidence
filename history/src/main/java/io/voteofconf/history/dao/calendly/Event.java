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
public class Event {
    private String uuid;
    @JsonProperty("assigned_to")
    private List<String> assignedTo;
    @JsonProperty("extended_assigned_to")
    private List<ExtendedAssignedTo> extendedAssignedTo;
    @JsonProperty("start_time")
    private String startTime;
    @JsonProperty("start_time_pretty")
    private String startTimePretty;
    @JsonProperty("invitee_start_time")
    private String inviteeStartTime;
    @JsonProperty("invitee_start_time_pretty")
    private String inviteeStartTimePretty;
    @JsonProperty("end_time")
    private String endTime;
    @JsonProperty("end_time_pretty")
    private String endTimePretty;
    @JsonProperty("invitee_end_time")
    private String inviteeEndTime;
    @JsonProperty("invitee_end_time_pretty")
    private String inviteeEndTimePretty;
    @JsonProperty("created_at")
    private String createdAt;
    private String location;
    private boolean canceled;
    @JsonProperty("canceler_name")
    private String cancelerName;
    @JsonProperty("cancel_reason")
    private String cancelReason;
    @JsonProperty("canceled_at")
    private String canceledAt;

}
