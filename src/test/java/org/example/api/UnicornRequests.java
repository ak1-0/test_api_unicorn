package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.models.Unicorn;
import org.example.utils.JsonMapper;

import java.io.IOException;

public class UnicornRequests {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    // Метод для создания Unicorn
    public static String createUnicorn(String name, String tailColor) throws IOException {
        Unicorn unicorn = new Unicorn(name, tailColor);
        String requestBody = objectMapper.writeValueAsString(unicorn);

        // Отправляем запрос и возвращаем ID созданного Unicorn
        return RestAssured.given()
                //.accept(ContentType.JSON) // Указываем, что ожидаем JSON в ответе
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/unicorn")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("_id");                  // Извлекаем ID из ответа
    }

    public static void updateTailColor(String unicornId, String newTailColor) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"tailColor\": \"" + newTailColor + "\"}")
                .when()
                .put("/unicorn/" + unicornId)
                .then()
                .assertThat()
                .statusCode(200);  // Ожидаем, что сервер вернет статус 200 (OK)
    }



    public static void deleteUnicorn(String unicornId) {
        RestAssured.given()
                .when()
                .delete("/unicorn/" + unicornId)
                .then()
                .assertThat()
                .statusCode(200); // Ожидаем, что сервер возвращает 200 при удалении
    }
}

