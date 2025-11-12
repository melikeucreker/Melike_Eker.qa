package Pages;

import Utilities.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;
import Utilities.Driver;

import java.time.Duration;

public class HomePage {
    WebDriver driver;

    public HomePage() {
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "body > nav:nth-child(6) > div:nth-child(2) > div:nth-child(3) > ul:nth-child(1) >" +
            " li:nth-child(6) > a:nth-child(1)")
    public WebElement companyMenu;


    @FindBy(xpath = "//a[@id='wt-cli-accept-all-btn']")
    public WebElement acceptAllCookiesButton;

    public void verifyHomePageIsOpened(String expectedUrlPart) {
        String currentUrl = driver.getCurrentUrl();


        System.out.println("Current URL: " + currentUrl);
        System.out.println("Expected to contain: " + expectedUrlPart);

        // Assertion — test will fail automatically if condition is false
        Assert.assertTrue(
                currentUrl.contains(expectedUrlPart),
                "Homepage did not load correctly! Expected URL to contain: "
                        + expectedUrlPart + " | Actual URL: " + currentUrl
        );

        System.out.println("Homepage successfully loaded!");
    }


}
