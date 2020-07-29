package dev.sandrocaseiro.template.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sandrocaseiro.template.configs.WebConfig;
import feign.Response;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
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

    public static <T> T deserialize(InputStream json, TypeReference<T> typeRef) {
        if (json == null)
            return null;

        try {
            return mapper.readValue(json, typeRef);
        }
        catch (IOException e) {
            log.error("Json deserialization error", e);
            return null;
        }
    }

    public static <T> T deserialize(Response.Body body, TypeReference<T> typeRef) {
        if (body == null)
            return null;

        try {
            return deserialize(body.asInputStream(), typeRef);
        }
        catch (IOException e) {
            log.error("Json deserialization error", e);
            return null;
        }
    }
}
