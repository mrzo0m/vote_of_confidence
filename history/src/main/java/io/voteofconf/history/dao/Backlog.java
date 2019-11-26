package io.voteofconf.history.dao;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("backlog")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class Backlog {

    @PrimaryKey
    private BacklogKey key;

    @Column("invite_uuid")
    private String inviteUuid;

    @Column("invite_first_name")
    private String inviteFirstName;

    @Column("invite_last_name")
    private String inviteLastName;

    @Column("invite_name")
    private String inviteName;

    @Column("invite_email")
    private String inviteEmail;

    @Column("invite_timezone")
    private String inviteTimezone;

    @Column("invite_created_at")
    private String inviteCreatedAt;

    @Column("invite_is_reschedule")
    private boolean isInviteReschedule;

    @Column("invite_canceled")
    private boolean inviteCanceled;

    @Column("invite_canceler_name")
    private String inviteCancelerName;

    @Column("invite_cancel_reason")
    private String inviteCancelReason;

    @Column("invite_canceled_at")
    private String inviteCanceledAt;

    @Column("event_uuid")
    private String eventUuid;

    @Column("event_start_time")
    private String startTime;
    @Column("event_start_time_pretty")
    private String startTimePretty;
    @Column("event_invitee_start_time")
    private String inviteeStartTime;
    @Column("event_invitee_start_time_pretty")
    private String inviteeStartTimePretty;
    @Column("event_end_time")
    private String endTime;
    @Column("event_end_time_pretty")
    private String endTimePretty;
    @Column("event_invitee_end_time")
    private String inviteeEndTime;
    @Column("event_invitee_end_time_pretty")
    private String inviteeEndTimePretty;
    @Column("event_created_at")
    private String eventCreatedAt;
    @Column("event_canceler_name")
    private String eventCancelerName;
    @Column("event_cancel_reason")
    private String eventCancelReason;
    @Column("event_canceled_at")
    private String eventCanceledAt;

}
