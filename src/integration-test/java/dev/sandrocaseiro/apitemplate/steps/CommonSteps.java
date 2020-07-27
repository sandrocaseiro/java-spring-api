package dev.sandrocaseiro.apitemplate.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.Map;

import static dev.sandrocaseiro.apitemplate.matchers.HasKeyPath.hasKeyAtPath;
import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.Matchers.*;

public class CommonSteps extends BaseSteps implements En {
    public CommonSteps() {
        Before(this::setupRestAssured);

        After(this::rebuildDbData);

        When("I use the header {string} with the value {string}", (String headerName, String headerValue) -> {
            state.getRequest()
                .header(new Header(headerName, headerValue));
        });

        When("I use form-urlencoded", () -> requestContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        When("I use the Content-Type {string}", this::requestContentType);

        When("I DELETE to {string}", (String url) -> {
            Response response = state.getRequest()
                .when()
                .delete(url);

            state.setResponse(response);
        });

        When("I GET to {string}", (String url) -> {
            Response response = state.getRequest()
                .when()
                .get(url);

            state.setResponse(response);
        });

        When("I PATCH to {string}", (String url) -> {
            Response response = state.getRequest()
                .when()
                .patch(url);

            state.setResponse(response);
        });

        When("I POST to {string}", (String url) -> {
            Response response = state.getRequest()
                .when()
                .post(url);

            state.setResponse(response);
        });

        When("I PUT to {string}", (String url) -> {
            Response response = state.getRequest()
                .when()
                .put(url);

            state.setResponse(response);
        });

        When("I use the payload:", (String payload) -> {
            state.getRequest()
                .body(payload);

            state.setRequestPayload(payload);
        });

        When("I use the formparams:", (DataTable table) -> {
            for (Map.Entry<String, String> param : table.<String, String>asMap(String.class, String.class).entrySet()) {
                if (param.getValue().equalsIgnoreCase("null"))
                    state.getRequest()
                        .formParam(param.getKey(), (String)null);
                else
                    state.getRequest()
                        .formParam(param.getKey(), param.getValue());
            }
        });

        When("I use the queryparams:", (DataTable table) -> {
           for (Map.Entry<String, String> param : table.<String, String>asMap(String.class, String.class).entrySet()) {
               state.getRequest()
                   .queryParam(param.getKey(), param.getValue());
           }
        });

        Then("I should receive the status code {int}", (Integer statusCode) -> {
            state.getResponse()
                .then()
                .statusCode(statusCode);
        });

        Then("The response should have a {string} property", (String property) -> {
            state.getResponse().then()
                .body("$", hasKeyAtPath(property));
        });

        Then("The response should not have a {string} property", (String property) -> {
            state.getResponse().then()
                .body("$", not(hasKeyAtPath(property)));
        });

        Then("The response should have a {string} property with the value {string}", (String property, String value) -> {
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))
                state.getResponse().then()
                    .body(property, is(Boolean.parseBoolean(value)));
            else
                state.getResponse().then()
                    .body(property, is(value));
        });

        Then("The response should have a {string} property containing {string}", (String property, String term) -> {
            state.getResponse().then()
                .body(property, containsString(term));
        });

        Then("The response should have a {string} property with the value {int}", (String property, Integer value) -> {
            state.getResponse().then()
                .body(property, is(value));
        });

        Then("The response data should be an empty list", () -> {
            state.getResponse().then()
                .body("data.size()", is(0));
        });

        Then("The response data should have {int} items", (Integer size) -> {
            state.getResponse().then()
                .body("data.size()", is(size));
        });

        Then("The response contains error code {int}", (Integer errorCode) -> {
            state.getResponse().then()
                .body("errors.code", hasItem(errorCode));
        });

        Then("The response has {int} errors", (Integer qty) -> {
            state.getResponse().then()
                .body("errors.size()", is(qty));
        });

        Then("The response has error containing {string}", (String field) -> {
            state.getResponse().then()
                .body("errors.description", hasItem(containsString(field)));
        });

        Then("The response has {int} errors with code {int}", (Integer qty, Integer code) -> {
            state.getResponse().then()
                .rootPath("errors.findAll { it.code == %s }", withArgs(code))
                .body("size()", is(qty));
        });

        Then("The response has {int} errors with code {int} containing {string}", (Integer qty, Integer code, String term) -> {
            state.getResponse().then()
                .rootPath("errors.findAll { it.code == %s && it.description.contains('%s') }", withArgs(code, term))
                .body("size()", is(qty));
        });

        Then("The response has an error with code {int} containing {string}", (Integer code, String term) -> {
            state.getResponse().then()
                .rootPath("errors.findAll { it.code == %s && it.description.contains('%s') }", withArgs(code, term))
                .body("size()", greaterThan(0));
        });

        Then("The response data {word} property should all contains {string}", (String field, String term) -> {
            state.getResponse().then()
                .rootPath("data.findAll { !it.%s.toLowerCase().contains('%s') }", withArgs(field, term))
                .body("size()", equalTo(0));
        });

        Then("The response data {string} property should have {int} items", (String field, Integer size) -> {
            state.getResponse().then()
                .rootPath(field)
                .body("size()", is(size));
        });
    }

    public void requestContentType(String contentType) {
        state.getRequest()
            .contentType(contentType);
    }
}
