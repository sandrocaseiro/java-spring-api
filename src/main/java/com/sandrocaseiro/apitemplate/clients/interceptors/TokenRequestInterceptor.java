package com.sandrocaseiro.apitemplate.clients.interceptors;

import com.sandrocaseiro.apitemplate.clients.AuthClient;
import com.sandrocaseiro.apitemplate.models.api.ATokenReq;
import com.sandrocaseiro.apitemplate.models.api.ATokenResp;
import com.sandrocaseiro.apitemplate.properties.EndpointProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
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
