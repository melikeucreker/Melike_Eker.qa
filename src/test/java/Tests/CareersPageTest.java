package Tests;

import Pages.CareersPage;
import Pages.HomePage;
import Utilities.ReusableMethods;
import Utilities.ConfigReader;
import Utilities.Driver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class CareersPageTest {

    CareersPage careersPage = new CareersPage();
    HomePage homePage = new HomePage();
    ReusableMethods reusableMethods = new ReusableMethods();

    @BeforeClass
    public void setUp() {
        Driver.getDriver().get(ConfigReader.getProperty("baseUrl"));
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        reusableMethods.acceptAllCookies();
    }

    @Test(priority = 1)
    public void openCareersFromCompanyMenu() {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(homePage.companyMenu).perform();
        ReusableMethods.waitSeconds(3);
        careersPage.careersLink.click();
    }

    @Test(priority = 2, dependsOnMethods = "openCareersFromCompanyMenu")
    public void verifyCareersUrlWithMethod() {
        Assert.assertTrue(careersPage.isCareersPageOpened(),
                "Careers URL is not in the expected format: baseUrl + 'careers/'");
        System.out.println("Careers page URL has been verified.");
    }

    @Test(priority = 3, dependsOnMethods = "verifyCareersUrlWithMethod")
    public void verifyLocationsTextIsVisibleAndCorrect() {
        Assert.assertTrue(careersPage.isLocationsTextVisible(),
                "Locations text is not visible on the Careers page!");

        String expectedText = "28 offices across 6 continents, home to 1100+ Insiders";
        String actualText = careersPage.getLocationsText();

        Assert.assertEquals(actualText, expectedText,
                "Locations text does not match the expected value!");
        System.out.println("Locations text is visible and matches the expected content.");
    }

    @Test(priority = 4, dependsOnMethods = "verifyLocationsTextIsVisibleAndCorrect")
    public void verifyLifeAtInsiderTitleVisibleAndCorrect() {
        Assert.assertTrue(careersPage.isLifeAtInsiderVisible(),
                "'Life at Insider' title is not visible on the Careers page!");

        String expectedText = "Life at Insider";
        String actualText = careersPage.getLifeAtInsiderText();

        Assert.assertEquals(actualText, expectedText,
                "'Life at Insider' title text does not match the expected value!");
        System.out.println("'Life at Insider' title is visible and matches the expected text.");
    }

    @Test(priority = 5, dependsOnMethods = "verifyLifeAtInsiderTitleVisibleAndCorrect")
    public void verifySeeAllTeamsShowsQualityAssurance() {
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", careersPage.seeAllTeamsButton);
        ReusableMethods.waitSeconds(2);

        careersPage.seeAllTeamsButton.click();
        Assert.assertTrue(
                careersPage.waitForQualityAssuranceVisible(),
                "'Quality Assurance' title is not visible after clicking 'See all teams'!"
        );
        System.out.println("'Quality Assurance' title is visible after clicking 'See all teams'.");
    }

    @AfterClass
    public void tearDown() {
        Driver.quitDriver();
    }
}
