package io.voteofconf.tracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

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

    private Company company;
    private String title;

    @Transient
    Set<User> users;
}
