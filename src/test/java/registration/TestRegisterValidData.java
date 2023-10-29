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

public class TestRegisterValidData extends DriversSetup {
    //поля формы регистрации
    private String name;
    private String email;
    private String password;
    private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    private static final String ACCOUNT_PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";


    @Before
    public void userRegisterData(){
        name = UserTestDataGeneration.getRandomName();
        email = UserTestDataGeneration.getRandomEmail();
        password = UserTestDataGeneration.getRandomPassword();
    }

    @DisplayName("Регистрация пользователя с валидными данными - имя, имейл, пароль не менее 6 символов")
    @Description("Пользователь нажимает на кнопку 'Войти в аккаунт', далее 'Зарегистрироваться' \n" +
            "заполняет поля в форме регистрации - имя, имейл, пароль не менее 6 символов, \n" +
            "нажимает кнопку 'Зарегистрироваться', перенаправляется на Логин страницу")
    @Test
    public void shouldRegisterUserWithValidData() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице
        String currentUrl1 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(LOGIN_PAGE_URL, currentUrl1);

        LoginPageModel objLoginPage = new LoginPageModel(driver);
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.registerButtonClick(); //Кликнули по кнопке "Зарегистрироваться" на логин странице
        String currentUrl2 = driver.getCurrentUrl();
        assertEquals(REGISTRATION_PAGE_URL, currentUrl2);

        RegisterPageModel registrationPage = new RegisterPageModel(driver);
        registrationPage.open();
        registrationPage.waitForLoadRegistrationPage();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.redirectToLoginAfterRegisterClick();
        String currentUrl3 = driver.getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl3);

    }

   @After
    public void cleanUp() throws Exception{
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        user.deleteUser(accessToken);
    }

}
