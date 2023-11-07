package registration;
import client.DriverRule;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import model.RegisterPageModel;
import static model.RegisterPageModel.REGISTRATION_PAGE_URL;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestRegisterInvalidData {
    @Rule
    public DriverRule driverRule = new DriverRule();
    //поля формы регистрации
    private final String name;
    private final String email;
    private final String password;
    private String currentUrl;

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
        RegisterPageModel registrationPage = new RegisterPageModel(driverRule.getDriver());
        registrationPage.open();
        registrationPage.waitForLoadRegistrationPage();
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.registerButtonClick();
        currentUrl = driverRule.getDriver().getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(REGISTRATION_PAGE_URL, currentUrl); //Пользователь остался на странице регистрации
    }
}

