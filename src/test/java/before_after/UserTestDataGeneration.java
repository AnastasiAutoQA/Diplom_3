package before_after;
import org.apache.commons.lang3.RandomStringUtils;
import page_object_model.LoginPageModel;
import page_object_model.MainPageModel;
import page_object_model.RegisterPageModel;
import static org.junit.Assert.assertEquals;

public class UserTestDataGeneration extends DriversSetup {
    public static final String name = RandomStringUtils.randomAlphanumeric(8);
    public static final String email = RandomStringUtils.randomAlphanumeric(6) + "@"
            + RandomStringUtils.randomAlphanumeric(4) + "."
            + RandomStringUtils.randomAlphanumeric(2);
    public static final String password = RandomStringUtils.randomAlphanumeric(6);
    public static final String passwordInvalid = RandomStringUtils.randomAlphanumeric(5);
    public static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    public static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    public static String[] сreateRegisteredUser(){
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
        registrationPage.fillRegistrationForm(name, email, password);
        registrationPage.registerButtonClick();
        //registrationPage.redirectToMainPage();
        return new String[]{name, email, password};

    }
    public static String getRandomName(){ return name; }
    public static String getRandomEmail(){ return email; }
    public static String getRandomPassword(){
        return password;
    }
    public static String getInvalidPassword(){
        return passwordInvalid;
    }


    @Override
    public String toString() {
        return "RegisteredUserCreate{" +
                "email=" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

