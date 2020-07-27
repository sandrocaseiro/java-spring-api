package dev.sandrocaseiro.apitemplate.clients.interceptors;

import dev.sandrocaseiro.apitemplate.clients.AuthClient;
import dev.sandrocaseiro.apitemplate.models.api.ATokenReq;
import dev.sandrocaseiro.apitemplate.models.api.ATokenResp;
import dev.sandrocaseiro.apitemplate.properties.EndpointProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TokenRequestInterceptor implements RequestInterceptor {
    private final AuthClient authClient;
    private final EndpointProperties endpointProperties;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ATokenResp tokenResp = authClient.token(new ATokenReq(endpointProperties.getApi1().getUsername(), endpointProperties.getApi1().getPassword()));
        requestTemplate.header("Authorization", "Bearer " + tokenResp.getAccessToken());
    }
}
