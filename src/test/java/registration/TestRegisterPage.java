package registration;
import client.APIConfigForUserData;
import client.DriverRule;
import client.UserTestDataGeneration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import model.LoginPageModel;
import model.MainPageModel;
import model.RegisterPageModel;
import static model.LoginPageModel.LOGIN_PAGE_URL;
import static model.RegisterPageModel.REGISTRATION_PAGE_URL;
import static org.junit.Assert.assertEquals;
public class TestRegisterPage  {
    @Rule
    public DriverRule driverRule = new DriverRule();
    //поля формы регистрации
    private String name;
    private String email;
    private String password;
    private String currentUrl;

    @Before
    public void userRegisterData(){
        name = UserTestDataGeneration.getRandomName();
        email = UserTestDataGeneration.getRandomEmail();
        password = UserTestDataGeneration.getRandomPassword();
    }

    @DisplayName("Регистрация пользователя с валидными данными - имя, имейл, пароль не менее 6 символов")
    @Description("Пользователь нажимает на кнопку 'Войти в аккаунт', далее 'Зарегистрироваться' " +
            "заполняет поля в форме регистрации - имя, имейл, пароль не менее 6 символов, " +
            "нажимает кнопку 'Зарегистрироваться', перенаправляется на Логин страницу")
    @Test
    public void shouldRegisterUserWithValidData() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице

        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.registerButtonClick(); //Кликнули по кнопке "Зарегистрироваться" на логин странице
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(REGISTRATION_PAGE_URL, currentUrl);

        RegisterPageModel registrationPage = new RegisterPageModel(driverRule.getDriver());
        registrationPage.waitForLoadRegistrationPage();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.redirectToLoginAfterRegisterClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);
    }

    @DisplayName("Ошибка при регистрации с существующим логином (имейлом)")
    @Description("Система должна выдавать ошибку, если пользователь пытается зарегистрироваться с имейлом, который уже есть в системе")
    @Test
    public void shouldNotRegisterDuplicateEmail() {
        //Регистрируем пользователя-1
        RegisterPageModel registrationPage1 = new RegisterPageModel(driverRule.getDriver());
        registrationPage1.open();
        registrationPage1.waitForLoadRegistrationPage();
        registrationPage1.fillRegistrationForm(name, email, password);
        registrationPage1.redirectToLoginAfterRegisterClick();
        currentUrl = driverRule.getDriver().getCurrentUrl();
        assertEquals(LOGIN_PAGE_URL, currentUrl);

        //Пытаемся зарегистрировать пользователя-2 с таким же имейлом, как уже зарегистрированный ранее пользователь-1
        RegisterPageModel registrationPage2 = new RegisterPageModel(driverRule.getDriver());
        registrationPage2.open();
        registrationPage2.waitForLoadRegistrationPage();
        registrationPage2.fillRegistrationForm(UserTestDataGeneration.getRandomName(), email, UserTestDataGeneration.getRandomPassword());
        registrationPage2.registerButtonClick();

        //Ошибка- такой пользователь уже существует
        String errorMessage = registrationPage2.getDuplicatedUserErrorText();
        assertEquals("Такой пользователь уже существует", errorMessage);

    }

    @DisplayName("Ошибка при регистрации пользователя, если пароль менее 6 символов")
    @Description("Система выдает ошибку, если введен пароль менее 6 символов")
    @Test
    public void shouldReturnIncorrectPasswordError() {
        // Пытаемся зарегистрировать пользователя с паролем менее 6 символов
        RegisterPageModel registrationPage = new RegisterPageModel(driverRule.getDriver());
        registrationPage.open();
        registrationPage.waitForLoadRegistrationPage();
        registrationPage.fillRegistrationForm(UserTestDataGeneration.getRandomName(),
                UserTestDataGeneration.getRandomEmail(), UserTestDataGeneration.getInvalidPassword());
        registrationPage.registerButtonClick();
        String wrongPasswordMessage = registrationPage.getIncorrectPasswordErrorText();
        assertEquals("Некорректный пароль", wrongPasswordMessage); //Появилась ошибка
        currentUrl = driverRule.getDriver().getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(REGISTRATION_PAGE_URL, currentUrl); //Пользователь остался на странице регистрации
    }
    @After
    public void clearDown() {
        try {
            APIConfigForUserData user = new APIConfigForUserData();
            String accessToken = user.loginAndExtractAccessToken(email, password);
            user.deleteUser(accessToken);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            System.out.println("No user found to delete");
        }
    }
}
