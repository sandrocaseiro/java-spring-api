package com.sandrocaseiro.apitemplate;

import com.sandrocaseiro.apitemplate.properties.CorsProperties;
import com.sandrocaseiro.apitemplate.properties.EndpointProperties;
import com.sandrocaseiro.apitemplate.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties({CorsProperties.class, EndpointProperties.class, JwtProperties.class})
public class APITemplateApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(APITemplateApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(APITemplateApplication.class);
    }
}
