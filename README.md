# Протестировано API учебного сервиса Яндекс.Самокат. Его документация: qa-scooter.praktikum-services.ru/docs/
Создан Maven проект, подключены  JUnit 4, RestAssured, Allure.
Для каждой ручки тесты лежат в отдельном классе.
Написаны тесты на ручку «Создать курьера»,«Логин курьера»,«Создать заказ».
Написаны тесты на ручку, которая получает список заказов.
Сделан отчёт с помощью Allure. В тестах проверяется тело и код ответа. Все тесты независимы.
Необходимые тестовые данные создаются перед тестом и удаляются после того, как он выполнится.
В тестах нет хардкода.
В проекте используется Java 11.
