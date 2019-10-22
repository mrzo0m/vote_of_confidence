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
public class Payment {
    private String id;
    private String provider;
    private float amount;
    private String currency;
    private String terms;
    private boolean successful;
}
