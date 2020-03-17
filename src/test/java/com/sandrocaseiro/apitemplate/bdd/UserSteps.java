package com.sandrocaseiro.apitemplate.bdd;

import com.fasterxml.jackson.databind.JsonNode;
import com.sandrocaseiro.apitemplate.models.domain.ERole;
import com.sandrocaseiro.apitemplate.models.domain.EUser;
import com.sandrocaseiro.apitemplate.repositories.UserRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UserSteps {
    @LocalServerPort
    public int port;

    @Autowired
    public TestState state;
    @Autowired
    public UserRepository userRepository;

    @Before
    public void before() {
        RestAssured.config = config()
            .encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))
            .logConfig(logConfig().enableLoggingOfRequestAndResponseIfValidationFails().and().enablePrettyPrinting(true));
        RestAssured.port = port;
    }

    @After
    public void after() {
        EUser user = state.getUser();
        if (user == null)
            return;

        userRepository.deleteById(user.getId());
    }

    @When("The client calls get by id endpoint using id: {int}")
    public void client_calls_user_by_id(int id) {
        Response response = state.getRequest()
            .when()
            .get("/v1/users/{id}", id);

        state.setResponse(response);
    }

    @When("The client calls user create endpoint")
    public void create_endpoint_call() {
        Response response = state.getRequest()
            .when()
            .post("/v1/users");

        state.setResponse(response);
    }

    @Then("The returned user has the following data:")
    public void returned_user_data(DataTable table) {
        Map<String, String> map = table.asMaps().get(0);
        state.getResponse()
            .then()
            .body("data.id", equalTo(Integer.parseInt(map.get("id"))))
            .body("data.name", equalTo(map.get("name")))
            .body("data.email", equalTo(map.get("email")));
    }

    @Then("The response has the created user info")
    public void response_has_user_info() {
        JsonNode reqUser = state.getRequestPayload();

        List<Integer> roles = new ArrayList<>();
        reqUser.withArray("roles").elements().forEachRemaining(i -> roles.add(i.asInt()));

        state.getResponse().then()
            .body("data.name", equalTo(reqUser.get("name").asText()))
            .body("data.email", equalTo(reqUser.get("email").asText()))
            .body("data.groupId", equalTo(reqUser.get("groupId").asInt()))
            .body("data.roles.size()", is(2))
            .body("data.roles", equalTo(roles))
        ;
    }

    @Then("The user is created in the database as active")
    @Transactional
    public void user_created_in_database() {
        int id = state.getResponse().getBody().jsonPath().getInt("data.id");
        EUser user = userRepository.findById(id).orElse(null);
        JsonNode payload = state.getRequestPayload();
        List<Integer> roles = new ArrayList<>();
        payload.withArray("roles").elements().forEachRemaining(i -> roles.add(i.asInt()));

        state.setUser(user);

        assertThat(user).isNotNull();
        assertThat(user.isActive()).isTrue();
        assertThat(user.getRoles()).hasSize(2);

        assertThat(payload.get("name").asText()).isEqualTo(user.getName());
        assertThat(payload.get("email").asText()).isEqualTo(user.getEmail());
        assertThat(payload.get("groupId").asInt()).isEqualTo(user.getGroupId());
        assertThat(roles).containsExactlyInAnyOrderElementsOf(user.getRoles().stream().map(ERole::getId).collect(Collectors.toList()));
    }
}
