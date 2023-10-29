package page_object_model;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ConstructorSectionModel {
    private final WebDriver driver;
    private static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final By CONSTRUCTOR_SECTION = By.xpath(".//section[@class = 'BurgerIngredients_ingredients__1N8v2']");
    //private static final By CONSTRUCTOR_TABS = By.xpath("//div[contains(@style, 'display: flex;') and preceding-sibling::h1[contains(text(), 'Соберите бургер')]]");
    private static final By CONSTRUCTOR_TABS = By.xpath("//div[contains(@style, 'display: flex;')]");
    private final By BUN_PARENT = By.xpath("//div[span[text() = 'Булки']]");
    private final By BUN = By.xpath("//span[text() = 'Булки']");
    private final By SAUCE_PARENT = By.xpath("//div[span[text() = 'Соусы']]");
    private final By SAUCE = By.xpath("//span[text() = 'Соусы']");
    private final By FILLING = By.xpath("//span[text() = 'Начинки']");
    private final By FILLING_PARENT = By.xpath("//div[span[text() = 'Начинки']]");
    private final By ORDER_BUTTON = By.xpath("//button[text() = 'Оформить заказ']");
    private final String isTabSelected = "tab_tab_type_current";

    public ConstructorSectionModel(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForLoadConstructorSection() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(CONSTRUCTOR_SECTION));
    }

    public String getSelectedTab() {
        return isTabSelected;
    }

    @Step("Выбор в конструкторе раздела Соусы")
    public WebElement clickTabSauce() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(SAUCE));
        driver.findElement(FILLING).click();
        driver.findElement(SAUCE).click();
        return driver.findElement(SAUCE_PARENT);
    }
    @Step("Выбор в конструкторе раздела с булочками")
    public WebElement clickTabBun() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(BUN));
        driver.findElement(SAUCE).click();
        driver.findElement(BUN).click();
        return driver.findElement(BUN_PARENT);
    }

    @Step("Выбор в конструкторе раздела с начинками")
    public WebElement clickTabFilling() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(FILLING));
        driver.findElement(SAUCE).click();
        driver.findElement(FILLING).click();
        return driver.findElement(FILLING_PARENT);
    }
    @Step("Найти кнопку оформления заказа")
    public WebElement locateOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(ORDER_BUTTON));
        return driver.findElement(ORDER_BUTTON);
    }

}
