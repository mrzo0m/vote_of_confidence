package io.voteofconf.common.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("report")
public class Report extends Entity {
    @Id
    private Long id;

    private String name;
    private String body;
}
