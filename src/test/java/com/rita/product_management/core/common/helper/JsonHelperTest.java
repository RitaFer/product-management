package com.rita.product_management.core.common.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class JsonHelperTest {

    @Data
    static class SampleObject {
        private String name;
        private int age;

        @JsonCreator
        public SampleObject(
                @JsonProperty("name") String name,
                @JsonProperty("age") int age) {
            this.name = name;
            this.age = age;
        }
    }

    @Test
    void givenPrivateConstructor_whenInstantiated_thenThrowsUnsupportedOperationException() throws NoSuchMethodException {
        Constructor<JsonHelper> constructor = JsonHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, constructor::newInstance);

        try {
            constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            assertInstanceOf(UnsupportedOperationException.class, e.getCause());
            assertEquals("Utility class", e.getCause().getMessage());
        }
    }

    @Test
    void givenObject_whenToJsonString_thenReturnsJsonString() throws JsonProcessingException {
        SampleObject sampleObject = new SampleObject("John Doe", 30);
        String jsonString = JsonHelper.toJsonString(sampleObject);
        assertNotNull(jsonString);
        assertTrue(jsonString.contains("\"name\":\"John Doe\""));
        assertTrue(jsonString.contains("\"age\":30"));
    }

    @Test
    void givenInvalidJsonString_whenToObject_thenThrowsException() {
        String invalidJson = "{name:\"John Doe\",age:30}";
        assertThrows(JsonProcessingException.class, () -> JsonHelper.toObject(invalidJson, SampleObject.class));
    }

    @Test
    void givenValidJsonString_whenToObject_thenReturnsObject() throws JsonProcessingException {
        String validJson = "{\"name\":\"John Doe\",\"age\":30}";
        SampleObject result = JsonHelper.toObject(validJson, SampleObject.class);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals(30, result.getAge());
    }

    @Test
    void givenObjectAndClass_whenToObject_thenReturnsConvertedObject() {
        SampleObject sampleObject = new SampleObject("Jane Doe", 25);

        SampleObject result = JsonHelper.toObject(sampleObject, SampleObject.class);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals(25, result.getAge());
    }


    @Test
    void givenInvalidObject_whenToObject_thenReturnsNull() {
        Object invalidObject = new Object();
        SampleObject result = JsonHelper.toObject(invalidObject, SampleObject.class);
        assertNull(result);
    }

    @Test
    void givenObjectAndClass_whenParse_thenReturnsParsedObject() {
        SampleObject sampleObject = new SampleObject("Jane Doe", 25);
        SampleObject result = JsonHelper.parse(sampleObject, SampleObject.class);
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals(25, result.getAge());
    }

}
