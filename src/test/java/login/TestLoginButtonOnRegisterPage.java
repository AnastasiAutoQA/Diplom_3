package login;
import before_after.APIConfigForUserData;
import before_after.DriversSetup;
import before_after.UserTestDataGeneration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page_object_model.LoginPageModel;
import page_object_model.MainPageModel;
import page_object_model.RegisterPageModel;
import static org.junit.Assert.assertEquals;

public class TestLoginButtonOnRegisterPage extends DriversSetup {
    private String name;
    private String email;
    private String password;
    private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    @Before
    public void userRegisteredCreate(){
        String[] credentials = UserTestDataGeneration.сreateRegisteredUser();
        name = credentials[0];
        email = credentials[1];
        password = credentials[2];
    }

    @DisplayName("Вход через кнопку 'Войти' в форме регистрации")
    @Description("Пользователь нажимает на кнопку 'Войти' в форме регистрации, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginFromRegistrationPage() {

        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице
        String currentUrl1 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl1, LOGIN_PAGE_URL);

        LoginPageModel objLoginPage = new LoginPageModel(driver);
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.registerButtonClick(); //Кликнули по кнопке "Зарегистрироваться" на логин странице
        String currentUrl2 = driver.getCurrentUrl();
        assertEquals(currentUrl2, REGISTRATION_PAGE_URL);

        RegisterPageModel registrationPage = new RegisterPageModel(driver);
        registrationPage.open();
        registrationPage.waitForLoadRegistrationPage();
        registrationPage.loginButtonRegPageClick();
        String currentUrl3 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl3, LOGIN_PAGE_URL);

        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();

    }
    @After
    public void cleanUp() {
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        user.deleteUser(accessToken);
    }
}
