package dev.sandrocaseiro.template.models.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ATokenResp {
    @JsonProperty("type")
    private String tokenType;
    @JsonProperty("expires_in")
    private long expiresIn;
    @JsonProperty("refresh_expires_in")
    private long refreshExpiresIn;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
