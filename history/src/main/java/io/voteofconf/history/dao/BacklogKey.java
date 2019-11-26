package io.voteofconf.history.dao;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.UUID;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@PrimaryKeyClass
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class BacklogKey implements Serializable {

    @PrimaryKeyColumn(name = "invite_year", type = PARTITIONED)
    private String inviteYear;

   @PrimaryKeyColumn(name = "invite_month", type = PARTITIONED)
    private String inviteMonth;

   @PrimaryKeyColumn(name = "invite_day", type = PARTITIONED)
    private String inviteDay;

    @PrimaryKeyColumn(name = "backlog_id", ordinal = 1, ordering = DESCENDING)
    private UUID id;
}
