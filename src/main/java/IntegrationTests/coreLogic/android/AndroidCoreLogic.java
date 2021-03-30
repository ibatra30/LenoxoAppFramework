package IntegrationTests.coreLogic.android;

import IntegrationTests.screens.android.LennoxScreensLocators;
import logger.Log;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


import IntegrationTests.coreLogic.base.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static java.lang.Thread.sleep;


/**
 * contains all methods to login on android app
 */
public class AndroidCoreLogic extends CoreLogic {
    LennoxScreensLocators lennoxScreensLocators;

    public AndroidCoreLogic(WebDriver driver) {
        lennoxScreensLocators = new LennoxScreensLocators(driver);
    }

    String actual;

    @Override
    public void verifyLoginFlow(String userName, String password, String logo, String txtUsername, String repair)
            throws InterruptedException {
        sleep(5000);
        handlePopUp();
        verifyLogin();
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.logo_Lennox);
        WebElement lenoxLogo = lennoxScreensLocators.findElement(lennoxScreensLocators.logo_Lennox);
        actual = lenoxLogo.getText();
        Assert.assertEquals(actual, logo);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.link_Login);
        lennoxScreensLocators.findElement(lennoxScreensLocators.link_Login).click();
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_UserName);
        WebElement userNameTxt = lennoxScreensLocators.findElement(lennoxScreensLocators.txt_UserName);
        actual = userNameTxt.getText();
        Assert.assertEquals(actual, txtUsername);
        lennoxScreensLocators.findElement(lennoxScreensLocators.editbox_UserName).sendKeys(userName);
        lennoxScreensLocators.findElement(lennoxScreensLocators.editbox_Password).sendKeys(password);
        lennoxScreensLocators.findElement(lennoxScreensLocators.btn_SignIn).click();
        Log.info("Login Successful");

        //verify if "repair finder" is displayed
        sleep(2000);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.repair_Finder);
        WebElement repair_Finder = lennoxScreensLocators.findElement(lennoxScreensLocators.repair_Finder);
        actual = repair_Finder.getText();
        Assert.assertEquals(actual, repair);
    }

    private void verifyLogin() {

    }

    private void handlePopUp() {
        if (lennoxScreensLocators.isAlertPresent()) {
            lennoxScreensLocators.dismissAlert();
        } else {
            Log.info("error handling in alert");
        }
    }

    @Override
    public void clearCheckoutCart()
            throws InterruptedException {
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.cart_logo);
        Log.info("user clicking on existing cart on dashboard");
        lennoxScreensLocators.findElement(lennoxScreensLocators.cart_logo).click();
        Log.info("user will check if cart is empty or not");
        if(lennoxScreensLocators.verifyElementPreset(lennoxScreensLocators.clear_cart)) {
            lennoxScreensLocators.findElement(lennoxScreensLocators.clear_cart).click();
            Log.info("user click on Yes button");
            lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.popUp_Yes);
            lennoxScreensLocators.findElement(lennoxScreensLocators.popUp_Yes).click();
            Log.info("user cleared the cart");
        }
        else {
           Log.info("active cart is already empty");
       }
    }

    @Override
    public void selectProductFromProductList(String postalCode, String product, String results, String miles, String heaterProduct, String storeName)
            throws InterruptedException {
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.icon_Menu);
        lennoxScreensLocators.findElement(lennoxScreensLocators.icon_Menu).click();
        Log.info("product list is listed in menu");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_product);
        WebElement productTxt = lennoxScreensLocators.findElement(lennoxScreensLocators.txt_product);
        actual = productTxt.getText();
        Assert.assertEquals(actual, product);
        Log.info("user find and select the product " + product + " in dashboard catalog");

        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_Commercial);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_Commercial).click();
        Log.info("clicked on commercial");
        Log.info("user scroll to view " + heaterProduct);
        lennoxScreensLocators.scrollDownNClick(lennoxScreensLocators.txt_Mini);

        Log.info("verify total products listed");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_Results);
        WebElement totalResults = lennoxScreensLocators.findElement(lennoxScreensLocators.txt_Results);
        actual = totalResults.getText();
        Assert.assertEquals(actual, results);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_Delivery);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_Delivery).click();
        Log.info("user enter postal code and radius");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_Post);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_Post).sendKeys(postalCode);
        sleep(1000);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_Radius);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_Radius).click();
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_120Miles);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_120Miles).click();
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.btn_Go);
        lennoxScreensLocators.findElement(lennoxScreensLocators.btn_Go).click();
        Log.info("scroll and check if View more is displayed");
        lennoxScreensLocators.scrollDownNClick(lennoxScreensLocators.btn_ViewMore);
        Log.info("verify whether address is selected or unselected");
        lennoxScreensLocators.scrollDownNVerify(lennoxScreensLocators.store_Name);
        Log.info("verified store name");
        lennoxScreensLocators.scrollDownNClick(lennoxScreensLocators.btn_Done);
    }

    @Override
    public void addProductToCart(String qty) throws InterruptedException {
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.productName);
        String actualProductName = lennoxScreensLocators.findElements(lennoxScreensLocators.productName).get(0).getText();
        Log.info(" " +actualProductName);
        Log.info("add the product to checkout");
        lennoxScreensLocators.scrollDown(3, 500);
        sleep(1000);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.btn_AddToCart);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.qty_box);
        lennoxScreensLocators.findElement(lennoxScreensLocators.qty_box).sendKeys(qty);
        Log.info("product adding to cart");
        lennoxScreensLocators.findElement(lennoxScreensLocators.btn_AddToCart).click();
        Log.info("productName verified again");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.productName);
        String expectedProductName = lennoxScreensLocators.findElements(lennoxScreensLocators.productName).get(0).getText();
        Assert.assertEquals(actualProductName, expectedProductName);
        Log.info("productName verified");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_ViewCart);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_ViewCart).click();
        Log.info("user proceed to checkout");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_ProceedToCheckout);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_ProceedToCheckout).click();
    }

    @Override
    public void checkOutProduct(String poNumber)
            throws InterruptedException {
        sleep(3000);
        Log.info("user enter Po number and proceed to checkout");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.entry_PoNumber);
        lennoxScreensLocators.findElement(lennoxScreensLocators.entry_PoNumber).sendKeys(poNumber);
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_ProceedToCheckout);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_ProceedToCheckout).click();
        Log.info("review order and view order status");
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.btn_ReviewOrder);
        lennoxScreensLocators.findElement(lennoxScreensLocators.btn_ReviewOrder).click();
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.btn_SubmitOrder);
        lennoxScreensLocators.findElement(lennoxScreensLocators.btn_SubmitOrder).click();
        lennoxScreensLocators.waitForVisibility(lennoxScreensLocators.txt_ViewOrderStatus);
        lennoxScreensLocators.findElement(lennoxScreensLocators.txt_ViewOrderStatus).click();
    }

}
