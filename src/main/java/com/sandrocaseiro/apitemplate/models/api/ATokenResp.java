package com.sandrocaseiro.apitemplate.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ATokenResp {
    private String tokenType;
    private long expiresIn;
    private long refreshExpiresIn;
    private String accessToken;
    private String refreshToken;
}
