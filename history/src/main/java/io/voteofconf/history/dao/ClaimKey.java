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
public class ClaimKey implements Serializable {

    @PrimaryKeyColumn(name = "company_name", type = PARTITIONED)
    private String companyName;

    @PrimaryKeyColumn(name = "claim_id", ordinal = 1, ordering = DESCENDING)
    private UUID id;
}
