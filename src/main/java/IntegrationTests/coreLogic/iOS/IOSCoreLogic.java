package IntegrationTests.coreLogic.iOS;

import IntegrationTests.screens.iOS.LoginScreen;
import logger.Log;
import org.openqa.selenium.WebDriver;
import IntegrationTests.coreLogic.base.*;

import static java.lang.Thread.sleep;


/**
 * contains method to login on iOS app
 */
public class IOSCoreLogic extends CoreLogic {

    LoginScreen loginScreen;

    public IOSCoreLogic(WebDriver driver) {
        loginScreen = new LoginScreen(driver);
    }

    String actual;

    @Override
    public void verifyLoginFlow(String userName, String password, String logo)
            throws InterruptedException {
    }


    @Override
    public void clearCheckoutCart()
            throws InterruptedException {
    }

    @Override
    public void selectProductFromMenu(String results, String heaterProduct)
            throws InterruptedException {
    }

    @Override
    public void userChangeTheStorePickup(String postalCode, String defaultDeliverTo, String defaultPickup, String miles, String storeName)
            throws InterruptedException {
    }

    @Override
    public void addProductToCart(String qty, String productName, String CatNo, String modelNo, String price)
            throws InterruptedException {
    }

    @Override
    public void checkOutProduct(String poNumber)
            throws InterruptedException {
    }
}

