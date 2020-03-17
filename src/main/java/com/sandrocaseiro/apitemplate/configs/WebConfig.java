package com.sandrocaseiro.apitemplate.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sandrocaseiro.apitemplate.handlers.PageableRequestResolver;
import com.sandrocaseiro.apitemplate.serializers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageableRequestResolver());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
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
