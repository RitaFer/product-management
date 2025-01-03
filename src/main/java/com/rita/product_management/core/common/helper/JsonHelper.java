package com.rita.product_management.core.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonHelper {

    private JsonHelper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String toJsonString(final Object object) throws JsonProcessingException {
        log.debug("Converting object of type {} to JSON string", object.getClass().getName());
        return objectMapper().writeValueAsString(object);
    }

    public static <T> T toObject(final String object, final Class<T> clazz) throws JsonProcessingException {
        log.debug("Converting JSON string to object of type {}", clazz.getName());
        return objectMapper().readValue(object, clazz);
    }

    public static <T> T toObject(final Object object, final Class<T> clazz) {
        try {
            log.debug("Converting object of type {} to object of type {}", object.getClass().getName(), clazz.getName());
            return objectMapper().readValue(toJsonString(object), clazz);
        } catch (JsonProcessingException e) {
            log.error("Error converting object of type {} to object of type {}. Returning null.",
                    object.getClass().getName(), clazz.getName(), e);
            return null;
        }
    }

    public static <T> T parse(final Object object, final Class<T> clazz) {
        log.debug("Parsing object of type {} to object of type {}", object.getClass().getName(), clazz.getName());
        return objectMapper().convertValue(object, clazz);
    }

    private static ObjectMapper objectMapper() {
        log.debug("Creating and configuring new ObjectMapper instance");
        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

}

