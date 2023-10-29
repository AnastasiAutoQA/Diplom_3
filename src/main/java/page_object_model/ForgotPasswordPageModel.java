package page_object_model;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ForgotPasswordPageModel {
    private final WebDriver driver;
    private static final String FORGOT_PASSWORD_PAGE_URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    private static final By FORGOT_PAGE = By.xpath(".//main[@class = 'App_componentContainer__2JC2W']");
    private static final By LOGIN_BUTTON_FORGOT_PAGE = By.xpath(".//a[@class='Auth_link__1fOlj' and contains(text(), 'Войти')]");

    public ForgotPasswordPageModel(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(FORGOT_PASSWORD_PAGE_URL);
    }
    public void waitForLoadForgotPassPage() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(FORGOT_PAGE));

    }
    public void loginOnRegPageButtonClick(){
        WebElement personalAccountButton = driver.findElement(LOGIN_BUTTON_FORGOT_PAGE);
        personalAccountButton.click();
    }
}
