package io.voteofconf.history.dao.calendly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @see https://github.com/coffeechris/calendly-api.git
 */

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
public class Tracking {
    @JsonProperty("utm_campaign")
    private String utmCampaign;
    @JsonProperty("utm_source")
    private String utmSource;
    @JsonProperty("utm_medium")
    private String utmMedium;
    @JsonProperty("utm_content")
    private String utmContent;
    @JsonProperty("utm_term")
    private String utmTerm;
    @JsonProperty("salesforce_uuid")
    private String salesforceUuid;
}
