package client;
import org.apache.commons.lang3.RandomStringUtils;
import model.LoginPageModel;
import model.MainPageModel;
import model.RegisterPageModel;
import org.junit.Rule;

import static org.junit.Assert.assertEquals;

public class UserTestDataGeneration  {
    @Rule
    public DriverRule driverRule = new DriverRule();
    public static final String name = RandomStringUtils.randomAlphanumeric(8);
    public static final String email = RandomStringUtils.randomAlphanumeric(6) + "@"
            + RandomStringUtils.randomAlphanumeric(4) + "."
            + RandomStringUtils.randomAlphanumeric(2);
    public static final String password = RandomStringUtils.randomAlphanumeric(6);
    public static final String passwordInvalid = RandomStringUtils.randomAlphanumeric(5);
    public static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";
    public static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";

    public String[] сreateRegisteredUser(){
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        objMainPage.waitForLoadBasketSection();
        objMainPage.loginAccountButtonClick(); //Кликнули по кнопке "Войти в аккаунт" на главной странице
        String currentUrl1 = driverRule.getDriver().getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(currentUrl1, LOGIN_PAGE_URL);

        LoginPageModel objLoginPage = new LoginPageModel(driverRule.getDriver());
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.registerButtonClick(); //Кликнули по кнопке "Зарегистрироваться" на логин странице
        String currentUrl2 = driverRule.getDriver().getCurrentUrl();
        assertEquals(currentUrl2, REGISTRATION_PAGE_URL);

        RegisterPageModel registrationPage = new RegisterPageModel(driverRule.getDriver());
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

