package dev.sandrocaseiro.apitemplate.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dev.sandrocaseiro.apitemplate.utils.DateUtil;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDateTime == null)
            jsonGenerator.writeString("");
        else
            jsonGenerator.writeString(DateUtil.toString(localDateTime));
    }
}
