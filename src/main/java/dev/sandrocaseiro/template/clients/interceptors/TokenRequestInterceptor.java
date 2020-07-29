package dev.sandrocaseiro.template.clients.interceptors;

import dev.sandrocaseiro.template.clients.AuthClient;
import dev.sandrocaseiro.template.models.api.ATokenReq;
import dev.sandrocaseiro.template.models.api.ATokenResp;
import dev.sandrocaseiro.template.properties.EndpointProperties;
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
