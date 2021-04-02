package tests.testngTests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import listeners.ExtentTestNGIReporterListener;
import logger.Log;

import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.*;

import IntegrationTests.coreLogic.base.*;
import IntegrationTests.coreLogic.android.*;
import IntegrationTests.coreLogic.iOS.*;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import static utils.InitMethod.*;

//@Listeners({ExtentTestNGIReporterListener.class})
public class LennoxTest extends CreateSession {
    CoreLogic coreLogic;
    ExtentTest test;
    ITestResult result;
    String temp;
    public ExtentReports extent = new ExtentReports();
    ExtentSparkReporter spark = new ExtentSparkReporter(OUTPUT_FOLDER +"Extent Report.html");

    @Parameters({"os"})
    @BeforeClass
    public void createDriver(String os) throws Exception {
        propertiesFileLoad(os);
        File propertiesFile = new File(LOG_FOLDER);
        PropertyConfigurator.configure(propertiesFile.toString());
        Log.info("--------------------------------------");

        if (os.equalsIgnoreCase("android")) {
            String buildPath = choosebuild(os);
            androidDriver(buildPath);
            Log.info("Android driver created");
            coreLogic = new AndroidCoreLogic(driver);
            Log.info("Android Core logic class instantiated");
        } else if (os.equalsIgnoreCase("iOS")) {
            String buildPath = choosebuild(os);
            iOSDriver(buildPath);
            Log.info("iOS driver created");
            coreLogic = new IOSCoreLogic(driver);
            Log.info("Ios Core logic class instantiated");
        } else if (os.equalsIgnoreCase("SauceLabAndroid")) {
            String buildPath = choosebuild(os);
            SauceLabAndroidDriver(buildPath);
            coreLogic = new AndroidCoreLogic(driver);
            Log.info("Android Core logic class instantiated");
        } else if (os.equalsIgnoreCase("SauceLabIos")) {
            String buildPath = choosebuild(os);
            SauceLabIos(buildPath);
            coreLogic = new IOSCoreLogic(driver);
            Log.info("Ios Core logic class instantiated");
        }
    }

    @BeforeMethod
    public void attachReport(){
        spark.getDeviceContextInfo();
        spark.config().setDocumentTitle("Lennox App Test Report");
        extent.attachReporter(spark);
    }


    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class,priority = 0)
    public void LoginFlow(String rowID, String description, JSONObject testData) throws InterruptedException, IOException {
        test = extent.createTest("LoginTest");
        test.info("Running login test");
        coreLogic.verifyLoginFlow(testData.get("userName").toString(), testData.get("password").toString(), logo);
        temp = screenshot();
        test.pass("login successfull");
        test.addScreenCaptureFromPath(temp);
        ExtentTest test1 = extent.createTest("ClearCheckOutCart");
        test1.info("clearing the cart");
        coreLogic.clearCheckoutCart();
        test1.pass("cart is empty now");
        test1.addScreenCaptureFromPath(temp);
        ExtentTest test2 = extent.createTest("selectProductFromMenu");
        test2.info("Selecting " +heaterProduct +" from the Menu");
        coreLogic.selectProductFromMenu(totalresults, heaterProduct);
        test2.pass("Total " +totalresults +" found on Product List");
        test2.addScreenCaptureFromPath(temp);
        ExtentTest test3 = extent.createTest("UserChangeTheStorePickup");
        test3.info("user changing the store pickup");
        coreLogic.userChangeTheStorePickup(postalCode, defaultDeliverTo, defaultPickup, miles, storeName);
        test3.pass("user able to change the store pickup");
        test3.addScreenCaptureFromPath(temp);
        ExtentTest test4 = extent.createTest("addProductToCart");
        test4.info("user adding the product to cart after changing qty");
        coreLogic.addProductToCart(qty, productName, CatNo, modelNo, price);
        test4.info("user added product to cart");
        test4.addScreenCaptureFromPath(temp);
        ExtentTest test5 = extent.createTest("checkOutProduct");
        test5.info("user will review the product and do the payment");
        coreLogic.checkOutProduct(poNumber);
        test5.addScreenCaptureFromPath(temp);
        test5.info("user successfully ordered the product");
    }

}