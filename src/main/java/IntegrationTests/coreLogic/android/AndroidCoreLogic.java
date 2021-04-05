package IntegrationTests.coreLogic.android;

import IntegrationTests.screens.android.CheckOutScreen;
import IntegrationTests.screens.android.DashBoardScreen;
import IntegrationTests.screens.android.LoginScreen;
import logger.Log;


import IntegrationTests.coreLogic.base.*;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;


/**
 * contains all methods to login on android app
 */
public class AndroidCoreLogic extends CoreLogic {
    LoginScreen loginScreen;
    CheckOutScreen checkOutScreen;
    DashBoardScreen dashBoardScreen;

    public AndroidCoreLogic(WebDriver driver) {
        loginScreen = new LoginScreen(driver);
        checkOutScreen = new CheckOutScreen(driver);
        dashBoardScreen = new DashBoardScreen(driver);
    }


    @Override
    public void verifyLoginFlow(String userName, String password, String logo) throws InterruptedException {
        Log.info("check if any alert present");
        loginScreen.alertHandle();
        loginScreen.checkIfLogoIsVisible(logo);
        Log.info("user click on Sign In link");
        loginScreen.clickSignIn();
        loginScreen.checkUserNameLabelDisplayed();
        loginScreen.enterUsername(userName);
        loginScreen.enterPassword(password);
        loginScreen.submitSignIn();
        Log.info("Login Successful");
    }

    @Override
    public void clearCheckoutCart() throws InterruptedException {
        Log.info("user clear the cart first");
        checkOutScreen.checkifCartLogoIsVisible();
        checkOutScreen.clearTheCartIfNotEmpty();
        Log.info("cart is empty now");

    }

    @Override
    public void selectProductFromMenu(String results, String heaterProduct) throws InterruptedException {
        Log.info("user click on Menu Icon");
        dashBoardScreen.clickMenuIcon();
        dashBoardScreen.verifyProductLabelIsVisible();
        Log.info("user select commercial option from Menu");
        dashBoardScreen.selectCommercialFromMenu();
        Log.info("user scroll and select " + heaterProduct + " from the commercial list");
        dashBoardScreen.scrollAndSelectProduct(heaterProduct);
        dashBoardScreen.verifyProductsListed(results);
        Log.info("user is shown " + results);
    }

    @Override
    public void userChangeTheStorePickup(String postalCode, String defaultDeliverTo, String defaultPickup, String miles, String storeName) throws InterruptedException {
        defaultDeliverTo = "45133-1102";
        Log.info("user check default deliver to " + defaultDeliverTo + " and pickup " + defaultPickup);
        dashBoardScreen.checkDefaultPickUp(defaultDeliverTo, defaultPickup);
        dashBoardScreen.selectDeliverToLink();
        Log.info("user clicked to modify store pickup");
        dashBoardScreen.userEnterPostalCode(postalCode);
        dashBoardScreen.userSelectRadius();
        Log.info("user select 120 miles");
        dashBoardScreen.select120miles(miles);
        Log.info("click On Go button and unselect default store");
        dashBoardScreen.clickGoBtn();
        Log.info("user scroll and check if View More is avaialble");
        dashBoardScreen.checkViewMoreBtn();
        Log.info("user scroll and search for Grand Prairie, and select the store if not select");
        dashBoardScreen.selectAnotherStore();
        Log.info("user click on Done Button");
        dashBoardScreen.clickDoneBtn();
    }

    @Override
    public void addProductToCart(String qty, String productName, String CatNo, String modelNo, String price) throws InterruptedException {
        Log.info("user is on product list page");
        Log.info("storing product details to verify on next screen");
        dashBoardScreen.addQty(qty);
        Log.info("user increased the  product qty");
        dashBoardScreen.ClickAddToCart();
        Log.info("user is shown item added to dashboard");
       // dashBoardScreen.verifySuccessMsg();
        Log.info("user verify product details");
        dashBoardScreen.verifyProductDetails(productName, CatNo, modelNo, price);
        Log.info("user proceed to checkout");
        dashBoardScreen.cickVewCartBtn();
    }

    @Override
    public void checkOutProduct(String poNumber)
            throws InterruptedException {
        Log.info("user enter Po number and proceed to checkout");
        checkOutScreen.enterPoNumber(poNumber);
        Log.info("user proceed to checkout");
        checkOutScreen.clickProceedToCheckout();
        Log.info("user choose the payment option");
        checkOutScreen.clickReviewOrder();
        Log.info("user validate the po number and submit order");
        checkOutScreen.validatePoNumberEntered(poNumber);
        checkOutScreen.clickSubmitOrder();
        checkOutScreen.viewOrderStatus();
        Log.info("user able to view order status");
    }
}
