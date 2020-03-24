package com.sandrocaseiro.apitemplate.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sandrocaseiro.apitemplate.states.TestState;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class CommonSteps {
    @Autowired
    public TestState state;

    @When("With content-type: {string}")
    public void with_content_type(String contentType) {
        RequestSpecification request = given()
            .contentType(contentType);
        state.setRequest(request);
    }

    @Then("The client receives status code of {int}")
    public void the_client_receives_status_code_of(int statusCode) {
        state.getResponse()
            .then()
            .statusCode(statusCode);
    }

    @Then("With payload:")
    public void with_payload(String payload) throws JsonProcessingException {
        RequestSpecification request = state.getRequest()
            .body(payload);

        state.setRequestPayload(payload);
        state.setRequest(request);
    }

    @Then("The response returns error code {int}")
    public void response_error_code(int errorCode) {
        state.getResponse().then()
            .body("errors.code", hasItem(errorCode));
    }

    @Then("The response has {int} errors")
    public void response_has_error_qty(int qty) {
        state.getResponse().then()
            .body("errors.size()", is(qty));
    }

    @Then("The response contains error for the {word} field")
    public void response_error_field(String field) {
        state.getResponse().then()
            .body("errors.description", hasItem(containsString(field)));
    }
}
