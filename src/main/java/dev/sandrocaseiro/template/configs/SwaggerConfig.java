package dev.sandrocaseiro.template.configs;

import dev.sandrocaseiro.template.exceptions.AppErrors;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.QueryParameter;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfig {
    private static final String SECURITY_SCHEME = "JWT";

    @Value("${info.app.version}")
    private String apiVersion;
    @Value("${info.app.name}")
    private String apiName;
    @Value("${info.app.description}")
    private String apiDescription;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .info(apiInfo())
            .components(addComponents())
            .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME))
            ;
    }

    @Bean
    public OpenApiCustomiser globalHeadersCustomiser() {
        return openApi -> {
            openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {
                    operation.addParametersItem(
                        new HeaderParameter()
                            .name(HttpHeaders.ACCEPT_LANGUAGE)
                            .required(false)
                            .schema(
                                new StringSchema()
                                    ._default("en-US")
                                    .addEnumItem("en-US")
                                    .addEnumItem("pt-BR")
                            )
                    );

                    Parameter pageableParam = operation.getParameters().stream()
                        .filter(p -> p.getSchema() != null
                            && p.getSchema().get$ref() != null
                            && p.getSchema().get$ref().endsWith("DPageable")).findFirst().orElse(null);
                    if (pageableParam != null) {
                        operation.getParameters().remove(pageableParam);
                        operation.addParametersItem(
                            new QueryParameter()
                                .name("$pageoffset")
                                .description("Current page number")
                                .example(1)
                        );
                        operation.addParametersItem(
                            new QueryParameter()
                                .name("$pagelimit")
                                .description("Result page size")
                                .example(10)
                        );
                        operation.addParametersItem(
                            new QueryParameter()
                                .name("$sort")
                                .description("Sorting fields\n\n* -[field] - field descending\n\n* +[field] - field ascending")
                                .example("-id,+name")
                        );
                    }
                });

            Schema errorSchema = openApi.getComponents().getSchemas().get("Error");
            if (errorSchema != null) {
                Schema codeSchema = (Schema) errorSchema.getProperties().get("code");
                StringBuilder builder = new StringBuilder(codeSchema.getDescription())
                    .append(System.lineSeparator()).append(System.lineSeparator())
                    .append(System.lineSeparator()).append(System.lineSeparator());
                for (AppErrors error : AppErrors.values()) {
                    codeSchema.addEnumItemObject(error.getCode());
                    builder
                        .append("* ")
                        .append(error.getCode())
                        .append(" - ")
                        .append(error.toString())
                        .append(System.lineSeparator()).append(System.lineSeparator());
                }
                codeSchema.description(builder.toString());
            }
        };
    }

    private Components addComponents() {
        return new Components().addSecuritySchemes(SECURITY_SCHEME,
            new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .flows(new OAuthFlows()
                    .password(new OAuthFlow()
                        .tokenUrl("/api/v1/token")
                        .refreshUrl("/api/v1/token")
                    )
                )
                .name(AUTHORIZATION)
        );
    }

    private Info apiInfo() {
        return new Info()
            .title(apiName)
            .description(apiDescription)
            .version(apiVersion);
    }
}
