package page_object_model;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPageModel {
    //Переход на странице регистрации - по ссылке "Зарегистрироваться" на логин странице
    private final WebDriver driver;
    private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    private static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final By REGISTRATION_PAGE = By.className("Auth_login__3hAey");
    private static final By NAME_INPUT_FIELD = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Имя')]]");
    private static final By EMAIL_INPUT_FIELD = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Email')]]");
    private static final By PASSWORD_INPUT_FIELD = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Пароль')]]");
    private static final By REGISTER_BUTTON = By.xpath("//button[text() = 'Зарегистрироваться']");
    private static final By LOGIN_BUTTON_REG_PAGE = By.xpath(".//*[contains(text(), 'Войти')]");
    private static final By INCORRECT_PASSWORD_ERROR = By.xpath(".//*[contains(text(), 'Некорректный пароль')]");
    private static final By ERROR_MESSAGE_SECTION = By.xpath("//p[@class = 'input__error text_type_main-default']");
    private static final By DUPLICATE_USER_ERROR = By.xpath("//p[contains(text(),'Такой пользователь уже существует')]");

    public RegisterPageModel(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(REGISTRATION_PAGE_URL);
    }
    public void redirectToMainPage() {
        driver.get(MAIN_PAGE_URL);
    }
    public void waitForLoadRegistrationPage() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(REGISTRATION_PAGE));

    }
    public void loginButtonRegPageClick() {
        WebElement loginAccountButton = driver.findElement(LOGIN_BUTTON_REG_PAGE);
        loginAccountButton.click();
    }
    public void registerButtonClick() {
        WebElement registerButton = driver.findElement(REGISTER_BUTTON);
        registerButton.click();
    }

    //Заполняем данными поля формы регистрации - данные заходят из Параметров в тесте
    public void fillRegistrationForm(String name, String email, String password) {
        driver.findElement(NAME_INPUT_FIELD).clear();
        driver.findElement(NAME_INPUT_FIELD).sendKeys(name);
        driver.findElement(EMAIL_INPUT_FIELD).clear();
        driver.findElement(EMAIL_INPUT_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_INPUT_FIELD).clear();
        driver.findElement(PASSWORD_INPUT_FIELD).sendKeys(password);
    }
    //Проверка видимости ошибки Некорректный пароль
    public String getIncorrectPasswordErrorText() {
        WebElement incorrectPasswordError = driver.findElement(INCORRECT_PASSWORD_ERROR);
        return incorrectPasswordError.getText();
    }

    @Step("Перейти на страницу логина по клику на кнопку регистрации")
    public String redirectToLoginAfterRegisterClick() {
        driver.findElement(REGISTER_BUTTON).isDisplayed();
        driver.findElement(REGISTER_BUTTON).click();
        driver.get(LOGIN_PAGE_URL);
        return driver.getCurrentUrl();
    }

    public String getDuplicatedUserErrorText() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE_SECTION));
        WebElement duplicateUserError = driver.findElement(DUPLICATE_USER_ERROR);
        return duplicateUserError.getText();
    }

}
