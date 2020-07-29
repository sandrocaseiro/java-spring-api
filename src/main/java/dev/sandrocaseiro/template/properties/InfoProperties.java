package dev.sandrocaseiro.template.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("info")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class InfoProperties {
    private final App app;

    @RequiredArgsConstructor
    @Getter
    public static class App {
        private final String id;
    }
}
