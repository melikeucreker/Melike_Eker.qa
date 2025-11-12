package Tests;

import Pages.CareersPage;
import Pages.HomePage;
import Pages.QualityAssurance;
import Utilities.ConfigReader;
import Utilities.ReusableMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import Utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class QualityAssurancePageTest {
    CareersPage careersPage = new CareersPage();
    HomePage homePage = new HomePage();
    QualityAssurance qualityAssurance = new QualityAssurance();
    ReusableMethods reusableMethods = new ReusableMethods();

    @BeforeClass
    public void setUp() {
        Driver.getDriver().get(ConfigReader.getProperty("baseUrl") + ConfigReader.getProperty("QualityAssurancePage"));
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        reusableMethods.acceptAllCookies();
    }

    @Test(priority = 1)
    public void openQualityAssurancePage() {
        Assert.assertTrue(
                Driver.getDriver().getCurrentUrl().contains("careers/quality-assurance"),
                "Quality Assurance Careers page URL is incorrect!"
        );
        System.out.println("Quality Assurance Careers page successfully opened.");
    }

    @Test(priority = 2)
    public void verifyFiltersContainQAAndIstanbul() {

        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", qualityAssurance.seeAllQAJobsButton);
        qualityAssurance.seeAllQAJobsButton.click();

        Assert.assertTrue(
                Driver.getDriver().getCurrentUrl().contains("open-positions"),
                "'See all QA jobs' link did not navigate to Open Positions page!"
        );
        ReusableMethods.waitSeconds(10);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));


        wait.until(ExpectedConditions.visibilityOf(qualityAssurance.locationFilter));
        wait.until(ExpectedConditions.elementToBeClickable(qualityAssurance.locationFilter)).click();

        wait.until(ExpectedConditions.visibilityOf(qualityAssurance.istanbulTurkeyOption));
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", qualityAssurance.istanbulTurkeyOption);
        qualityAssurance.istanbulTurkeyOption.click();
        ReusableMethods.waitSeconds(5);

        List<WebElement> positionList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//span[@class='position-department text-large font-weight-600 text-primary']")
        ));

        List<WebElement> locationList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("//div[@class='position-location text-large']")
        ));

        for (int i = 0; i < positionList.size(); i++) {
            String positionText = positionList.get(i).getText().trim();
            String locationText = locationList.get(i).getText().trim();

            Assert.assertTrue(
                    positionText.contains("Quality Assurance"),
                    "Job position does not contain 'Quality Assurance' text!"
            );

            Assert.assertTrue(
                    locationText.contains("Istanbul, Turkiye"),
                    "Job location does not contain 'Istanbul, Turkiye' text!"
            );
        }


    }

    @Test(priority = 3)
    public void verifyViewRoleButtonRedirectsToLever() {
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();


        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        ReusableMethods.waitSeconds(3);


        WebElement viewRoleButton = Driver.getDriver().findElement(
                By.xpath("(//a[contains(@class,'btn btn-navy') and contains(text(),'View Role')])[1]")
        );


        js.executeScript("arguments[0].scrollIntoView({block:'center'});", viewRoleButton);
        ReusableMethods.waitSeconds(2);


        viewRoleButton.click();


        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
        }

        String currentUrl = Driver.getDriver().getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("jobs.lever.co"),
                "Clicking 'View Role' did not redirect to Lever Application form!"
        );

    }
}