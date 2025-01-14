package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.example.api.models.Unicorn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnicornTest {

    @BeforeAll
    public static void setup() {
        // Настройка RestAssured перед запуском тестов
        RestAssured.baseURI = "https://crudcrud.com/api/d455de6fe2354674841491bcc4778825"; // Указываем базовый URL API
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // Логируем запросы и ответы
    }

    @Test
    public void userShouldBeAbleCreateUnicorn() {
        // Тело запроса для создания Unicorn
        String requestBody = """
                {
                  "name": "Twilight Sparkle",
                  "tailColor": "Pink"
                }
                """;

        // Отправка POST-запроса и проверка ответа
        RestAssured.given()
                .contentType(ContentType.JSON) // Указываем, что отправляем JSON
                .body(requestBody)             // Передаём тело запроса
                .when()
                .post("/unicorn")              // Отправляем POST-запрос на создание Unicorn
                .then()
                .statusCode(201);              // Проверяем, что ответ имеет статус 201 Created
    }

    @Test
    void testIsItRightCreatedUnicorn() {
        // Шаг 1: Создаем объект Unicorn с именем и цветом хвоста
        Unicorn unicorn = new Unicorn("Sparkle", "Rainbow");

        // Шаг 2: Проверяем, что имя было задано верно
        assertEquals("Sparkle", unicorn.name, "Имя не совпадает!");

        // Шаг 3: Проверяем, что цвет хвоста был задан верно
        assertEquals("Rainbow", unicorn.tailColor, "Цвет хвоста не совпадает!");
    }
}
