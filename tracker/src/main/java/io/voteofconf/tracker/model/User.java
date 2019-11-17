package io.voteofconf.tracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("user")
public class User extends Entity {

    public enum ClientType {

        CANDIDATE(1), COMPANY(2), EXPERT(3);

        private final int id;

        ClientType(int id) {
            this.id = id;
        }
    }

    @Id
    private Long id;

    private ClientType clientType;

    // TODO write question on StackOverflow how to change default column name prefix at MappingR2dbcConverter:204
//    @Column("account_type")
    private AccountType accountType;

    private String firstName;
    private String secondName;
    private String surName;
    private String emailAddr;
}

