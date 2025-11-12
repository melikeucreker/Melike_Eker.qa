package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class TestBaseReport {

    protected static ExtentReports extentReports;
    protected static ExtentSparkReporter extentSparkReporter;
    protected static ExtentTest extentTest;

    @BeforeTest(alwaysRun = true)
    public void setUpTest() {
        extentReports = new ExtentReports();
        String date = new SimpleDateFormat("_yyMMdd_HHmmss").format(new Date());
        String filePath = System.getProperty("user.dir") + "/test-output/Report" + date + ".html";

        extentSparkReporter = new ExtentSparkReporter(filePath);
        extentReports.attachReporter(extentSparkReporter);

        extentReports.setSystemInfo("Environment", "live");
        extentReports.setSystemInfo("Browser", ConfigReader.getProperty("browser")); // chrome, firefox...
        extentReports.setSystemInfo("Automation Engineer", "Melike Eker");
        extentSparkReporter.config().setDocumentTitle("TestNG Test Reports");
        extentSparkReporter.config().setReportName("HTML Reports");
    }


    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ReusableMethods.addScreenshotToReport(result.getName());
            extentTest.fail(result.getName());
            extentTest.addScreenCaptureFromPath(screenshotPath);
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip("Test Case is skipped: " + result.getName());
        }
        Driver.quitDriver();
    }


    @AfterTest(alwaysRun = true)
    public void tearDownTest() {
        extentReports.flush();
    }
}