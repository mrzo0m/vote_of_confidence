package io.voteofconf.common.model;

import lombok.*;
import org.springframework.data.annotation.Id;
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

    private Long reportId;
    private Long certificateId;
}
