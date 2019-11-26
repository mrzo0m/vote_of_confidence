package io.voteofconf.common.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("vacancy")
public class Vacancy extends Entity {

    @Id
    private Long id;

    private String title;

    @Transient
    Set<User> users = new HashSet<>();
}
