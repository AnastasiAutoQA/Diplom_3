package model;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static environment.EnvConfig.DEFAULT_TIMEOUT;
import static model.MainPageModel.MAIN_PAGE_URL;
import static org.junit.Assert.assertNotNull;

public class AccountProfilePageModel {
    private final WebDriver driver;
    public static final String ACCOUNT_PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
    private static final By ACCOUNT_PROFILE_PAGE = By.xpath(".//div[@class='App_App__aOmNj']");
    private static final By EXIT_BUTTON = By.xpath("//button[text() ='Выход']");
    private static final By PROFILE_DATA_SECTION = By.xpath("//div[@class = 'Profile_profile__3dzvr']");
    private static final By NAME_FIELD_PROFILE = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Имя')]]");
    private static final By EMAIL_FIELD_PROFILE = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Логин')]]");
    private static final By PASSWORD_FIELD_PROFILE = By.xpath("//input[contains(@class, 'text input__textfield text_type_main-default') and preceding-sibling::label[contains(text(), 'Пароль')]]");
    private static final By HEADER_SECTION = By.xpath(".//nav[@class='AppHeader_header__nav__g5hnF']");
    private static final By STELLAR_LOGO = By.xpath(".//div[@class = 'AppHeader_header__logo__2D0X2']");
    private static final By CONSTRUCTOR_BUTTON = By.xpath("//p[contains(@class, 'AppHeader_header__linkText__3q_va ml-2') and contains(text(), 'Конструктор')]");

    // Проверяем, что данные в Профиле пользователя совпадают с теми, с которыми он зарегился
    public void checkUserDataOnProfilePage(String name, String email, String password){
        String nameOnProfile = String.format("//input[contains(@value, '%s') and preceding-sibling::label[contains(text(), 'Имя')]]", name);
        assertNotNull(driver.findElement(By.xpath(nameOnProfile)));
        String emailOnProfile = String.format("//input[contains(@value, '%s') and preceding-sibling::label[contains(text(), 'Логин')]]", email.toLowerCase());
        assertNotNull(driver.findElement(By.xpath(emailOnProfile)));
        String passwordOnProfile = String.format("//input[contains(@value, '%s') and preceding-sibling::label[contains(text(), 'Пароль')]]", "*****");
        assertNotNull(driver.findElement(By.xpath(passwordOnProfile)));
    }

    public AccountProfilePageModel(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(ACCOUNT_PROFILE_PAGE_URL);
    }
    public void waitForLoadAccountProfilePage() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(PROFILE_DATA_SECTION));

    }
    public void exitButtonClick(){
        WebElement personalAccountButton = driver.findElement(EXIT_BUTTON);
        personalAccountButton.click();
    }

    @Step("Перейти на главную страницу  по клику на кнопку Выход на странице Профиля")
    public String redirectToMainPageAfterExitClick() {
        driver.findElement(EXIT_BUTTON).isDisplayed();
        driver.findElement(EXIT_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.urlMatches(MAIN_PAGE_URL));
        return driver.getCurrentUrl();
    }

    @Step("Кликнуть по кнопке Конструктор в хэдере")
    public String redirectToMainPageOnConstructorButtonClick(){
        driver.findElement(HEADER_SECTION).isDisplayed();
        driver.findElement(CONSTRUCTOR_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.urlMatches(MAIN_PAGE_URL));
        return driver.getCurrentUrl();
    }

    @Step("Кликнуть по Логотипу в хэдере")
    public String redirectToMainPageOnLogoClick(){
        driver.findElement(HEADER_SECTION).isDisplayed();
        driver.findElement(STELLAR_LOGO).click();
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.urlMatches(MAIN_PAGE_URL));
        return driver.getCurrentUrl();
    }
}
