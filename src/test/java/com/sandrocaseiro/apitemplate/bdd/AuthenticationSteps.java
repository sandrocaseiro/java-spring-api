package com.sandrocaseiro.apitemplate.bdd;

import com.sandrocaseiro.apitemplate.security.TokenAuthResponse;
import com.sandrocaseiro.apitemplate.security.TokenUser;
import com.sandrocaseiro.apitemplate.services.JwtAuthService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.hamcrest.Matchers.hasKey;

public class AuthenticationSteps {
    @LocalServerPort
    public int port;

    @Autowired
    public TestState state;
    @Autowired
    public JwtAuthService jwtAuthService;

    @Before
    public void before() {
        RestAssured.config = config()
            .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
            .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails().and().enablePrettyPrinting(true));
        RestAssured.port = port;
    }

    @Given("I am authenticated")
    public void i_am_authenticated() {
        TokenUser user = (TokenUser)jwtAuthService.loadUserByUsername("user1@mail.com");
        TokenAuthResponse tokenResp = jwtAuthService.generateTokenResponse(user);

        state.setToken(tokenResp.getAccessToken());
    }

    @When("The client calls \\/v1\\/token endpoint with username: {word} and password: {word}")
    public void the_client_calls_token_with_username_and_password(String username, String password) {
        Response response = state.getRequest()
            .body("username=" + username + "&password=" + password)
            .when()
                .post("/v1/token");

        state.setResponse(response);
    }

    @When("The client calls \\/v1\\/token endpoint")
    public void the_client_calls_token_endpoint() {
        Response response = state.getRequest()
            .when()
                .post("/v1/token");

        state.setResponse(response);
    }

    @When("The client calls \\/v1\\/token endpoint with the payload")
    public void the_client_calls_token_with_payload(String payload) {
        Response response = state.getRequest()
            .body(payload)
            .when()
                .post("/v1/token");

        state.setResponse(response);
    }

    @Then("Response returned bearer token")
    public void response_has_token() {
        state.getResponse().then()
            .body("$", hasKey("accessToken"));
    }
}
