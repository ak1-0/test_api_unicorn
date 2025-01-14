package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.models.Unicorn;
import org.example.utils.JsonMapper;
import org.hamcrest.Matcher;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class UnicornRequests {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Метод для отправки GET запроса и получения ответа в виде строки
    private static String sendGetRequest(String url) {
        return RestAssured.given()
                .when()
                .get(url)
                .then()
                .assertThat()
                .statusCode(200)  // Ожидаем успешный ответ
                .extract()
                .asString();
    }

    // Метод для отправки POST запроса и извлечения ID из ответа
    private static String sendPostRequest(String url, String body) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(url)
                .then()
                .assertThat()
                .statusCode(201)  // Ожидаем, что сервер вернет статус 201 (создано)
                .extract()
                .path("_id");  // Извлекаем ID из ответа
    }

    // Метод для отправки PUT запроса
    private static void sendPutRequest(String url, String body) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(url)
                .then()
                .assertThat()
                .statusCode(200);  // Ожидаем, что сервер вернет статус 200 (OK)
    }

    // Метод для отправки DELETE запроса
    private static void sendDeleteRequest(String url) {
        RestAssured.given()
                .when()
                .delete(url)
                .then()
                .assertThat()
                .statusCode(200);  // Ожидаем, что сервер вернет статус 200 (OK)
    }

    // Метод для создания Unicorn
    public static String createUnicorn(String name, String tailColor) throws IOException {
        Unicorn unicorn = new Unicorn(name, tailColor);
        String requestBody = objectMapper.writeValueAsString(unicorn);
        return sendPostRequest("/unicorn", requestBody);
    }

    // Метод для обновления цвета хвоста
    public static void updateTailColor(String unicornId, String newTailColor) {
        String requestBody = "{\"tailColor\": \"" + newTailColor + "\"}";
        sendPutRequest("/unicorn/" + unicornId, requestBody);
    }

    // Метод для удаления Unicorn
    public static void deleteUnicorn(String unicornId) {
        sendDeleteRequest("/unicorn/" + unicornId);
    }

    // Метод для получения Unicorn по ID
    public static String getUnicorn(String unicornId) {
        return sendGetRequest("/unicorn/" + unicornId);
    }
}

