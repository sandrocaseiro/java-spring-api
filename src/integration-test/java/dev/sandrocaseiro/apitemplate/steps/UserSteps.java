package dev.sandrocaseiro.apitemplate.steps;

import com.fasterxml.jackson.databind.JsonNode;
import dev.sandrocaseiro.apitemplate.models.domain.ERole;
import dev.sandrocaseiro.apitemplate.models.domain.EUser;
import dev.sandrocaseiro.apitemplate.repositories.UserRepository;
import io.cucumber.java8.En;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UserSteps extends BaseSteps implements En {
    @Autowired
    public UserRepository userRepository;

    public UserSteps() {
        Given("I inactivate the user {int}", (Integer userId) -> {
            EUser user = userRepository.findById(userId).orElse(null);
            user.setActive(false);
            userRepository.save(user);
        });

        Then("The response should have the created user info", () -> {
            JsonNode reqUser = state.getRequestPayload();

            List<Integer> roles = new ArrayList<>();
            reqUser.withArray("roles").elements().forEachRemaining(i -> roles.add(i.asInt()));

            state.getResponse().then()
                .body("data.name", equalTo(reqUser.get("name").asText()))
                .body("data.email", equalTo(reqUser.get("email").asText()))
                .body("data.groupId", equalTo(reqUser.get("groupId").asInt()))
                .body("data.roles.size()", is(2))
                .body("data.roles", equalTo(roles));
        });

        Then("The created user should exist in the database as active", () -> {
            int id = state.getResponse().getBody().jsonPath().getInt("data.id");
            EUser user = userRepository.findById(id).orElse(null);
            JsonNode payload = state.getRequestPayload();
            List<Integer> roles = new ArrayList<>();
            payload.withArray("roles").elements().forEachRemaining(i -> roles.add(i.asInt()));

            assertThat(user).isNotNull();
            assertThat(user.isActive()).isTrue();
            assertThat(user.getRoles()).hasSize(2);

            assertThat(payload.get("name").asText()).isEqualTo(user.getName());
            assertThat(payload.get("email").asText()).isEqualTo(user.getEmail());
            assertThat(payload.get("cpf").asText()).isEqualTo(user.getCpf());
            assertThat(payload.get("password").asText()).isEqualTo(user.getPassword());
            assertThat(payload.get("groupId").asInt()).isEqualTo(user.getGroupId());
            assertThat(roles).containsExactlyInAnyOrderElementsOf(user.getRoles().stream().map(ERole::getId).collect(Collectors.toList()));
        });

        Then("The user {int} should have his data updated in the database", (Integer userId) -> {
            EUser user = userRepository.findById(userId).orElse(null);
            JsonNode payload = state.getRequestPayload();
            List<Integer> roles = new ArrayList<>();
            payload.withArray("roles").elements().forEachRemaining(i -> roles.add(i.asInt()));

            assertThat(user).isNotNull();
            assertThat(user.getRoles()).hasSize(2);

            assertThat(payload.get("name").asText()).isEqualTo(user.getName());
            assertThat(payload.get("cpf").asText()).isEqualTo(user.getCpf());
            assertThat(payload.get("password").asText()).isEqualTo(user.getPassword());
            assertThat(payload.get("groupId").asInt()).isEqualTo(user.getGroupId());
            assertThat(roles).containsExactlyInAnyOrderElementsOf(user.getRoles().stream().map(ERole::getId).collect(Collectors.toList()));
        });

        Then("The user {int} should have his balance updated in the database", (Integer userId) -> {
            EUser user = userRepository.findById(userId).orElse(null);
            JsonNode payload = state.getRequestPayload();

            assertThat(user).isNotNull();

            assertThat(new BigDecimal(payload.get("balance").asText())).isEqualTo(user.getBalance());
        });

        Then("The user {int} should be inactive", (Integer userId) -> {
            EUser user = userRepository.findById(userId).orElse(null);

            assertThat(user).isNotNull();

            assertThat(user.isActive()).isFalse();
        });
    }
}
