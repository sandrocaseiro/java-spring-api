package dev.sandrocaseiro.template.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("jwt")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class JwtProperties {
    private final Long expiration;

    private final Long refreshExpiration;

    private final String secret;

    private final String tokenPrefix;

    private final String header;
}
