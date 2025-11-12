package Utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Driver {

    private Driver() {
    }

    private static WebDriver driver;

    public static WebDriver getDriver() {

        String browserToUse = ConfigReader.getProperty("browser");

        if (browserToUse == null) {
            browserToUse = "chrome";
        }

        if (driver == null) {
            switch (browserToUse.toLowerCase()) {

                case "edge":
                    driver = new EdgeDriver();
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                    firefoxOptions.addPreference("geo.enabled", false);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;

                case "safari":
                    driver = new SafariDriver();
                    break;

                default:
                    // 🔒 Chrome: Disable all permission popups (notifications, camera, mic, etc.)
                    ChromeOptions chromeOptions = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2); // 1=allow, 2=block
                    prefs.put("profile.default_content_setting_values.geolocation", 2);
                    prefs.put("profile.default_content_setting_values.media_stream_mic", 2);
                    prefs.put("profile.default_content_setting_values.media_stream_camera", 2);
                    chromeOptions.setExperimentalOption("prefs", prefs);
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--disable-geolocation");

                    driver = new ChromeDriver(chromeOptions);
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }

        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
