package org.example;

import org.example.api.UnicornRequests;
import org.example.api.models.Unicorn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UnicornTest {

    @BeforeAll
    public static void setup() {
        // Настройка RestAssured перед запуском тестов
        RestAssured.baseURI = "https://crudcrud.com/api/bd3e9ccb2a7e438ea573d991b4f885f9"; // Указываем базовый URL API
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

    // @Test
    // void testIsItRightCreatedUnicorn() {
        // Шаг 1: Создаем объект Unicorn с именем и цветом хвоста
    // Unicorn unicorn = new Unicorn("Sparkle", "Rainbow");

        // Шаг 2: Проверяем, что имя было задано верно
    // assertEquals("Sparkle", unicorn.name, "Имя не совпадает!");

        // Шаг 3: Проверяем, что цвет хвоста был задан верно
    // assertEquals("Rainbow", unicorn.tailColor, "Цвет хвоста не совпадает!");
    // }

    @Test
    public void userShouldBeAbleChangeTailColor() throws IOException {
        // ШАГ 1: СОЗДАНИЕ СУЩНОСТИ (единорога)
        String unicornId = UnicornRequests.createUnicorn("Rarity", "Purple");

        // ШАГ 2: ИЗМЕНЕНИЕ ЦВЕТА ХВОСТА
        String newTailColor = "Blue";
        UnicornRequests.updateTailColor(unicornId, newTailColor);

        // ШАГ 3: ПРОВЕРКА, ЧТО ЦВЕТ ХВОСТА ИЗМЕНИЛСЯ
        String response = RestAssured.given()
                .when()
                .get("/unicorn/" + unicornId)
                .then()
                .extract()
                .asString();

        // Использование jsonPath для извлечения tailColor из JSON ответа
        String actualTailColor = RestAssured.given()
                .when()
                .get("/unicorn/" + unicornId)
                .then()
                .extract()
                .jsonPath()
                .getString("tailColor");

        // Проверка, что цвет хвоста был обновлен на новый
        assertEquals(newTailColor, actualTailColor, "Цвет хвоста не был изменен корректно!");
    }


    @Test public void userShouldBeAbleDeleteExistingUnicorn()
        throws IOException {
        // ШАГ 1: СОЗДАНИЕ UNICORN
        String unicornId = UnicornRequests.createUnicorn("Rarity", "Purple");
        // ШАГ 2: УДАЛЕНИЕ UNICORN
        UnicornRequests.deleteUnicorn(unicornId);
        // ШАГ 3: ПРОВЕРКА, ЧТО UNICORN НЕ СУЩЕСТВУЕТ
        RestAssured.given()
                .when()
                .get("/unicorn/" + unicornId)
                .then()
                .assertThat()
                .statusCode(404);
        //.extract()
        //.asString();
        }
}
