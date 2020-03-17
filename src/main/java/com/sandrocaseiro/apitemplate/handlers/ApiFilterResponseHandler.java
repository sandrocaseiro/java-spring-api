package com.sandrocaseiro.apitemplate.handlers;

import com.sandrocaseiro.apitemplate.exceptions.FilterBadRequestException;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Order(1)
@RequiredArgsConstructor
public class ApiFilterResponseHandler implements ResponseBodyAdvice<Object> {
    public static final String FILTER_PARAM = "$filter";

    private final ObjectMapper mapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !returnType.getParameterType().equals(ResponseEntity.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType mediaType, Class converterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String filter = ServletRequestUtils.getStringParameter(servletRequest, FILTER_PARAM, "");

        if (StringUtils.isEmpty(filter))
            return body;

        mapper.addMixIn(Object.class, PropertyFilterMixin.class);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider()
            .addFilter("PropertyFilter", SimpleBeanPropertyFilter.filterOutAllExcept(filter.split(",")));
        mapper.setFilterProvider(filterProvider);

        try {
            String json = mapper.writeValueAsString(body);

            if (Collection.class.isAssignableFrom(body.getClass()) || body.getClass().isArray())
                return mapper.readValue(json, List.class);
            else
                return mapper.readValue(json, Map.class);
        }
        catch (JsonProcessingException e) {
            throw new FilterBadRequestException(FILTER_PARAM, e);
        }
    }

    @JsonFilter("PropertyFilter")
    static class PropertyFilterMixin {}
}
