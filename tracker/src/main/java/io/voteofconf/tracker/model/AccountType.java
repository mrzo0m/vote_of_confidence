package io.voteofconf.tracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("account_types")
public class AccountType extends Entity {

    @Id
    private Long id;

    private String name;
    private String description;
    private Integer period;
    private BigDecimal cost;
}
