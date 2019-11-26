package io.voteofconf.history.dao.calendly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;

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

    @Column("uuid")
    private String uuid;

    @Column("first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Column("last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Column("name")
    private String name;

    @Column("email")
    private String email;

    @Column("timezone")
    private String timezone;

    @Column("created_at")
    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("is_reschedule")
    @Column("is_reschedule")
    private boolean isReschedule;

    private List<Payment> payments;

    @Column("canceled")
    private boolean canceled;

    @Column("canceler_name")
    @JsonProperty("canceler_name")
    private String cancelerName;

    @Column("cancel_reason")
    @JsonProperty("cancel_reason")
    private String cancelReason;

    @Column("canceled_at")
    @JsonProperty("canceled_at")
    private String canceledAt;
}
