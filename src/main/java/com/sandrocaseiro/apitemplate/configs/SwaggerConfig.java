package com.sandrocaseiro.apitemplate.configs;

import com.sandrocaseiro.apitemplate.security.UserPrincipal;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private static final String SECURITY_TYPE = "JWT";

    @Value("${info.app.version}")
    private String apiVersion;
    @Value("${info.app.name}")
    private String apiName;
    @Value("${info.app.description}")
    private String apiDescription;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .ignoredParameterTypes(Errors.class, UserPrincipal.class)
            .select()
            .apis(controllers())
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())
            .securityContexts(Collections.singletonList(securityContext()))
            .securitySchemes(Collections.singletonList(apiKey()))
            ;
    }

    private static Predicate<RequestHandler> controllers() {
        return RequestHandlerSelectors
            .withClassAnnotation(RestController.class);
    }

    private static ApiKey apiKey() {
        return new ApiKey(SECURITY_TYPE, AUTHORIZATION, "header");
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

        return Collections.singletonList(new SecurityReference(SECURITY_TYPE,  new AuthorizationScope[]{authorizationScope}));
    }

    private static SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(apiName)
            .description(apiDescription)
            .version(apiVersion)
            .build();
    }
}
