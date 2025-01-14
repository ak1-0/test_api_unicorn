package org.example;

import org.example.api.UnicornRequests;
import org.example.api.models.Unicorn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class UnicornTest {

    @BeforeAll
    public static void setup() {
        // Настройка RestAssured перед запуском тестов
        RestAssured.baseURI = "https://crudcrud.com/api/d455de6fe2354674841491bcc4778825"; // Указываем базовый URL API
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // Логируем запросы и ответы
    }

    @Test
    public void userShouldBeAbleCreateUnicorn() throws IOException {
        // Вызов метода создания Unicorn
        String unicornId = UnicornRequests.createUnicorn("Twilight Sparkle", "Pink");

        // Проверяем, что ID не пустой
        assertNotNull(unicornId, "ID не должен быть пустым!");
        assertFalse(unicornId.isEmpty(), "ID не должен быть пустым!");
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
