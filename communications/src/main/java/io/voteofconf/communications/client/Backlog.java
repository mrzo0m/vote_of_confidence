package io.voteofconf.communications.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Backlog {

    private BacklogKey key;

    
    private String inviteUuid;

    
    private String inviteFirstName;

    
    private String inviteLastName;

    
    private String inviteName;

    
    private String inviteEmail;

    
    private String inviteTimezone;

    
    private String inviteCreatedAt;

    
    private boolean isInviteReschedule;

    
    private boolean inviteCanceled;

    
    private String inviteCancelerName;

    
    private String inviteCancelReason;

    
    private String inviteCanceledAt;

    
    private String eventUuid;

    
    private String startTime;
    
    private String startTimePretty;
    
    private String inviteeStartTime;
    
    private String inviteeStartTimePretty;
    
    private String endTime;
    
    private String endTimePretty;
    
    private String inviteeEndTime;
    
    private String inviteeEndTimePretty;
    
    private String eventCreatedAt;
    
    private String eventCancelerName;
    
    private String eventCancelReason;
    
    private String eventCanceledAt;
}
