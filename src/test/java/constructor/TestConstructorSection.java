package constructor;
import client.DriverRule;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import model.ConstructorSectionModel;
import model.MainPageModel;

public class TestConstructorSection  {
    @Rule
    public DriverRule driverRule = new DriverRule();

    @DisplayName("Проверка, что работает переход к разделу Соусы")
    @Test
    public void shouldOpenSauceTabInBurgerConstructor() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        ConstructorSectionModel constructorObj = new ConstructorSectionModel(driverRule.getDriver());
        constructorObj.waitForLoadConstructorSection();
        String isCurrentElementSelected = constructorObj.clickTabSauce().getAttribute("class");
        Assert.assertTrue("Соусы вкладка не выбрана", isCurrentElementSelected.contains(constructorObj.getSelectedTab()));
    }

    @DisplayName("Проверка, что работает переход к разделу Булки")
    @Test
    public void shouldOpenBunTabInBurgerConstructor() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        ConstructorSectionModel constructorObj = new ConstructorSectionModel(driverRule.getDriver());
        constructorObj.waitForLoadConstructorSection();
        String isCurrentElementSelected = constructorObj.clickTabBun().getAttribute("class");
        Assert.assertTrue("Булочки вкладка не выбрана", isCurrentElementSelected.contains(constructorObj.getSelectedTab()));
    }

    @DisplayName("Проверка, что работает переход к разделу Начинки")
    @Test
    public void shouldOpenFillingTabInBurgerConstructor() {
        MainPageModel objMainPage = new MainPageModel(driverRule.getDriver());
        objMainPage.open();
        ConstructorSectionModel constructorObj = new ConstructorSectionModel(driverRule.getDriver());
        constructorObj.waitForLoadConstructorSection();
        String isCurrentElementSelected = constructorObj.clickTabFilling().getAttribute("class");
        Assert.assertTrue("Начинки вкладка не выбрана", isCurrentElementSelected.contains(constructorObj.getSelectedTab()));
    }

}
