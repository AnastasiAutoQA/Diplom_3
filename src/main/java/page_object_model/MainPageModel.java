package page_object_model;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPageModel {
    private final WebDriver driver;
    private static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String ACCOUNT_PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
    private static final By MAIN_PAGE = By.xpath(".//div[@class = 'App_App__aOmNj']");
    private static final By HEADER_MAIN_PAGE = By.xpath(".//nav[@class='AppHeader_header__nav__g5hnF']");
    private static final By BURGER_BASKET_SECTION = By.xpath("//section[contains(@class, 'BurgerConstructor_basket')]");
    private static final By LOGIN_INTO_ACCOUNT_BUTTON = By.xpath(".//button[contains(text(), 'Войти в аккаунт')]");
    //private static final By LOGIN_INTO_ACCOUNT_BUTTON = By.xpath("//button[contains(@class, 'button_type_primary') and contains(text(), 'Войти в аккаунт')]");
    private static final By ACCOUNT_PROFILE_BUTTON = By.xpath(".//*[contains(text(), 'Личный Кабинет')]");

    public MainPageModel(WebDriver driver) {
        this.driver = driver;
    }
    //Открывает главную страницу
    public void open() {
        driver.get(MAIN_PAGE_URL);
    }

    public void waitForLoadMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(MAIN_PAGE));
    }
    public void waitForLoadHeaderMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(HEADER_MAIN_PAGE));
    }
    public void waitForLoadBasketSection() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(BURGER_BASKET_SECTION));
    }

    //Кликает кнопку "Войти в аккаунт" на главной странице
    public void loginAccountButtonClick() {
        WebElement loginAccountButton = driver.findElement(LOGIN_INTO_ACCOUNT_BUTTON);
        loginAccountButton.click();
    }
    //Кликает кнопку "Личный кабинет"
    public void accountProfileButtonClick(){
        WebElement personalAccountButton = driver.findElement(ACCOUNT_PROFILE_BUTTON);
        personalAccountButton.click();
    }

    @Step("Перейти на страницу Личного кабинета по клику на кнопку Личный кабинет на глав стр")
    public String redirectToAccProfilePageAfterAccProfileButtonClick() {
        driver.findElement(ACCOUNT_PROFILE_BUTTON).isDisplayed();
        driver.findElement(ACCOUNT_PROFILE_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.urlMatches(ACCOUNT_PROFILE_PAGE_URL));
        return driver.getCurrentUrl();
    }

}
