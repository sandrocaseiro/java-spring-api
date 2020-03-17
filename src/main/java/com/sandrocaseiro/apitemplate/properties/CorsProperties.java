package com.sandrocaseiro.apitemplate.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Collection;
import java.util.Collections;

@ConfigurationProperties("cors")
@ConstructorBinding
public class CorsProperties {
    @Getter
    private final boolean enabled;
    private final String[] allowedMethods;
    private final String[] allowedHeaders;
    private final String[] exposedHeaders;
    private final String[] allowedOrigins;

    public CorsProperties(boolean enabled, String[] allowedMethods, String[] allowedHeaders, String[] exposedHeaders, String[] allowedOrigins) {
        this.enabled = enabled;
        this.allowedMethods = allowedMethods == null ? new String[0] : allowedMethods.clone();
        this.allowedHeaders = allowedHeaders == null ? new String[0] : allowedHeaders.clone();
        this.exposedHeaders = exposedHeaders == null ? new String[0] : exposedHeaders.clone();
        this.allowedOrigins = allowedOrigins == null ? new String[0] : allowedOrigins.clone();
    }

    public String[] getAllowedMethods() {
        return allowedMethods.clone();
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders.clone();
    }

    public String[] getExposedHeaders() {
        return exposedHeaders.clone();
    }

    public String[] getAllowedOrigins() {
        return allowedOrigins.clone();
    }
}
