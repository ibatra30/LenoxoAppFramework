package tests.testngTests;

import logger.Log;

import org.json.simple.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.*;

import IntegrationTests.coreLogic.base.*;
import IntegrationTests.coreLogic.android.*;
import IntegrationTests.coreLogic.iOS.*;
import utils.*;

import java.io.IOException;

import static utils.InitMethod.*;

/**
 * automated test to verify login to android/iOS app.
 */
@Listeners({TestListener.class})
public class LennoxTest extends CreateSession {
    CoreLogic coreLogic;

    @Parameters({"os"})
    @BeforeMethod
    public void instantiateHelpers(String invokeDriver) {
        if (invokeDriver.equalsIgnoreCase("android") || (invokeDriver.equalsIgnoreCase("SauceLabAndroid"))) {
            coreLogic = new AndroidCoreLogic(driver);
        } else if (invokeDriver.equalsIgnoreCase("iOS") || (invokeDriver.equalsIgnoreCase("SauceLabIos"))) {
            coreLogic = new IOSCoreLogic(driver);
        }
    }

    @Test(dataProvider = "fetchData_JSON", dataProviderClass = JSONDataProvider.class)
    public void UserCheckOutMiniHeaterProduct(String rowID,
                                              String description,
                                              JSONObject testData) throws InterruptedException, IOException {
        Log.info("Running login test");
        coreLogic.verifyLoginFlow(testData.get("userName").toString(), testData.get("password").toString(), logo);
        screenshot();
        coreLogic.clearCheckoutCart();
        coreLogic.selectProductFromMenu(totalresults, heaterProduct);
        screenshot();
        coreLogic.userChangeTheStorePickup(postalCode, defaultDeliverTo, defaultPickup, miles, storeName);
        coreLogic.addProductToCart(qty, productName, CatNo, modelNo, price);
        screenshot();
        Log.info("user will review the product and do the payment");
        coreLogic.checkOutProduct(poNumber);
        screenshot();
        Log.info("user successfully ordered the product");
    }
}
