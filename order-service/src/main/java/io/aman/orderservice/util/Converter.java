package io.aman.orderservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Converter {
    private final ObjectMapper objectMapper;

    public String toJSON(final Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert " + object + " to json", e);
        }
    }

    public <T> T toObject(String json , Class<T> clazz){
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert " + json + " to object type " + clazz.getSimpleName(), e);
        }
    }
}
