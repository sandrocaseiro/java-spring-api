package dev.sandrocaseiro.template.clients.configs;

import dev.sandrocaseiro.template.clients.AuthClient;
import dev.sandrocaseiro.template.clients.interceptors.TokenRequestInterceptor;
import dev.sandrocaseiro.template.properties.EndpointProperties;
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
