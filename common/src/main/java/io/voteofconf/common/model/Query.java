package io.voteofconf.common.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("queries")
public class Query {
    private String name;
    private String source;
}
