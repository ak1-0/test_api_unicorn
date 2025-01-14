package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Метод для преобразования объекта в JSON строку
    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }

    // Метод для преобразования JSON строки в объект
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        return objectMapper.readValue(json, clazz);
    }
}
