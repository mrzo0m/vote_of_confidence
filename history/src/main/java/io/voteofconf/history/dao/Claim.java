package io.voteofconf.history.dao;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("claim_by_company")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class Claim {

    @PrimaryKey
    private ClaimKey key;

    @Column("claimInfo")
    private String claimInfo;
}
