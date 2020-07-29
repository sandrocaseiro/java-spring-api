package dev.sandrocaseiro.template;

import dev.sandrocaseiro.template.configs.FeignConfig;
import dev.sandrocaseiro.template.properties.CorsProperties;
import dev.sandrocaseiro.template.properties.EndpointProperties;
import dev.sandrocaseiro.template.properties.InfoProperties;
import dev.sandrocaseiro.template.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EnableFeignClients(defaultConfiguration = FeignConfig.class)
@EnableConfigurationProperties({CorsProperties.class, EndpointProperties.class, InfoProperties.class, JwtProperties.class})
public class APIApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(APIApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(APIApplication.class);
    }
}
