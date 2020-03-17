package com.sandrocaseiro.apitemplate.configs;

import com.sandrocaseiro.apitemplate.exceptions.APIException;
import com.sandrocaseiro.apitemplate.models.AError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class FeignConfig {
    @Bean
    public Encoder feignEncoder() {
        return new FormEncoder(new JacksonEncoder());
    }

    @Bean
    public Decoder feignDecoder() {
        return new JacksonDecoder(new ObjectMapper());
    }

    @Bean
    public ErrorDecoder apiErrorDecoder() {
        return new APIErrorDecoder();
    }

    private static class APIErrorDecoder implements ErrorDecoder {
        @Override
        public APIException decode(String s, Response response) {
            ObjectMapper json = new ObjectMapper();
            try {
                AError error = json.readValue(response.body().asInputStream(), AError.class);

                return new APIException(error.getErrors().stream().map(AError.Error::getDescription).findFirst().orElse(""));
            }
            catch (IOException e) {
                return new APIException(e);
            }
        }
    }
}
