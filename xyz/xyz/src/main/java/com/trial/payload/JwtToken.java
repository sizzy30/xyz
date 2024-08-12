package com.trial.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {
    private String type;
    private String token;
}
