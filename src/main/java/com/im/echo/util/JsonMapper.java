package com.im.echo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {

    private JsonMapper() {}

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T decode(String jsonString, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, clazz);
    }

    public static String parse(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

}
