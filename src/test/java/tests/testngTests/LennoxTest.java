package tests.testngTests;

import UITestFramework.ExtentReporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.sun.org.apache.xalan.internal.res.XSLTErrorResources_en;
import logger.Log;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import IntegrationTests.coreLogic.base.*;
import IntegrationTests.coreLogic.android.*;
import IntegrationTests.coreLogic.iOS.*;
import UITestFramework.CreateSession;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.Locale;

import static UITestFramework.ExtentReporter.endExtentReport;

/**
 * automated test to verify login to android/iOS app.
 */
public class LennoxTest extends CreateSession {
    CoreLogic coreLogic;
    Method method;
    ITestResult result;
    ExtentReporter extentReporter;
    String userName;
    String password;
    String logo;
    String txtUsername;
    String repair;
    String postalCode;
    String product;
    String totalresults;
    String miles;
    String poNumber;
    String heaterProduct;
    String storeName;
    String qty;

    /**
     * this method instantiate required helpers depending on the platform(android or iOS)
     *
     * @param invokeDriver android or iOS
     */
    @Parameters({"os"})
    @BeforeMethod
    public void instantiateHelpers(String invokeDriver) {
       ExtentReporter.startExtentTestReport("Test1","UserCheckout",result);
        userName = localeConfigProp.getProperty("userName");
        password = localeConfigProp.getProperty("password");
        logo = localeConfigProp.getProperty("logo");
        txtUsername = localeConfigProp.getProperty("txtUsername");
        repair = localeConfigProp.getProperty("repair");
        postalCode = localeConfigProp.getProperty("postalCode");
        product = localeConfigProp.getProperty("product");
        totalresults = localeConfigProp.getProperty("totalresults");
        miles = localeConfigProp.getProperty("miles");
        poNumber = localeConfigProp.getProperty("poNumber");
        heaterProduct = localeConfigProp.getProperty("heaterProduct");
        storeName = localeConfigProp.getProperty("storeName");
        qty = localeConfigProp.getProperty("qty");

        if (invokeDriver.equalsIgnoreCase("android") || (invokeDriver.equalsIgnoreCase("SauceLabAndroid"))) {
            coreLogic = new AndroidCoreLogic(driver);
        } else if (invokeDriver.equalsIgnoreCase("iOS") || (invokeDriver.equalsIgnoreCase("SauceLabIos"))) {
            coreLogic = new IOSCoreLogic(driver);
        }
    }

    @Test(priority = 0)
    public void UserCheckOutMiniHeaterProduct() throws InterruptedException, IOException {
        Log.info("Running login test");
        coreLogic.verifyLoginFlow(userName, password, logo, txtUsername, repair);
        Log.info("Verified the login");
       /* Log.info("user clearing the cart");
        coreLogic.clearCheckoutCart();
        Log.info("user will select product now");
        Log.info("user search for the product from menu and add to the cart");
        coreLogic.selectProductFromProductList(postalCode, product, totalresults, miles, heaterProduct, storeName);
        Log.info("Add product to cart");
        coreLogic.addProductToCart(qty);
        Log.info("product added to the cart");
        Log.info("user landed on checkout page");
        Log.info("user will review the product and do the payment");
        coreLogic.checkOutProduct(poNumber);
        Log.info("user successfully ordered the product");*/
    }

    @AfterTest(alwaysRun = true)
    public void getEntireResult(){
        endExtentReport();
    }

}
