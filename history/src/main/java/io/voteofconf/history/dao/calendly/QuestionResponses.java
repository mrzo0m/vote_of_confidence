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
public class QuestionResponses {
    @JsonProperty("1_question")
    private String question1;
    @JsonProperty("1_response")
    private String response1;
    @JsonProperty("2_question")
    private String question2;
    @JsonProperty("2_response")
    private String response2;
    @JsonProperty("3_question")
    private String question3;
    @JsonProperty("3_response")
    private String response3;
    @JsonProperty("4_question")
    private String question4;
    @JsonProperty("4_response")
    private String response4;
}
