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
import static org.junit.Assert.assertEquals;

public class TestLoginButtonOnMainPage extends DriversSetup {
    private String name;
    private String email;
    private String password;
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    @Before
    public void userRegisteredCreate(){
        String[] credentials = UserTestDataGeneration.сreateRegisteredUser();
        name = credentials[0];
        email = credentials[1];
        password = credentials[2];
    }

    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной странице")
    @Description("Пользователь нажимает на кнопку 'Войти в аккаунт' на главной странице, вводит логин и пароль и входит в ЛК")
    @Test
    public void shouldLoginUserFromMainPage() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт"
        // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        String currentUrl = driver.getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);

        LoginPageModel objLoginPage = new LoginPageModel(driver);
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

