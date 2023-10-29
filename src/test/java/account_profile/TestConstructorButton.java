package account_profile;
import before_after.APIConfigForUserData;
import before_after.DriversSetup;
import before_after.UserTestDataGeneration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import page_object_model.AccountProfilePageModel;
import page_object_model.LoginPageModel;
import page_object_model.MainPageModel;
import static org.junit.Assert.assertEquals;

public class TestConstructorButton extends DriversSetup {
    private String name;
    private String email;
    private String password;
    private static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/";
    private static final String LOGIN_PAGE_URL = "https://stellarburgers.nomoreparties.site/login";
    private static final String ACCOUNT_PROFILE_PAGE_URL = "https://stellarburgers.nomoreparties.site/account/profile";

    @Before
    public void userRegisteredCreate(){
        String[] credentials = UserTestDataGeneration.сreateRegisteredUser();
        name = credentials[0];
        email = credentials[1];
        password = credentials[2];
    }

    @Test
    public void shouldRedirectToMainPageOnConstructorClick() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.accountProfileButtonClick(); //Кликнули по кнопке "Личный кабинет"
        String currentUrl1 = driver.getCurrentUrl(); // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
        assertEquals(LOGIN_PAGE_URL, currentUrl1);

        //Пользователь логинится
        LoginPageModel objLoginPage = new LoginPageModel(driver);
        objLoginPage.open();
        objLoginPage.waitForLoadLoginSection();
        objLoginPage.fillLoginForm(email, password);
        objLoginPage.loginButtonClick();

        /// Залогиненный пользователь кликает на кнопку "Личный кабинет" на главной странице и переходит в свой Профиль
        objMainPage.waitForLoadHeaderMainPage();
        objMainPage.redirectToAccProfilePageAfterAccProfileButtonClick();
        String currentUrl2 = driver.getCurrentUrl();
        assertEquals(ACCOUNT_PROFILE_PAGE_URL, currentUrl2);

        // Нажимает кнопку "Конструктор" в хэдере
        AccountProfilePageModel accProfilePage = new AccountProfilePageModel(driver);
        accProfilePage.waitForLoadAccountProfilePage();
        accProfilePage.redirectToMainPageOnConstructorButtonClick();
        String currentUrl3 = driver.getCurrentUrl();
        assertEquals(MAIN_PAGE_URL, currentUrl3);
    }

    @After
    public void cleanUp() {
        APIConfigForUserData user = new APIConfigForUserData();
        String accessToken = user.loginAndExtractAccessToken(email, password);
        user.deleteUser(accessToken);
    }
}
