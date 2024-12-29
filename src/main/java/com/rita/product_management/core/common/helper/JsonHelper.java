package com.rita.product_management.core.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public final class JsonHelper {

    private JsonHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String toJsonString(final Object object) throws JsonProcessingException {
        return objectMapper().writeValueAsString(object);
    }

    public static <T> T toObject(final String object, final Class<T> clazz) throws JsonProcessingException {
        return objectMapper().readValue(object, clazz);
    }

    public static <T> T toObject(final Object object, final Class<T> clazz) {
        try {
            return objectMapper().readValue(toJsonString(object), clazz);
        } catch (JsonProcessingException e) {
            System.out.println("[JsonHelper] Error to convert json object to object, returning an empty object");
            return null;
        }
    }

    public static <T> T parse(final Object object, final Class<T> clazz) {
        return objectMapper().convertValue(object, clazz);
    }

    private static ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
