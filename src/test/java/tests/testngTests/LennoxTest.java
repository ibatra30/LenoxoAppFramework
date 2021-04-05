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
    /*public ExtentReports extent = new ExtentReports();
    ExtentSparkReporter spark = new ExtentSparkReporter(OUTPUT_FOLDER +"ExtentReport.html");*/

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

    @Test(dataProvider = "LennoxHeaterProductCheckoutTest", dataProviderClass = ExcelDataProvider.class,priority = 0)
    public void LennoxHeaterProductCheckoutTest(String username, String password,String logo,
                                                String totalresults,String miles,String heaterProduct,
                                                String postalCode, String defaultDeliverTo, String qty,String poNumber,
                                                String storeName, String defaultPickup,String productName,String CatNo,String modelNo,String price)
            throws InterruptedException, IOException {
        coreLogic.verifyLoginFlow(username, password, logo);
        coreLogic.clearCheckoutCart();
        coreLogic.selectProductFromMenu(totalresults, heaterProduct);
        coreLogic.userChangeTheStorePickup(postalCode, defaultDeliverTo, defaultPickup, miles, storeName);
        coreLogic.addProductToCart(qty, productName, CatNo, modelNo, price);
        coreLogic.checkOutProduct(poNumber);
    }
}