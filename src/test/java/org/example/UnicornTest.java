package org.example;

import org.example.api.UnicornRequests;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import java.io.IOException;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.*;

public class UnicornTest {

    private Object ContentType;

    @BeforeAll
    public static void setup() {
        // Настройка RestAssured перед запуском тестов
        RestAssured.baseURI = "https://crudcrud.com/api/447bcc18808447008618558b288944f6"; // Указываем базовый URL API
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // Логируем запросы и ответы
    }

    @Test
    public void userShouldBeAbleCreateUnicorn() throws IOException {
        // Вызов метода создания Unicorn
        String unicornId = UnicornRequests.createUnicorn("Twilight Sparkle", "Pink");

        // Проверяем, что ID не пустой
        assertAll(
                () -> assertNotNull(unicornId, "ID не должен быть пустым!"),
                () -> assertFalse(unicornId.isEmpty(), "ID не должен быть пустым!")
        );

        // Дополнительная проверка, что API действительно возвращает не пустой ответ
        String response = RestAssured.given()
                .when()
                .get("/unicorn/" + unicornId)
                .then()
                .extract()
                .asString();

        assertNotNull(response, "Ответ не должен быть пустым!");
        assertFalse(response.isEmpty(), "Ответ от API не должен быть пустым!");
    }

    @Test
    public void userShouldBeAbleChangeTailColor() throws IOException {
        // ШАГ 1: СОЗДАНИЕ СУЩНОСТИ (единорога)
        String unicornId = UnicornRequests.createUnicorn("Rarity", "Purple");

        // ШАГ 2: ИЗМЕНЕНИЕ ЦВЕТА ХВОСТА
        String newTailColor = "Blue";
        UnicornRequests.updateTailColor(unicornId, newTailColor);

        // Однократный запрос для получения ответа
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

    @Test
    public void userShouldBeAbleDeleteExistingUnicorn() throws IOException {
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
    }

    @Test
    public void userShouldBeAbleToUpdateUnicornName() throws IOException {
        // ШАГ 1: СОЗДАНИЕ СУЩНОСТИ (единорога)
        String unicornId = UnicornRequests.createUnicorn("Original Name", "Purple");

        // ШАГ 2: ОБНОВЛЕНИЕ ИМЕНИ
        String newName = "Updated Name";
        UnicornRequests.updateUnicornName(unicornId, newName);

        // ШАГ 3: ПРОВЕРКА, ЧТО ИМЯ ОБНОВИЛОСЬ
        String response = UnicornRequests.getUnicorn(unicornId);
        String actualName = RestAssured.given()
                .when()
                .get("/unicorn/" + unicornId)
                .then()
                .extract()
                .jsonPath()
                .getString("name");

        // Проверка, что имя было обновлено на новое
        assertEquals(newName, actualName, "Имя единорога не было обновлено корректно!");
    }

    @Test
    public void shouldReturnNotFoundWhenUnicornDoesNotExist() {
        RestAssured.given()
                .when()
                .get("/unicorn/nonexistent_id")
                .then()
                .statusCode(404); // Ожидаем 404 Not Found
    }

    @Test
    public void testResponseTime() {
        RestAssured.given()
                .when()
                .get("/unicorn")
                .then()
                .statusCode(200)
                .time(lessThan(2000L)); // Время отклика должно быть меньше 2000 мс
    }
}
