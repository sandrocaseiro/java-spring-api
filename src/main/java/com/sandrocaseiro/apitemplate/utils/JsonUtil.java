package com.sandrocaseiro.apitemplate.utils;

import com.sandrocaseiro.apitemplate.configs.WebConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {
    private static final ObjectMapper mapper = WebConfig.getJsonMapper();

    private JsonUtil() { }

    public static <T> String serialize(T model) {
        return serialize(model, false);
    }

    public static <T> String serializePrettyPrint(T model) {
        return serialize(model, true);
    }

    private static <T> String serialize(T model, boolean prettyPrint) {
        if (model == null)
            return null;

        try {
            if (prettyPrint)
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
            else
                return mapper.writeValueAsString(model);
        }
        catch (JsonProcessingException e) {
            log.error("Json serialization error", e);
            return null;
        }
    }
}
