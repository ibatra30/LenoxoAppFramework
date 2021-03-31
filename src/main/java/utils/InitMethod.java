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

/**
 * @Author Gladson Antony
 * @Date 28-Jan-2017
 */
public class InitMethod {
    static JSONObject testData;

    public static String SUITE_NAME = null;
    public static final String OUTPUT_FOLDER = "./src/test/resources/Reports/";
    public static final String FILE_NAME = "Extent Report.html";
    public static final String REPORT_CONFIG_FILE = "./src/main/java/utils/extent-config.xml";
    public static final String TEST_OUTPUT_PATH = "test-output/";
    public static final String LOGFILE_PATH = TEST_OUTPUT_PATH + "Logs/";
    public ISuite suite;
    public ExtentTest test;

    public static String logo = localeConfigProp.getProperty("logo");
    public static String txtUsername = localeConfigProp.getProperty("txtUsername");
    public static String repair = localeConfigProp.getProperty("repair");
    public static String postalCode = localeConfigProp.getProperty("postalCode");
    public static String totalresults = localeConfigProp.getProperty("totalresults");
    public static String miles = localeConfigProp.getProperty("miles");
    public static String poNumber = localeConfigProp.getProperty("poNumber");
    public static String heaterProduct = localeConfigProp.getProperty("heaterProduct");
    public static String storeName = localeConfigProp.getProperty("storeName");
    public static String qty = localeConfigProp.getProperty("qty");
    public static String defaultDeliverTo = localeConfigProp.getProperty("defaultDeliverTo");
    public static String defaultPickup = localeConfigProp.getProperty("defaultPickup");
    public static String productName = localeConfigProp.getProperty("productName");
    public static String CatNo = localeConfigProp.getProperty("CatNo");
    public static String modelNo = localeConfigProp.getProperty("modelNo");
    public static String price = localeConfigProp.getProperty("price");
    public static String qtyNo = localeConfigProp.getProperty("qtyNo");

}
