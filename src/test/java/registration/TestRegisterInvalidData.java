package registration;
import before_after.DriversSetup;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page_object_model.LoginPageModel;
import page_object_model.MainPageModel;
import page_object_model.RegisterPageModel;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestRegisterInvalidData extends DriversSetup {
    //поля формы регистрации
    private final String name;
    private final String email;
    private final String password;
    private static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    public TestRegisterInvalidData(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
    // Тестовые данные для заполнения формы регистрации валидными данными
    @Parameterized.Parameters
    public static Object[][] getUserData() {
        return new Object[][]{
                {"", "anna123@yandex.ru", "Ann123"}, //нет имени
                {"Анна Иванова", "", "anyaivanova"}, //нет имейла
                {"Анна Иванова", "anna123@yandex.ru", ""}, //нет пароля
                {"Анна Иванова", "annamailru", "Ann123"}, //невалидный формат имейла
                {"Анна Иванова", "anna@", "Ann123"},
                {"Анна Иванова", "anna@mailru", "Ann123"},
                {"Анна Иванова", "annamail.ru", "Ann123"},
                {"Анна Иванова", "anna@mail.ru", "12345"}, //пароль меньше 6 символов
        };
    }


    @DisplayName("Регистрация пользователя с Невалидными данными")
    @Description("Пользователь не может зарегистрироваться, если:" +
            "- не введено Имя," +
            "- не введен Имейл," +
            "- не введен Пароль," +
            "- введен Имейл в неверном формате," +
            "- введен пароль менее 6 символов")
    @Test
    public void shouldNotRegisterUserWithInvalidData() {
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
        registrationPage.registerButtonClick();
        String currentUrl3 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(REGISTRATION_PAGE_URL, currentUrl3); //Пользователь остался на странице регистрации
    }
}

