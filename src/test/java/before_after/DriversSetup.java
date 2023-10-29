package before_after;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class DriversSetup {

    protected static WebDriver driver;

    @Before
    public void setUpChrome() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:/WebDriver/bin/chromedriver.exe"))
                .build();
        ChromeOptions options = new ChromeOptions()
                .setBinary("C:/Program Files/Google/Chrome/Application/chrome.exe");
        options.addArguments("--headless", "--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(service, options);
    }

    /*public void setUpYandex() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:/Projects_AUTO_QA/chromedriver116-win64/chromedriver.exe"))
                .build();
        ChromeOptions options = new ChromeOptions()
                .setBinary("C:/Users/Lenovo/AppData/Local/Yandex/YandexBrowser/Application/browser.exe");
        options.addArguments("--headless", "--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(service, options);
    }*/

    /*public void setUpFirefox() {
        System.setProperty("webdriver.http.factory", "jdk-http-client");
        var service = new GeckoDriverService.Builder()
                .usingDriverExecutable(new File("C:/WebDriverMozilla/bin/geckodriver.exe"))
                .build();
        var options = new FirefoxOptions()
                .setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        driver = new FirefoxDriver(service, options);
    }*/

    /*@Before
    public void startBrowser() throws InterruptedException {
        // Стартуем Chrome
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        //chromeOptions.addArguments("--headless", "--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);

        // Стартуем FireFox
        System.setProperty("webdriver.gecko.driver", "C:\\WebDriverMozilla\\bin\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
*/

    @After
    public void tearDown () {
        driver.quit();
    }
}
