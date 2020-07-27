package dev.sandrocaseiro.apitemplate.steps;

import dev.sandrocaseiro.apitemplate.security.TokenAuthResponse;
import dev.sandrocaseiro.apitemplate.security.TokenUser;
import dev.sandrocaseiro.apitemplate.services.JwtAuthService;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthenticationSteps extends BaseSteps implements En {
    @Autowired
    public JwtAuthService jwtAuthService;

    public AuthenticationSteps() {
        Given("I am authenticated", () -> {
            TokenUser user = (TokenUser)jwtAuthService.loadUserByUsername("user1@mail.com");
            TokenAuthResponse tokenResp = jwtAuthService.generateTokenResponse(user);

            state.setToken(tokenResp.getAccessToken());
        });

        Given("I am authenticated with {string}", (String username) -> {
            TokenUser user = (TokenUser)jwtAuthService.loadUserByUsername(username);
            TokenAuthResponse tokenResp = jwtAuthService.generateTokenResponse(user);

            state.setToken(tokenResp.getAccessToken());
        });
    }
}
