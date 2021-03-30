package UITestFramework;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentReporter {

    protected static ExtentTest test;
    protected static ExtentReports extent;
    private static DateFormat dateFormat;
    private static Date date;
    private static String dt;
    private static String buildnumber;
    private static String jenkinsjobname;
    public static String extentReportPath;
    private static String extentReport, reportFolder, reportPath;
    private static HashMap testStatusDetail;
    private static String envName;
    static Map extentTestMap = new HashMap();
    static Map testMap = new HashMap();

    @BeforeSuite(alwaysRun = true)
    public void extentsetUp(ITestContext xmlSuite) {
        String suitName = xmlSuite.getSuite().getName();
        startExtentReport(suitName);
    }

    public synchronized static ExtentReports startExtentReport(String suiteName) {
        try {

            dateFormat = new SimpleDateFormat("MMM-dd_HH-mm");
            date = new Date();
            dt = dateFormat.format(date);
            reportFolder = suiteName + "_" + dt;

            extentReportPath = System.getProperty("user.dir") + "/test-output/";
            buildnumber = System.getProperty("jenkinsbuild");
            jenkinsjobname = System.getProperty("jobname");
            envName = System.getProperty("envname");

            if (buildnumber != null) {
                reportFolder = buildnumber;
                extentReport = buildnumber + "/" + jenkinsjobname + "_" + envName.toUpperCase() + ".html";
            } else {
                extentReport = reportFolder + "/" + suiteName + "_Report_" + dt + ".html";
            }

            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("Report");
            extent = new ExtentReports();
            htmlReporter.setAnalysisStrategy(AnalysisStrategy.TEST);
            extent.attachReporter(htmlReporter);
            testStatusDetail = new HashMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return extent;
    }

    public synchronized ExtentTest startTestCase(String testName) {

        test = getReporter().createTest(testName, testName);
        return test;
    }


    @SuppressWarnings("unchecked")
    public synchronized static void reportStep(WebDriver driver, String desc, String status, int scFlag) {
        try {

            MediaEntityModelProvider mediaModel;

            if (status.toUpperCase().equals("PASS")) {

                if (scFlag > 0) {
                    mediaModel = MediaEntityBuilder.createScreenCaptureFromPath("Screenshots/" + getScreenShot(driver) + ".jpg").build();
                    getTest().log(Status.PASS, desc, mediaModel);
                    testStatusDetail.put(getTestName(), "-");
                } else {
                    getTest().log(Status.PASS, desc);
                    testStatusDetail.put(getTestName(), "-");
                }

            } else if (status.toUpperCase().equals("FAIL")) {
                if (scFlag > 0) {
                    mediaModel = MediaEntityBuilder.createScreenCaptureFromPath("Screenshots/" + getScreenShot(driver) + ".jpg").build();
                    getTest().log(Status.FAIL, desc, mediaModel);
                    testStatusDetail.put(getTestName(), desc);
                } else {
                    getTest().log(Status.FAIL, desc);
                    testStatusDetail.put(getTestName(), "-");
                }

            } else if (status.toUpperCase().equals("ERROR")) {
                getTest().log(Status.ERROR, desc);
                testStatusDetail.put(getTestName(), desc);
            } else if (status.toUpperCase().equals("INFO")) {
                if (scFlag > 0) {
                    mediaModel = MediaEntityBuilder.createScreenCaptureFromPath("Screenshots/" + getScreenShot(driver) + ".jpg").build();
                    getTest().log(Status.INFO, desc, mediaModel);
                    testStatusDetail.put(getTestName(), "-");
                } else {
                    getTest().log(Status.INFO, desc);
                    testStatusDetail.put(getTestName(), "-");
                }

            } else if (status.toUpperCase().equals("WARNING")) {
                if (scFlag > 0) {
                    mediaModel = MediaEntityBuilder.createScreenCaptureFromPath("Screenshots/" + getScreenShot(driver) + ".jpg").build();
                    getTest().log(Status.WARNING, desc, mediaModel);
                    testStatusDetail.put(getTestName(), "-");
                } else {
                    getTest().log(Status.WARNING, desc);
                    testStatusDetail.put(getTestName(), "-");
                }

            }
        } catch (Exception e) {
            try {
                throw (e);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        // return test;

    }

    private static long getScreenShot(WebDriver driver) throws IOException {
        long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"));
            FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE),
                    new File(extentReportPath + "reports/extent" + "/" + reportFolder + "/Screenshots/" + number + ".jpg"));
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        return number;
    }


    protected synchronized void endExtentReport(String status, ITestResult result) {
        if (status == "Failed") {
            ExtentReporter.getTest().log(Status.FAIL, result.getThrowable());
        } else if (status == "Skipped") {
            ExtentReporter.getTest().log(Status.SKIP, "Test skipped " + result.getThrowable());
        } else {
            ExtentReporter.getTest().log(Status.PASS, "Test passed");
        }
        getReporter().flush();
    }

    protected synchronized void endExtentReport(String scenario, String status) {
        if (status == "Failed") {
            ExtentReporter.getTest().log(Status.FAIL, "Scenario \"" + scenario + "\" Failed.");
        } else if (status == "Skipped") {
            ExtentReporter.getTest().log(Status.SKIP, "Scenario \"" + scenario + "\" Skipped.");
        } else {
            ExtentReporter.getTest().log(Status.PASS, "Scenario \"" + scenario + "\" Passed.");
        }
        endTest();
        getReporter().flush();
    }

    public synchronized static void endExtentReport() {
        endTest();
        getReporter().flush();
    }

    public synchronized static void assignGroups(ITestResult result) {
        for (String group : result.getMethod().getGroups()) {
            ExtentReporter.getTest().assignCategory(group);
        }
    }

    private synchronized static ExtentReports getReporter() {
        if (extent == null)
            extent = startExtentReport("AEM Automation");
        return extent;
    }

    private static synchronized ExtentTest getTest() {
        return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    private static synchronized String getTestName() {
        String testName = (String) testMap.get((int) (long) (Thread.currentThread().getId()));
        return testName;
    }

    private synchronized static void endTest() {
        getReporter().flush();
        //getReporter().endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }

    @SuppressWarnings("unused")
    private synchronized ExtentTest startExtentTestReport(String testName) {
        return startExtentTestReport(testName, "");
    }

    @SuppressWarnings("unchecked")
    public synchronized static ExtentTest startExtentTestReport(String testName, String desc) {
        System.out.println(testName);
        test = getReporter().createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        testMap.put((int) (long) (Thread.currentThread().getId()), testName);

        return test;
    }

    @SuppressWarnings("unchecked")
    public synchronized static ExtentTest startExtentTestReport(String testName, String desc, ITestResult result) {
        System.out.println(testName);
        test = getReporter().createTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        testMap.put((int) (long) (Thread.currentThread().getId()), testName);
       // assignGroups(result);
        return test;
    }
}