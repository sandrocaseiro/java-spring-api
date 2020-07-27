package dev.sandrocaseiro.apitemplate.clients.configs;

import dev.sandrocaseiro.apitemplate.clients.AuthClient;
import dev.sandrocaseiro.apitemplate.clients.interceptors.TokenRequestInterceptor;
import dev.sandrocaseiro.apitemplate.properties.EndpointProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class UserClientConfiguration {
    private final AuthClient authClient;
    private final EndpointProperties endpointProperties;

    @Bean
    public TokenRequestInterceptor tokenInterceptor() {
        return new TokenRequestInterceptor(authClient, endpointProperties);
    }
}
