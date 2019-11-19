package io.voteofconf.tracker.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("expertise")
public class Expertise extends Entity {

    public enum ExpertiseLevel {
        LEVEL_0, LEVEL_1, LEVEL_2, LEVEL_3;
    }

    @Id
    private Long id;

    private String name;
    private Set<String> keywords;
    private String description;
    private ExpertiseLevel level;
}
