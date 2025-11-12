package Pages;

import Utilities.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Utilities.Driver;

import java.time.Duration;

public class QualityAssurance extends CareersPage {
    WebDriver driver;

    public QualityAssurance() {
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@href='https://useinsider.com/careers/open-positions/?department=qualityassurance' and " +
            "contains(@class, 'btn-outline-secondary')]")
    public WebElement seeAllQAJobsButton;

    @FindBy(xpath = "//span[@id='select2-filter-by-location-container']/ancestor::span[contains(@class,'select2-selection') and @role='combobox']")
    public WebElement locationFilter;

    @FindBy(xpath = "//button[@title='Remove all items']")
    public WebElement removeAllItemsButton;

    @FindBy(xpath = "//li[@class='select2-results__option' and contains(text(),'Istanbul, Turkiye')]")
    public WebElement istanbulTurkeyOption;

    @FindBy(css = "span.select2-selection__arrow[role='presentation']:nth-of-type(2)")
    public WebElement departmentFilterButton;

    @FindBy(css = "li[id='select2-filter-by-department-result-9fs6-Quality Assurance']")
    public WebElement qualityAssuranceOption;

    @FindBy(xpath = "//span[@class='position-department text-large font-weight-600 text-primary']")
    public WebElement qualityAssuranceLabel;

    @FindBy(xpath = "//div[@class='position-location text-large' and normalize-space()='Istanbul, Turkiye']")
    public WebElement istanbulLocationText;


}

