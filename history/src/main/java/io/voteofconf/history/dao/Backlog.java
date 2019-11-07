package io.voteofconf.history.dao;

import io.voteofconf.history.dao.calendly.Invitee;
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
//
//    @Column
//    private Invitee invitee;

}
