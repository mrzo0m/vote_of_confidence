package io.voteofconf.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@ToString
@Table("interview_application")
public class Interview extends Entity {

    @Id
    private Long id;

    @Column("candidate_id")
    @JsonIgnore
    private Long candidateId;
    @Transient
    private User candidate;

    @Column("expert_id")
    @JsonIgnore
    private Long expertId;
    @Transient
    private User expert;

    @Column("discipline_id")
    @JsonIgnore
    private Long disciplineId;
    @Transient
    private Expertise discipline;

    @Column("date_time")
    private DateTime dateOfCreation;

    private DateTime dateOfInterview;

    @NonNull
    private String calendlyLink;

    public Interview(Long id, Long candidateId, Long expertId, Long disciplineId, DateTime dateOfCreation, DateTime dateOfInterview, String calendlyLink) {
        this.id = id;
        this.candidateId = candidateId;
        this.expertId = expertId;
        this.disciplineId = disciplineId;
        this.dateOfCreation = dateOfCreation;
        this.dateOfInterview = dateOfInterview;
        this.calendlyLink = calendlyLink;
    }
}
