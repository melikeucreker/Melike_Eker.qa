package Utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ReusableMethods {


    public static void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<String> convertToStringList(List<WebElement> webElementList) {
        List<String> stringList = new ArrayList<>();
        for (WebElement element : webElementList) {
            stringList.add(element.getText());
        }
        return stringList;
    }


    public static void switchWindowByUrl(WebDriver driver, String targetUrl) {
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            driver.switchTo().window(handle);
            String actualUrl = driver.getCurrentUrl();
            if (targetUrl.equals(actualUrl)) {
                break;
            }
        }
    }


    public static void switchWindowByTitle(WebDriver driver, String targetTitle) {
        Set<String> allWindowHandles = driver.getWindowHandles();
        for (String handle : allWindowHandles) {
            driver.switchTo().window(handle);
            String actualTitle = driver.getTitle();
            if (targetTitle.equals(actualTitle)) {
                break;
            }
        }
    }


    public static void takeFullPageScreenshot(WebDriver driver) {
        TakesScreenshot tss = (TakesScreenshot) driver;
        File file = new File("target/screenshots/fullPageScreenshot.jpg");
        File tempFile = tss.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeFullPageScreenshot(WebDriver driver, String reportName) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TakesScreenshot tss = (TakesScreenshot) driver;
        File file = new File("target/screenshots/" + reportName + ".jpg");
        File tempFile = tss.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeFullPageScreenshotWithTimestamp(WebDriver driver) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_ddMMyy_HHmmss");
        String timestamp = now.format(formatter);

        TakesScreenshot tss = (TakesScreenshot) driver;
        File file = new File("target/screenshots/fullPageScreenshot" + timestamp + ".jpg");
        File tempFile = tss.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void takeFullPageScreenshotWithTimestamp(WebDriver driver, String reportName) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_ddMMyy_HHmmss");
        String timestamp = now.format(formatter);

        TakesScreenshot tss = (TakesScreenshot) driver;
        File file = new File("target/screenshots/" + reportName + timestamp + ".jpg");
        File tempFile = tss.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeElementScreenshot(WebElement element) {
        String filePath = "target/screenshots/targetElement.jpg";
        File destFile = new File(filePath);
        File tempFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeElementScreenshot(WebElement element, String imageName) {
        String filePath = "target/screenshots/" + imageName + ".jpg";
        File destFile = new File(filePath);
        File tempFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeElementScreenshotWithTimestamp(WebElement element) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_ddMMyy_HHmmss");
        String timestamp = now.format(formatter);

        String filePath = "target/screenshots/targetElement" + timestamp + ".jpg";
        File destFile = new File(filePath);
        File tempFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void takeElementScreenshotWithTimestamp(WebElement element, String imageName) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_ddMMyy_HHmmss");
        String timestamp = now.format(formatter);

        String filePath = "target/screenshots/" + imageName + timestamp + ".jpg";
        File destFile = new File(filePath);
        File tempFile = element.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(tempFile, destFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String addScreenshotToReport(String testName) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("_yyMMdd_HHmmss");
        String timestamp = now.format(formatter);

        TakesScreenshot tss = (TakesScreenshot) Driver.getDriver();
        File tempFile = tss.getScreenshotAs(OutputType.FILE);

        String filePath = System.getProperty("user.dir") + "/test-output/Screenshots/" + testName + timestamp + ".jpg";
        File destFile = new File(filePath);
        FileUtils.copyFile(tempFile, destFile);

        return filePath;
    }

    public static void closeCommonPopups(WebDriver driver) {
        try {
            driver.switchTo().defaultContent();
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement frame : iframes) {
                String src = frame.getAttribute("src");
                String id = frame.getAttribute("id");
                if (src != null && (src.contains("consent") || src.contains("onetrust")) ||
                        id != null && id.contains("consent")) {
                    driver.switchTo().frame(frame);
                    break;
                }
            }
        } catch (Exception ignore) {
        }

        List<By> popupSelectors = Arrays.asList(
                By.id("onetrust-accept-btn-handler"),
                By.id("onetrust-reject-all-handler"),
                By.cssSelector("button[aria-label='Close'], .btn-close, .close"),
                By.xpath("//button[contains(.,'Accept') or contains(.,'Allow') or contains(.,'Kabul')]"),
                By.xpath("//button[contains(.,'Reject') or contains(.,'Block') or contains(.,'Reddet')]")
        );

        for (By by : popupSelectors) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
                element.click();
                System.out.println("Popup closed successfully: " + by);
                break;
            } catch (Exception ignore) {
            }
        }


        try {
            driver.switchTo().defaultContent();
        } catch (Exception ignore) {
        }

        System.out.println("Popup handling completed (if any existed).");
    }

    public static boolean dismissCookies(WebDriver driver) {
        return dismissCookies(driver, new By[0]);
    }

    public static boolean dismissCookies(WebDriver driver, By... extraSelectors) {
        boolean handled = false;
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            driver.switchTo().defaultContent();
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement frame : iframes) {
                String id = safe(frame.getAttribute("id"));
                String src = safe(frame.getAttribute("src"));
                String name = safe(frame.getAttribute("name"));
                if (containsAny(src, "consent", "cookie", "onetrust")
                        || containsAny(id, "consent", "cookie", "onetrust")
                        || containsAny(name, "consent", "cookie", "onetrust")) {
                    driver.switchTo().frame(frame);
                    break;
                }
            }
        } catch (Exception ignore) {
        }

        List<By> commonCandidates = Arrays.asList(
                // Prefer reject first
                By.id("onetrust-reject-all-handler"),
                By.xpath("//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'reject') or contains(.,'Reddet') or contains(.,'Decline')]"),
                // Accept fallbacks
                By.id("onetrust-accept-btn-handler"),
                By.xpath("//button[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'accept') or contains(.,'Kabul') or contains(.,'Allow')]"),
                // Generic close
                By.cssSelector("button[aria-label='Close'], .btn-close, .close, .ot-close-icon")
        );

        for (By by : merge(commonCandidates, Arrays.asList(extraSelectors))) {
            try {
                WebElement el = shortWait.until(ExpectedConditions.elementToBeClickable(by));
                el.click();
                handled = true;
                break;
            } catch (Exception ignore) {
            }
        }

        try {
            driver.switchTo().defaultContent();
        } catch (Exception ignore) {
        }

        if (!handled) {
            try {
                String hideScript =
                        "const sels=[" +
                                "'#onetrust-banner-sdk','#onetrust-consent-sdk','.onetrust-pc-dark-filter'," +
                                "'.cookie-banner','.cookieConsent','.cookie-consent','.cc-window','.ot-sdk-container'];" +
                                "let hidden=false; sels.forEach(s=>{document.querySelectorAll(s).forEach(e=>{e.style.display=" +
                                "'none'; hidden=true;});}); hidden;";
                Boolean hidden = (Boolean) ((JavascriptExecutor) driver).executeScript(hideScript);
                handled = Boolean.TRUE.equals(hidden);
            } catch (Exception ignore) {
            }
        }

        return handled;
    }

    // ---------- helpers ----------
    private static boolean containsAny(String text, String... needles) {
        text = safe(text).toLowerCase();
        for (String n : needles) if (text.contains(n.toLowerCase())) return true;
        return false;
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }

    private static List<By> merge(List<By> base, List<By> extras) {
        return new java.util.ArrayList<By>() {{
            addAll(base);
            addAll(extras);
        }};
    }

    public void acceptAllCookies() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
        try {

            WebElement acceptAllBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.id("wt-cli-accept-all-btn")));
            ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", acceptAllBtn);
            acceptAllBtn.click();
            System.out.println("'Accept All' cookies button clicked successfully.");
        } catch (TimeoutException e) {
            System.out.println("'Accept All' cookies button not found on this page.");
        } catch (Exception e) {
            System.out.println("Error while clicking 'Accept All' button: " + e.getMessage());
        }
    }

    public void waitForAndClosePopup() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ins-notification-content")));


            WebElement closeBtn = Driver.getDriver().findElement(By.cssSelector(".ins-close-button"));
            ((JavascriptExecutor) Driver.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", closeBtn);
            closeBtn.click();

            System.out.println("Popup closed successfully.");
        } catch (TimeoutException e) {
            System.out.println(" Popup did not appear within the wait time.");
        } catch (Exception e) {
            System.out.println("Error while closing popup: " + e.getMessage());
        }
    }

    public void hoverOverAcceptAllButton() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            WebElement acceptAllBtn = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("wt-cli-accept-all-btn"))
            );

            ((JavascriptExecutor) Driver.getDriver())
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", acceptAllBtn);

            Actions actions = new Actions(Driver.getDriver());
            actions.moveToElement(acceptAllBtn).perform();

            System.out.println("Hovered over 'Accept All' button successfully.");
        } catch (Exception e) {
            System.out.println(" Could not hover over 'Accept All' button: " + e.getMessage());
        }
    }


}