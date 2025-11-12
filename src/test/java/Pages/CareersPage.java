package Pages;

import Utilities.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import Utilities.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareersPage extends HomePage {
    @FindBy(xpath = "//a[@href='https://useinsider.com/careers/']")
    public WebElement careersLink;

    @FindBy(xpath = "//a[@href='javascript:void(0)' and contains(text(),'See all teams')]")
    public WebElement seeAllTeamsButton;

    @FindBy(xpath = "//h3[@class='text-center mb-4 mb-xl-5' and normalize-space()='Quality Assurance']")
    public WebElement qualityAssuranceTitle;


    @FindBy(css = "p.mt-5.mb-0.mt-lg-0.mx-auto.pl-0")
    public WebElement locationsText;

    @FindBy(xpath = "//h2[text()='Life at Insider']")
    public WebElement lifeAtInsiderTitle;

    public boolean isCareersPageOpened() {
        String baseUrl = ConfigReader.getProperty("baseUrl");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        return currentUrl.equals(baseUrl + "careers/");
    }

    public boolean isLocationsTextVisible() {
        try {
            return locationsText.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLocationsText() {
        return locationsText.getText();
    }

    public boolean isLifeAtInsiderVisible() {
        try {
            return lifeAtInsiderTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLifeAtInsiderText() {
        return lifeAtInsiderTitle.getText();
    }

    public void clickSeeAllTeams() {
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", seeAllTeamsButton);
        seeAllTeamsButton.click();
    }

    public boolean waitForQualityAssuranceVisible() {

        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
        ((JavascriptExecutor) Driver.getDriver())
                .executeScript("arguments[0].scrollIntoView({block:'center'});", qualityAssuranceTitle);
        try {
            wait.until(ExpectedConditions.visibilityOf(qualityAssuranceTitle));
            return qualityAssuranceTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCareersAfterHover() {
        new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(careersLink))
                .click();
    }

    public void hoverAndClickCareersLink() {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(companyMenu)
                .pause(Duration.ofMillis(500))
                .moveToElement(careersLink)
                .click()
                .build()
                .perform();
    }
}
