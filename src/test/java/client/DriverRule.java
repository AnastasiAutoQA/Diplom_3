package client;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import java.io.File;
import java.time.Duration;
import environment.EnvConfig;

import static environment.EnvConfig.DEFAULT_TIMEOUT;

public class DriverRule extends ExternalResource {
    WebDriver driver;
    @Override
    protected void before() throws Throwable {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        if ("chrome".equals(System.getProperty("browser")))
            setUpChrome();
        else
            setUpYandex();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    private void setUpChrome() {
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(EnvConfig.CHROME_DRIVER))
                .build();
        ChromeOptions options = new ChromeOptions()
                .setBinary(EnvConfig.CHROME_BINARY);
        options.addArguments("--headless", "--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(service, options);
    }

    public void setUpYandex() {
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(EnvConfig.YANDEX_DRIVER))
                .build();
        ChromeOptions options = new ChromeOptions()
                .setBinary(EnvConfig.YANDEX_BINARY);
        options.addArguments("--headless", "--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(service, options);
    }

    @Override
    protected void after() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

}
