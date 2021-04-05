package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.CreateSession;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import static com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String;
import static utils.InitMethod.OUTPUT_FOLDER;
import static utils.InitMethod.SCREENSHOT_FOLDER;

public class ExtentReportListener extends CreateSession implements ITestListener {
    private static ExtentSparkReporter spark;
    private static ExtentReports extent;
    private static ExtentTest test;
    Logger log;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("test started");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("test success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("test failure");
        try {
            test.log(Status.WARNING, result.getName() + "Test Case Failed",
                    createScreenCaptureFromBase64String(screenshot(result.getName(),driver)).build());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        String error =
                "<details>"
                        + "<summary>"
                        + "<b>"
                        + "<font color="
                        + "red>"
                        + "Exception :Click to see"
                        + "</font>"
                        + "</b>"
                        + "</summary>"
                        + Arrays.toString(result.getThrowable()
                        .getStackTrace()).replace(",", "<br>")
                        + "</details> \n"
                        + "</summary>";
        test.log(Status.FAIL, MarkupHelper.createLabel(error, ExtentColor.BLUE));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("skip ");
        test.log(Status.SKIP, " is skip");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info("fail with steps success ");
        test.log(Status.WARNING,"fail with steps success ");
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("start ");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("finish tests ");
    }

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private static String screenshot(String screenName, WebDriver driver) throws Exception {
        String dateName = new SimpleDateFormat("yyyy-MM-dd-hh-mm").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty(SCREENSHOT_FOLDER )+".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }
}
