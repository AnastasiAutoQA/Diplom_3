# Diplom_3
## UI testing
[Тестируемое приложение](https://stellarburgers.nomoreparties.site/)
## Используемые технологии в проекте:
- Java 11
- Maven 3.9.0
- JUnit 4.13.2
- Selenium
- Allure
- RestAssured
## Команды:
- для запуска те[README.md](README.md)стовых сценариев ```mvn clean test```
- для геренации отчета ```mvn allure:serve```
## Use git bash if running on Windows 
(команды должны идти одной строкой, без переносов на другую строку -
уберите переносы, если скопировали код отсюда)

mvn -Dwebdriver.chrome.driver=/src/main/resources/chromedriver.exe \ 
-Dwebdriver.chrome.binary=C:/Program Files/Google/Chrome/Application/chrome.exe \
-Dwebdriver.yandex.driver=/src/main/resources/chromedriver_116.exe \
-Dwebdriver.yandex.binary=C:/Users/Lenovo/AppData/Local/Yandex/YandexBrowser/Application/browser.exe \
-Dbrowser=yandex test