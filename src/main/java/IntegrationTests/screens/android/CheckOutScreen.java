package IntegrationTests.screens.android;

import logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.GenericMethods;

import java.util.List;


/**
 * contains all the locators present on the login Screen
 */
public class CheckOutScreen extends GenericMethods {

    public CheckOutScreen(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    WebElement element;
    String text;

    // Locators on the checkout screen
    @FindBy(xpath = "//android.view.View[@content-desc='cart#clear-cart-modal']")
    WebElement delete_cart_btn;

    @FindBy(xpath = "//android.view.View[@content-desc=\"YES\"]")
    WebElement popUp_yes;

    @FindBy(xpath = "//android.widget.Button[@text='REVIEW ORDER']")
    WebElement btn_ReviewOrder;

    @FindBy(xpath = "//android.widget.Button[@text='SUBMIT ORDER']")
    WebElement btn_SubmitOrder;

    @FindBy(xpath = "//android.view.View[@content-desc='VIEW ORDER STATUS']")
    WebElement txt_ViewOrderStatus;

    @FindBy(xpath = "//android.view.View[1]/android.view.View[2]/android.view.View[4]")
    WebElement cart_logo;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='checkout-po']")
    WebElement txt_PoNumber;

    @FindBy(xpath = "//android.view.View[@content-desc='PROCEED TO CHECKOUT']")
    WebElement btn_ProceedToCheckout;

    @FindBy(xpath = "//android.view.View[@content-desc=\"(0)\"]")
    List<WebElement> empty_Cart;

    @FindBy(xpath = "//android.view.View[1]/android.view.View[2]/android.view.View[4]")
    WebElement cart_xpath;


    public void checkifCartLogoIsVisible() {
        try {
            WaitTillVisible(cart_logo);
            Assert.assertEquals(cart_logo.isDisplayed(), true);
            Log.info("Cart Logo is displayed");
        } catch (Exception e) {
            Log.info("logo not displayed");
        }
    }

    public void clearTheCartIfNotEmpty() {
        if (VerifyCartIsEmpty()) {
            Log.info("cart is already empty");
        } else {
            element = findElement(cart_xpath);
            element.click();
            WaitTillVisible(delete_cart_btn);
            click(delete_cart_btn);
            WaitTillVisible(popUp_yes);
            click(popUp_yes);
        }
    }

    public boolean VerifyCartIsEmpty() {
        element = findElements(empty_Cart).get(0);
        return element.isDisplayed();
    }

    public void enterPoNumber(String poNumber) {
        this.WaitTillVisible(txt_PoNumber);
        this.enter(txt_PoNumber, poNumber);
        text = txt_PoNumber.getText();
    }

    public void clickProceedToCheckout() {
        this.WaitTillVisible(btn_ProceedToCheckout);
        this.click(btn_ProceedToCheckout);
    }

    public void clickReviewOrder() {
        this.WaitTillVisible(btn_ReviewOrder);
        this.click(btn_ReviewOrder);
    }

    public void clickSubmitOrder() {
        this.WaitTillVisible(btn_SubmitOrder);
        this.click(btn_SubmitOrder);
    }

    public void validatePoNumberEntered(String poNumber) {
        Assert.assertEquals(text, poNumber);
    }

    public void viewOrderStatus() {
        this.WaitTillVisible(txt_ViewOrderStatus);
        Assert.assertEquals(txt_ViewOrderStatus.isDisplayed(), true);
        this.click(txt_ViewOrderStatus);
    }
}