package io.voteofconf.history.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.voteofconf.history.dao.calendly.Payload;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhookSubscription {
    private String event;
    private String time;
    private Payload payload;
}
