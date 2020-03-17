package com.sandrocaseiro.apitemplate.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class TokenAuthResponse {
    private String tokenType;
    private long expiresIn;
    private long refreshExpiresIn;
    private String accessToken;
    private String refreshToken;
}
