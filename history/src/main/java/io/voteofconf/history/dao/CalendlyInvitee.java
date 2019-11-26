package io.voteofconf.history.dao;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("calendly_invitee_stream")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class CalendlyInvitee {

    @PrimaryKey
    private CalendlyInviteeKey calendlyInviteeKey;

    @Column("event")
    private String event;

    @Column("payload")
    private String payload;

}
