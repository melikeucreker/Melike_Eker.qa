package Tests;

import Pages.HomePage;
import Utilities.ConfigReader;
import Utilities.Driver;
import Utilities.ReusableMethods;
import org.testng.annotations.*;


public class HomePageTest {
    HomePage homePage;

    @BeforeMethod
    public void setUp() {
        Driver.getDriver().get(ConfigReader.getProperty("baseUrl"));
        homePage = new HomePage();
        ReusableMethods.waitSeconds(1);
    }

    @Test
    public void verifyHomePageOpened() {
        homePage.verifyHomePageIsOpened(ConfigReader.getProperty("baseUrl"));
        Driver.quitDriver();
    }

}
