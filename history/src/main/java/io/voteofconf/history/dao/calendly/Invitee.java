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
public class Invitee {
    private String uuid;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String name;
    private String email;
    private String timezone;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("is_reschedule")
    private boolean isReschedule;
    private List<Payment> payments;
    private boolean canceled;
    @JsonProperty("canceler_name")
    private String cancelerName;
    @JsonProperty("cancel_reason")
    private String cancelReason;
    @JsonProperty("canceled_at")
    private String canceledAt;
}
