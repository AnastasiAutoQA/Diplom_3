package registration;
import before_after.DriversSetup;
import before_after.UserTestDataGeneration;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import page_object_model.LoginPageModel;
import page_object_model.MainPageModel;
import page_object_model.RegisterPageModel;
import static org.junit.Assert.assertEquals;

public class TestRegisterWrongPasswordError extends DriversSetup {
    private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    public TestRegisterWrongPasswordError(){ }

    @DisplayName("Ошибка при регистрации пользователя, если пароль менее 6 символов")
    @Description("Система выдает ошибку, если введен пароль менее 6 символов")
    @Test
    public void shouldReturnIncorrectPasswordError(){
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
        registrationPage.fillRegistrationForm(UserTestDataGeneration.getRandomName(),
                UserTestDataGeneration.getRandomEmail(), UserTestDataGeneration.getInvalidPassword());
        registrationPage.registerButtonClick();
        String wrongPasswordMessage = registrationPage.getIncorrectPasswordErrorText();
        assertEquals("Некорректный пароль", wrongPasswordMessage); //Появилась ошибка
        String currentUrl3 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(REGISTRATION_PAGE_URL, currentUrl3); //Пользователь остался на странице регистрации

    }
}
