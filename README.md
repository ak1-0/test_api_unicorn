# Unicorn API Tests

Домашняя работа в проекте **AlexPshe NoBugs**. API автотест с использованием эндпоинта **CrudCrud.com**.

Проект содержит тесты для работы с API управления единорогами, используя библиотеку **RestAssured**. Тесты проверяют различные операции с сущностью "единорог", такие как создание, обновление, удаление и проверка существования.

## Стек технологий

- **Java 11**
- **JUnit 5**
- **RestAssured** для тестирования REST API

## Описание тестов

- **userShouldBeAbleCreateUnicorn** — Проверяет создание единорога.
- **userShouldBeAbleChangeTailColor** — Проверяет изменение цвета хвоста единорога.
- **userShouldBeAbleDeleteExistingUnicorn** — Проверяет удаление единорога.
- **userShouldBeAbleToUpdateUnicornName** — Проверяет обновление имени единорога.
- **shouldReturnNotFoundWhenUnicornDoesNotExist** — Проверяет, что возвращается ошибка 404, если единорог не существует.
- **testResponseTime** — Проверяет, что время отклика API не превышает 2000 миллисекунд.
