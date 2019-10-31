package io.voteofconf.frontend.config;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class OktaApiToken {

    @NonNull
    private String type;

    @NonNull
    private String value;
}
