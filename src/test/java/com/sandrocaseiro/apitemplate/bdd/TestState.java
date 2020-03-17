package com.sandrocaseiro.apitemplate.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandrocaseiro.apitemplate.models.domain.EUser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class TestState {
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";
    private static final String TOKEN = "token";
    private static final String REQUEST_PAYLOAD = "request_payload";
    private static final String USER = "user";

    private Map<String, Object> states = new HashMap<>();

    public <T> T get(String name) {
        return (T) states.getOrDefault(name, null);
    }

    public <T> T set(String name, T object) {
        states.put(name, object);

        return object;
    }

    public RequestSpecification getRequest() {
        return get(REQUEST);
    }

    public RequestSpecification setRequest(RequestSpecification request) {
        String token = getToken();
        if (!StringUtils.isBlank(token))
            request.header("Authorization", "Bearer " + token);

        return set(REQUEST, request);
    }

    public Response getResponse() {
        return get(RESPONSE);
    }

    public Response setResponse(Response response) {
        return set(RESPONSE, response);
    }

    public String getToken() {
        return get(TOKEN);
    }

    public String setToken(String token) {
        return set(TOKEN, token);
    }

    public JsonNode getRequestPayload() {
        return get(REQUEST_PAYLOAD);
    }

    public JsonNode setRequestPayload(String payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return set(REQUEST_PAYLOAD, mapper.readTree(payload));
    }

    public EUser getUser() {
        return get(USER);
    }

    public EUser setUser(EUser user) {
        return set(USER, user);
    }
}
