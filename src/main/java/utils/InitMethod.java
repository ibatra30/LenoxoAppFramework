package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.json.simple.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

import java.awt.*;
import java.io.File;
import java.net.URI;

import static utils.CreateSession.localeConfigProp;

public class InitMethod {
    static JSONObject testData;

    public static String SUITE_NAME = null;
    public static final String LOG_FOLDER = "./src/main/java/log4j.properties";
    public static final String OUTPUT_FOLDER = "./src/test/resources/Reports/";
    public static final String SCREENSHOT_FOLDER = "./src/test/resources/Screenshots/";
    public static final String FILE_NAME = "ExtentReport.html";
    public static final String REPORT_CONFIG_FILE = "./src/main/java/utils/extent-config.xml";
    public static final String TEST_OUTPUT_PATH = "test-output/";
    public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "Logs/";
    public static final String EXCEL_FOLDER = "./src/main/java/testData/testData.xlsx";

    public static String postalCode = localeConfigProp.getProperty("postalCode");
    public static String poNumber = localeConfigProp.getProperty("poNumber");
    public static String qty = localeConfigProp.getProperty("qty");
    public static String defaultDeliverTo = localeConfigProp.getProperty("defaultDeliverTo");


    public ExtentReports extent;
    public ExtentTest test;

}
