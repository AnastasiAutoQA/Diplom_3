package constructor;
import before_after.DriversSetup;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import page_object_model.ConstructorSectionModel;
import page_object_model.MainPageModel;

public class TestConstructorSection extends DriversSetup {

    @DisplayName("Проверка, что работает переход к разделу Соусы")
    @Test
    public void shouldOpenSauceTabInBurgerConstructor() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        ConstructorSectionModel constructorObj = new ConstructorSectionModel(driver);
        constructorObj.waitForLoadConstructorSection();
        String isCurrentElementSelected = constructorObj.clickTabSauce().getAttribute("class");
        Assert.assertTrue("Соусы вкладка не выбрана", isCurrentElementSelected.contains(constructorObj.getSelectedTab()));
    }

    @DisplayName("Проверка, что работает переход к разделу Булки")
    @Test
    public void shouldOpenBunTabInBurgerConstructor() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        ConstructorSectionModel constructorObj = new ConstructorSectionModel(driver);
        constructorObj.waitForLoadConstructorSection();
        String isCurrentElementSelected = constructorObj.clickTabBun().getAttribute("class");
        Assert.assertTrue("Булочки вкладка не выбрана", isCurrentElementSelected.contains(constructorObj.getSelectedTab()));
    }

    @DisplayName("Проверка, что работает переход к разделу Начинки")
    @Test
    public void shouldOpenFillingTabInBurgerConstructor() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        ConstructorSectionModel constructorObj = new ConstructorSectionModel(driver);
        constructorObj.waitForLoadConstructorSection();
        String isCurrentElementSelected = constructorObj.clickTabFilling().getAttribute("class");
        Assert.assertTrue("Начинки вкладка не выбрана", isCurrentElementSelected.contains(constructorObj.getSelectedTab()));
    }

}
