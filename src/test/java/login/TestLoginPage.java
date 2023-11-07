package login;
import client.DriverRule;
import client.UserTestDataGeneration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static java.net.HttpURLConnection.HTTP_OK;
import static model.ForgotPasswordPageModel.FORGOT_PASSWORD_PAGE_URL;
import static model.LoginPageModel.LOGIN_PAGE_URL;
import static model.RegisterPageModel.REGISTRATION_PAGE_URL;
import static org.junit.Assert.assertEquals;
import client.APIConfigForUserData;
import model.MainPageModel;
import model.LoginPageModel;
import model.ForgotPasswordPageModel;
import model.RegisterPageModel;
public class TestLoginPage {
    @Rule
    public DriverRule driverRule = new DriverRule();
    private String name;
    private String email;
    private String password;
    private String currentUrl;

    @Before
    public void registeredUserCreation(){
        APIConfigForUserData userReg = new APIConfigForUserData();
        name = UserTestDataGeneration.getRandomName();
        email = UserTestDataGeneration.getRandomEmail();
        password = UserTestDataGeneration.getRandomPassword();
        ValidatableResponse userRegResponse = userReg.registerNewUserResponse(name, email, password);
        assertEquals("The status code is invalid", HTTP_OK, userRegResponse.extract().statusCode());
    }

    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной странице")
    @Description("Пользователь нажимает на кнопку 'Войти в аккаунт' на главной странице, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginUserFromMainPage() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт"
        // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);
        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
    }
    @DisplayName("Вход через кнопку 'Личный кабинет' на главной странице")
    @Description("Пользователь нажимает на кнопку 'Личный кабинет' на главной странице, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginPersonalAccountFromMainPage() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick(); //Кликнули по кнопке "Личный кабинет"
        // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);
        //Пользователь логинится
        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
    }

    @DisplayName("Вход через кнопку 'Войти' в форме регистрации")
    @Description("Пользователь нажимает на кнопку 'Войти' в форме регистрации, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginFromRegistrationPage() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице

        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.registerButtonClick(); //Кликнули по кнопке "Зарегистрироваться" на логин странице
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(currentUrl, REGISTRATION_PAGE_URL);

        RegisterPageModel registrationPage = new RegisterPageModel(driverRule.getDriver());
        registrationPage.waitForLoadRegistrationPage();
        registrationPage.loginButtonRegPageClick();
        currentUrl = driverRule.getDriver().getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl, LOGIN_PAGE_URL);

        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();
    }

    @DisplayName("Вход через кнопку 'Войти' в форме Восстановления пароля")
    @Description("Пользователь нажимает на кнопку 'Войти' в форме восстановления пароля, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginFromForgotPasswordPage() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице
        String currentUrl1 = driverRule.getDriver().getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl1, LOGIN_PAGE_URL);

        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginPage();
        objLoginPage.restorePasswordButtonBClick(); //Кликнули по кнопке "Восстановить пароль" на логин странице
        String currentUrl2 = driverRule.getDriver().getCurrentUrl();
        assertEquals(currentUrl2, FORGOT_PASSWORD_PAGE_URL);

        ForgotPasswordPageModel forgotPasswordPage = new ForgotPasswordPageModel(driverRule.getDriver());
        forgotPasswordPage.waitForLoadForgotPassPage();
        forgotPasswordPage.loginOnRegPageButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl, LOGIN_PAGE_URL);

        LoginPageModel objLoginPage2 = new LoginPageModel(driverRule.getDriver());
        objLoginPage2.open();
        objLoginPage2.waitForLoadLoginSection();
        objLoginPage2.fillLoginForm(email, password);
        objLoginPage2.loginButtonClick();
    }

    @After
    public void checkUserDataOnProfileAndCleanUp() {
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        // Залогиненный пользователь кликает на кнопку Личный кабинет на главной странице и переходит в свой Профиль
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.redirectToAccProfilePageAfterAccProfileButtonClick();
        //Проверяем, что данные в Профиле пользователя совпадают с теми, с которыми он зарегился
        AccountProfilePageModel accProfilePage = new AccountProfilePageModel(driverRule.getDriver());
        accProfilePage.waitForLoadAccountProfilePage();
        accProfilePage.checkUserDataOnProfilePage(name, email, password);
        //Удаляем пользователя
        user.deleteUser(accessToken);
    }

}
