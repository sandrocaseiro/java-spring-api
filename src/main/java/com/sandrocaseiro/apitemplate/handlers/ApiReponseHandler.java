package com.sandrocaseiro.apitemplate.handlers;

import com.sandrocaseiro.apitemplate.exceptions.CustomErrors;
import com.sandrocaseiro.apitemplate.models.DResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Order(2)
@RequiredArgsConstructor
public class ApiReponseHandler implements ResponseBodyAdvice<Object> {
    private final MessageSource messageSource;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getParameterType().equals(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class converterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        String message = messageSource.getMessage(CustomErrors.SUCCESS.getMessageRes(), null, LocaleContextHolder.getLocale());

        return DResponse.ok(body, CustomErrors.SUCCESS.getCode(), message);
    }
}
