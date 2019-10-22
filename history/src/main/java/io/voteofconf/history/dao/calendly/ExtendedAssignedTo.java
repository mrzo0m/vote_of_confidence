package io.voteofconf.history.dao.calendly;

import lombok.*;

/**
 * @see https://github.com/coffeechris/calendly-api.git
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class ExtendedAssignedTo {
    private String name;
    private String email;
    private boolean primary;
}
