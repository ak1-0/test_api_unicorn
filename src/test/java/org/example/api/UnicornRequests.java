package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.models.Unicorn;
import java.io.IOException;

public class UnicornRequests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    // Метод для создания Unicorn
    public static String createUnicorn(String name, String tailColor) throws IOException {
        Unicorn unicorn = new Unicorn(name, tailColor);
        String requestBody = objectMapper.writeValueAsString(unicorn);

        // Отправляем запрос и возвращаем ID созданного Unicorn
        return RestAssured.given()
                .contentType(ContentType.JSON) // Указываем, что отправляем JSON
                .body(requestBody)             // Передаём тело запроса
                .when()
                .post("/unicorn")              // Отправляем запрос на конечную точку
                .then()
                .assertThat()
                .statusCode(201)               // Проверяем, что ответ — 201 Created
                .extract()
                .path("_id");                  // Извлекаем ID из ответа
    }
}

