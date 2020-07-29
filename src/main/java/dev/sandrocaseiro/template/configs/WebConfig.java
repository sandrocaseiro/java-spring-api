package dev.sandrocaseiro.template.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.sandrocaseiro.template.handlers.LogHandlerInterceptor;
import dev.sandrocaseiro.template.handlers.PageableRequestResolver;
import dev.sandrocaseiro.template.serializers.*;
import dev.sandrocaseiro.template.services.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final LogService logService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableRequestResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogHandlerInterceptor(logService));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));
    }

    @Bean
    public static ObjectMapper getJsonMapper() {
        return new ObjectMapper()
            .registerModule(new SimpleModule()
                .addSerializer(LocalDate.class, new LocalDateSerializer())
                .addSerializer(LocalTime.class, new LocalTimeSerializer())
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer())
                .addDeserializer(LocalDate.class, new LocalDateDeserializer())
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer())
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer())
            );
    }
}
