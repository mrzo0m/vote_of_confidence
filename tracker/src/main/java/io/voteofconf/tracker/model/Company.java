package io.voteofconf.tracker.model;

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
@Table("company")
public class Company extends Entity {
    @Id
    private Long id;

    private String name;
    private String description;

    @Transient
    private Set<User> users = new HashSet<>();

    @Transient
    Set<Vacancy> vacancies = new HashSet<>();
}
