package profile;
import client.APIConfigForUserData;
import client.DriverRule;
import client.UserTestDataGeneration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import model.AccountProfilePageModel;
import model.LoginPageModel;
import model.MainPageModel;

import static java.net.HttpURLConnection.HTTP_OK;
import static model.AccountProfilePageModel.ACCOUNT_PROFILE_PAGE_URL;
import static model.LoginPageModel.LOGIN_PAGE_URL;
import static model.MainPageModel.MAIN_PAGE_URL;
import static org.junit.Assert.assertEquals;
public class TestAccountProfilePage {
    @Rule
    public DriverRule driverRule = new DriverRule();
    private String name;
    private String email;
    private String password;
    private String currentUrl;

    @Before
    public void userRegisteredCreate(){
        APIConfigForUserData userReg = new APIConfigForUserData();
        name = UserTestDataGeneration.getRandomName();
        email = UserTestDataGeneration.getRandomEmail();
        password = UserTestDataGeneration.getRandomPassword();
        ValidatableResponse userRegResponse = userReg.registerNewUserResponse(name, email, password);
        assertEquals("The status code is invalid", HTTP_OK, userRegResponse.extract().statusCode());
    }

    @DisplayName("Переход по кнопке Конструктор на главную страницу из Личного кабинета")
    @Description("Находясь на странице Личного кабинета пользователь кликает на Конструктор и переходит на главную страницу")
    @Test
    public void shouldRedirectToMainPageOnConstructorClick() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick(); //Кликнули по кнопке "Личный кабинет"
        //Пользователь логинится
        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
        /// Залогиненный пользователь кликает на кнопку "Личный кабинет" на главной странице и переходит в свой Профиль
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.redirectToAccProfilePageAfterAccProfileButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(ACCOUNT_PROFILE_PAGE_URL, currentUrl);
        // Нажимает кнопку "Конструктор" в хэдере
        AccountProfilePageModel accProfilePage = new AccountProfilePageModel(driverRule.getDriver());
        accProfilePage.waitForLoadAccountProfilePage();
        accProfilePage.redirectToMainPageOnConstructorButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(MAIN_PAGE_URL, currentUrl);
    }
    @DisplayName("Переход по кнопке Выход из Личного кабинета на главную страницу")
    @Description("Находясь на странице Личного кабинета пользователь кликает на Выход и переходит на главную страницу." +
            "Если пользователь нажимает на кнопку Войти на главной странице, должна открываться страница Логина" +
            "с пустыми полями для ввода имейла и пароля.")
    @Test
    public void shouldExitAccountProfile() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick(); //Кликнули по кнопке "Личный кабинет"
        //Пользователь логинится
        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
        /// Залогиненный пользователь кликает на кнопку "Личный кабинет" на главной странице и переходит в свой Профиль
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.redirectToAccProfilePageAfterAccProfileButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(ACCOUNT_PROFILE_PAGE_URL, currentUrl);
        //Пользователь нажимает кнопку "Выход"
        AccountProfilePageModel accProfilePage = new AccountProfilePageModel(driverRule.getDriver());
        accProfilePage.waitForLoadAccountProfilePage();
        accProfilePage.redirectToMainPageAfterExitClick();
        //Нажимаем кнопку "Личный кабинет" на главной странице- проверяем, что открывается Логин страница
        //с пустыми полями для ввода имейла и пароля, а не страница Профиля c данными пользователя
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);
        assertEquals("", objLoginPage.getEmail());
        assertEquals("", objLoginPage.getPassword());
    }
    @DisplayName("Переход по клику на Логотип на главную страницу из Личного кабинета")
    @Description("Находясь на странице Личного кабинета пользователь кликает на Логотип и переходит на главную страницу")
    @Test
    public void shouldRedirectToMainPageOnLogoClick() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick(); //Кликнули по кнопке "Личный кабинет"
        //Пользователь логинится
        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
        /// Залогиненный пользователь кликает на кнопку "Личный кабинет" на главной странице и переходит в свой Профиль
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.redirectToAccProfilePageAfterAccProfileButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(ACCOUNT_PROFILE_PAGE_URL, currentUrl);
        // Нажимает на Логотип в хэдере
        AccountProfilePageModel accProfilePage = new AccountProfilePageModel(driverRule.getDriver());
        accProfilePage.waitForLoadAccountProfilePage();
        accProfilePage.redirectToMainPageOnLogoClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(MAIN_PAGE_URL, currentUrl);
    }

    @DisplayName("Переход по клику на Личный кабинет с главной страницы на страницу Личного кабинета")
    @Description("Находясь на главной странице пользователь кликает на Личный кабинет и переходит на страницу своего профиля." +
            "Данные пользователя - имя, имейл - должны совпадать с теми, с каким он зарегистрировался." +
            "Пароль должен быть скрыт *****")
    @Test
    public void shouldTransferToAccountProfile(){
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick(); //Кликнули по кнопке "Личный кабинет"
        //Пользователь логинится
        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
        /// Залогиненный пользователь кликает на кнопку "Личный кабинет" на главной странице и переходит в свой Профиль
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.redirectToAccProfilePageAfterAccProfileButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(ACCOUNT_PROFILE_PAGE_URL, currentUrl);
        //Проверяем, что данные в Профиле пользователя совпадают с теми, с которыми он зарегился
        AccountProfilePageModel accProfilePage = new AccountProfilePageModel(driverRule.getDriver());
        accProfilePage.waitForLoadAccountProfilePage();
        accProfilePage.checkUserDataOnProfilePage(name, email, password);
    }

    @After
    public void cleanUp() {
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        user.deleteUser(accessToken);
    }

}
