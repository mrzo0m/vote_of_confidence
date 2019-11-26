package io.voteofconf.common.model;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("interview_application")
public class Application extends Entity {

    @Id
    private Long id;

    @Column("candidate_id")
    private User candidate;

    @Column("expert_id")
    private User expert;

    @Column("discipline_id")
    private Expertise expertise;

    @Column("date_time")
    private DateTime dateOfCreation;

    private DateTime dateOfInterview;

    @NonNull
    private String calendlyLink;
}
