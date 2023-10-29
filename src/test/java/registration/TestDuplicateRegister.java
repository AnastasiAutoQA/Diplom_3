package registration;
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

public class TestDuplicateRegister extends DriversSetup{
        private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
        private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
        private static final String ACCOUNT_PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";
        private String email;
        private String password;

        @Before
        public void userRegisteredCreate(){
            String[] credentials = UserTestDataGeneration.сreateRegisteredUser();
            email = credentials[1]; // Зарегистрировали пользователя-1 и взяли его имейл
            password = credentials[2];
        }

        @DisplayName("Ошибка при регистрации с существующим логином (имейлом)")
        @Description("Система должна выдавать ошибку, если пользователь пытается зарегистрироваться с имейлом, который уже есть в системе")
        @Test
        public void shouldNotRegisterDuplicateEmail() {
            MainPageModel objMainPage = new MainPageModel(driver);
            objMainPage.open();
            objMainPage.waitForLoadBasketSection();
            objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице
            String currentUrl1 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
            assertEquals(LOGIN_PAGE_URL, currentUrl1);

            //Кликнули по кнопке "Зарегистрироваться" на логин странице
            LoginPageModel objLoginPage = new LoginPageModel(driver);
            objLoginPage.waitForLoadLoginSection();
            objLoginPage.registerButtonClick();
            String currentUrl2 = driver.getCurrentUrl();
            assertEquals(REGISTRATION_PAGE_URL, currentUrl2);

            //Пытаемся зарегистрировать пользователя-2 с таким же имейлом, как уже зарегистрированный ранее пользователь-1
            RegisterPageModel registrationPage = new RegisterPageModel(driver);
            registrationPage.open();
            registrationPage.waitForLoadRegistrationPage();
            registrationPage.fillRegistrationForm(UserTestDataGeneration.getRandomName(), email, UserTestDataGeneration.getRandomPassword());
            registrationPage.registerButtonClick();

            //Ошибка- такой пользователь уже существует
            String errorMessage = registrationPage.getDuplicatedUserErrorText();
            assertEquals("Такой пользователь уже существует", errorMessage);

        }

    @After
    public void cleanUp() throws Exception{
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        user.deleteUser(accessToken);
    }
}
