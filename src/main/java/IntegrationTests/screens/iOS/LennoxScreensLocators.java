package IntegrationTests.screens.iOS;

import UITestFramework.GenericMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static UITestFramework.CreateSession.localeConfigProp;


/**
 * contains all the locators present on the login Screen
 */
public class LennoxScreensLocators extends GenericMethods {

    public LennoxScreensLocators(WebDriver driver) {
        super(driver);
    }
    public By OKBtn = By.name("Don't Allow");

    // Locators on the login screen
    public By logo_Lennox = By.xpath("//XCUIElementTypeImage[@name=\"LennoxPros Logo\"]");
    public By link_Login = By.xpath("//XCUIElementTypeStaticText[@name=\"Please sign in or create an account\"]");
    public By txt_UserName = By.xpath("//XCUIElementTypeStaticText[@name=\"User ID (Email)\"]");
    public By editbox_UserName = By.xpath("//XCUIElementTypeOther[@name=\"Login | LennoxPROs.com\"]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeTextField");
    public By editbox_Password = By.className("XCUIElementTypeSecureTextField");
    public By btn_SignIn = By.xpath("//XCUIElementTypeButton[@name=\"Sign In\"]");
    public By repair_Finder = By.xpath("//XCUIElementTypeStaticText[@name=\"OEM Repair Parts\"]");


    // Locators on the Dashboard screen
    public By cart_logo = By.xpath("//android.view.View[1]/android.view.View[2]/android.view.View[4]");
    public By txt_product = By.xpath("//android.view.View[@text='PRODUCTS']");
    public By icon_Menu = By.xpath("//android.view.View[@resource-id='menu-trigger']");
    public By txt_Commercial = By.xpath("//android.widget.TextView[@text='Commercial']/parent::android.view.View");
    public By txt_Mini = By.xpath("//android.view.View[@content-desc='Mini-Split Single Zone Heat Pumps']");
    public By txt_Delivery = By.xpath("//android.widget.TextView[@text='Deliver to: ']/parent::android.view.View");
    public By txt_Results = By.xpath("//android.view.View[@text='14 results']");
    public By txt_Post = By.xpath("//android.widget.EditText[@resource-id='postal-code-store']");
    public By txt_Radius = By.xpath("//android.view.View[@resource-id='store-radius']");
    //public By txt_Radius = By.xpath("//android.widget.Spinner[@resource-id='store-radius']");
    public By txt_120Miles = By.xpath("//android.widget.CheckedTextView[@text='Within 120 miles']");
    public By btn_Go = By.xpath("//android.widget.Button[@text='GO']");
    public By btn_ViewMore = By.xpath("//android.widget.Button[@text='VIEW MORE']");
    public By store_Name = By.xpath("//android.widget.TextView[@text='Desoto']");

    public By btn_Done = By.xpath("//android.widget.Button[@text='DONE']");
    public By btn_AddToCart = By.xpath("//android.widget.Button[@text='Add To Cart']");
    public By txt_ViewCart = By.xpath("//android.view.View[@content-desc='VIEW CART']");
    public By txt_ProceedToCheckout = By.xpath("//android.view.View[@content-desc='PROCEED TO CHECKOUT']");

    // Locators on the checkout screen
    public By clear_cart = By.xpath("//android.view.View[@content-desc='cart#clear-cart-modal']");
    public By entry_PoNumber = By.xpath("//android.widget.EditText[@resource-id='checkout-po']");
    public By btn_ReviewOrder = By.xpath("//android.widget.Button[@text='REVIEW ORDER']");
    public By btn_SubmitOrder = By.xpath("//android.widget.Button[@text='SUBMIT ORDER']");
    public By txt_ViewOrderStatus = By.xpath("//android.view.View[@content-desc='VIEW ORDER STATUS']");

    public By popUp_Yes = By.xpath("//android.view.View[@content-desc='YES']");

    //product Details
    public By productName = By.xpath("//android.view.View[@text='"+localeConfigProp.getProperty("productName")+"']");
    public By modelNumber = By.xpath("//android.view.View[@text='"+localeConfigProp.getProperty("ModelNumber")+"']");
    public By productPrice = By.xpath("//android.view.View[@text='"+localeConfigProp.getProperty("productPrice")+"']");


}
