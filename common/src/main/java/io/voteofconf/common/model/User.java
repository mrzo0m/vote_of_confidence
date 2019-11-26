package io.voteofconf.common.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;


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

        public int getId() {
            return id;
        }

        public static ClientType valueOf(Integer val) {
            if (val == null) return null;

            for (ClientType clientType : values()) {
                if (val.equals(clientType.id)) return clientType;
            }

            return null;
        }
    }

    @Id
    private Long id;

    @Column("client_type_id")
    private ClientType clientType;

    // TODO write question on StackOverflow how to change default column name prefix at MappingR2dbcConverter:204
//    @Column("account_type_id")
    @Transient
    private AccountType accountType;

    private String firstName;
    private String secondName;
    private String surName;
    private String emailAddr;

    @Transient
    private Boolean agreed;

    @Transient
    private Set<Expertise> expertises = new HashSet<>();
}

