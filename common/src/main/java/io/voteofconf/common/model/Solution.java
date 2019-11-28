package io.voteofconf.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("application_solution")
public class Solution extends Entity {

    public enum ResolutionType {

        SUCCESS(1), FAIL(2);

        private final int id;

        ResolutionType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Solution.ResolutionType valueOf(Integer val) {
            if (val == null) return null;

            for (Solution.ResolutionType resolutionType : values()) {
                if (val.equals(resolutionType.id)) return resolutionType;
            }

            return null;
        }
    }

    @Id
    private Long id;

    @Column("resolution_id")
    private ResolutionType resolutionType;

    @JsonIgnore
    private Long interviewApplicationId;
    @Transient
    private Interview interview;

    @JsonIgnore
    private Long reportId;
    @Transient
    private Report report;

    @JsonIgnore
    private Long certificateId;
    @Transient
    private Certificate certificate;


    public Solution(Long id, ResolutionType resolutionType, Long interviewApplicationId, Long reportId, Long certificateId) {
        this.id = id;
        this.resolutionType = resolutionType;
        this.interviewApplicationId = interviewApplicationId;
        this.reportId = reportId;
        this.certificateId = certificateId;
    }
}
