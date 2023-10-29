package login;
import before_after.APIConfigForUserData;
import before_after.DriversSetup;
import before_after.UserTestDataGeneration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page_object_model.ForgotPasswordPageModel;
import page_object_model.LoginPageModel;
import page_object_model.MainPageModel;
import static org.junit.Assert.assertEquals;

public class TestLoginOnForgotPasswordPage extends DriversSetup {
    private String name;
    private String email;
    private String password;
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    private static final String FORGOT_PASSWORD_PAGE_URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    @Before
    public void userRegisteredCreate(){
        String[] credentials = UserTestDataGeneration.сreateRegisteredUser();
        name = credentials[0];
        email = credentials[1];
        password = credentials[2];
    }

    @DisplayName("Вход через кнопку 'Войти' в форме восстановления пароля")
    @Description("Пользователь нажимает на кнопку 'Войти' в форме восстановления пароля, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginFromForgotPasswordPage() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице
        String currentUrl1 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl1, LOGIN_PAGE_URL);

        LoginPageModel objLoginPage = new LoginPageModel(driver);
        objLoginPage.waitForLoadLoginPage();
        objLoginPage.restorePasswordButtonBClick(); //Кликнули по кнопке "Восстановить пароль" на логин странице
        String currentUrl2 = driver.getCurrentUrl();
        assertEquals(currentUrl2, FORGOT_PASSWORD_PAGE_URL);

        ForgotPasswordPageModel forgotPasswordPage = new ForgotPasswordPageModel(driver);
        forgotPasswordPage.open();
        forgotPasswordPage.waitForLoadForgotPassPage();
        forgotPasswordPage.loginOnRegPageButtonClick();
        String currentUrl3 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl3, LOGIN_PAGE_URL);

        LoginPageModel objLoginPage2 = new LoginPageModel(driver);
        objLoginPage2.open();
        objLoginPage2.waitForLoadLoginSection();
        objLoginPage2.fillLoginForm(email, password);
        objLoginPage2.loginButtonClick();

    }
    @After
    public void cleanUp() {
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        user.deleteUser(accessToken);
    }
}
