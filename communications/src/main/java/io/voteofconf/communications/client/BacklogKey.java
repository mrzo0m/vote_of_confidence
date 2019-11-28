package io.voteofconf.communications.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BacklogKey {

    private String inviteYear;

    private String inviteMonth;

    private String inviteDay;

    private UUID id;
}
