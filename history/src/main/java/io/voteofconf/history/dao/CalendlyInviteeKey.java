package io.voteofconf.history.dao;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;


@PrimaryKeyClass
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class CalendlyInviteeKey implements Serializable {

    @PrimaryKeyColumn(name = "invitee_email", type = PARTITIONED)
    private String inviteeEmail;

    /*
      TODO:
        COMPACTION = {'class': 'TimeWindowCompactionStrategy',
                             'compaction_window_unit': 'DAYS',
                             'compaction_window_size': 1};
       */
    @PrimaryKeyColumn(name = "day", type = PARTITIONED)
    private String day;

    @PrimaryKeyColumn(name = "bucket", type = PARTITIONED)
    private Integer bucket;

    @PrimaryKeyColumn(name = "ts", type = CLUSTERED, ordering = DESCENDING)
    private UUID ts;
}
