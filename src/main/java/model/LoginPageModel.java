package model;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static environment.EnvConfig.DEFAULT_TIMEOUT;
import static model.MainPageModel.MAIN_PAGE;

public class LoginPageModel {
    private final WebDriver driver;
    public static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    private static final String ACCOUNT_PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
    private static final By ACCOUNT_PROFILE_BUTTON = By.xpath(".//*[contains(text(), 'Личный Кабинет')]");
    private static final By LOGIN_PAGE = By.xpath(".//div[@class = 'App_App__aOmNj']");
    private static final By LOGIN_SECTION = By.xpath(".//div[@class = 'Auth_login__3hAey']");
    private static final By EMAIL_INPUT_FIELD = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Email')]]");
    private static final By PASSWORD_INPUT_FIELD = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Пароль')]]");
    private static final By LOGIN_BUTTON_LOGIN_PAGE = By.xpath("//button[text() = 'Войти']");
    private static final By REGISTER_BUTTON_ON_LOGIN = By.xpath(".//*[contains(text(), 'Зарегистрироваться')]");
    private static final By RESTORE_PASSWORD_BUTTON = By.xpath (".//*[contains(text(), 'Восстановить пароль')]");

    public LoginPageModel(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(LOGIN_PAGE_URL);
    }
    public void waitForLoadLoginPage() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(LOGIN_PAGE));

    }
    public void waitForLoadLoginSection() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(LOGIN_SECTION));

    }
    public void registerButtonClick(){
        WebElement personalAccountButton = driver.findElement(REGISTER_BUTTON_ON_LOGIN);
        personalAccountButton.click();
    }
    public void restorePasswordButtonBClick(){
        WebElement personalAccountButton = driver.findElement(RESTORE_PASSWORD_BUTTON);
        personalAccountButton.click();
    }

    public void fillLoginForm(String email, String password) {
        driver.findElement(EMAIL_INPUT_FIELD).clear();
        driver.findElement(EMAIL_INPUT_FIELD).sendKeys(email);
        driver.findElement(PASSWORD_INPUT_FIELD).clear();
        driver.findElement(PASSWORD_INPUT_FIELD).sendKeys(password);
    }
    public void loginButtonClick(){
        WebElement personalAccountButton = driver.findElement(LOGIN_BUTTON_LOGIN_PAGE);
        personalAccountButton.click();
    }
    public void clickLoginAndRedirectToMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(MAIN_PAGE));
    }
    public void loggedInUserClicksAccountProfileButton() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(MAIN_PAGE));
        driver.findElement(ACCOUNT_PROFILE_BUTTON).click();
    }

    @Step("Получить текущий адрес страницы")
    public String getLoginPageUrl() {
        return driver.getCurrentUrl();
    }

    public String getEmail(){
        return driver.findElement(EMAIL_INPUT_FIELD).getText();
    }

    public String getPassword(){
        return driver.findElement(PASSWORD_INPUT_FIELD).getText();
    }

}

