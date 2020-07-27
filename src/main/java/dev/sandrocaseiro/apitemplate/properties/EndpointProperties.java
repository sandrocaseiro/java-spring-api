package dev.sandrocaseiro.apitemplate.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("endpoints")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class EndpointProperties {
    private final API api1;

    @RequiredArgsConstructor
    @Getter
    public static class API {
        @Value("${base-url}")
        private final String baseUrl;

        private final String username;

        private final String password;
    }
}
