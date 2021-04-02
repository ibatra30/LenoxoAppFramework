package IntegrationTests.screens.android;

import logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.GenericMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.CreateSession.localeConfigProp;


/**
 * contains all the locators present on the login Screen
 */
public class DashBoardScreen extends GenericMethods {

    public DashBoardScreen(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    // Locators on the icon menu and store picker screen
    @FindBy(xpath = "//android.view.View[@resource-id='menu-trigger']")
    WebElement icon_Menu;

    @FindBy(xpath = "//android.view.View[@text='PRODUCTS']")
    WebElement products_label;

    @FindBy(xpath = "//android.widget.TextView[@text='Commercial']/parent::android.view.View")
    WebElement txt_Commercial;

    @FindBy(xpath = "//android.view.View[@content-desc='Mini-Split Single Zone Heat Pumps']")
    WebElement MiniSplitProduct;

    @FindBy(xpath = "//android.view.View[@text='14 results']")
    WebElement txt_Results;

    @FindBy(xpath = "//android.widget.TextView[@text='Deliver to: ']/parent::android.view.View")
    WebElement Delivery_to_link;

    @FindBy(xpath = "//android.widget.TextView[2]")
    List<WebElement> txt_DeliverTo;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='postal-code-store']")
    WebElement txt_PostalCode;

    @FindBy(xpath = "//android.widget.Spinner[@resource-id='store-radius']")
    WebElement txt_Radius;

    @FindBy(xpath = "//android.view.View[@resource-id='store-radius']")
    WebElement txt_Radius1;

    @FindBy(xpath = "//android.widget.CheckedTextView[@text='Within 120 miles']")
    WebElement txt_120Miles;

    @FindBy(xpath = "//android.widget.Button[@text='GO']")
    WebElement btn_Go;

    @FindBy(xpath = "//android.widget.Button[@text='VIEW MORE']")
    WebElement btn_ViewMore;

    @FindBy(xpath = "//android.view.View[6]/android.view.View[2]/android.view.View/android.widget.Button")
    WebElement btn_defaultStore;

    @FindBy(xpath = "//android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.Button")
    WebElement btn_anotherStore;

    @FindBy(xpath = "//android.widget.Button[@text='DONE']")
    WebElement btn_Done;

    @FindBy(className = "android.widget.EditText")
    WebElement txt_qty;

    @FindBy(xpath = "//android.widget.Button[@text='Add To Cart']")
    WebElement btn_AddToCart;

    @FindBy(xpath = "//android.view.View[3]/android.view.View/android.view.View[3]")
    WebElement sucessMsg;

    @FindBy(xpath = "*//android.view.View[3]/android.view.View/android.view.View[5]")
    WebElement actualProductName;

    @FindBy(xpath = "//android.view.View[3]/android.view.View/android.view.View[6]")
    WebElement actualCatNo;

    @FindBy(xpath = "//android.view.View[3]/android.view.View/android.view.View[7]")
    WebElement actualModelNo;

    @FindBy(xpath = "//android.view.View[3]/android.view.View/android.view.View[9]")
    WebElement actualPrice;

    @FindBy(xpath = "//android.view.View[@content-desc='VIEW CART']")
    WebElement btn_ViewCart;


    public void clickMenuIcon() {
        this.WaitTillVisible(icon_Menu);
        this.click(icon_Menu);
    }

    public boolean verifyProductLabelIsVisible() {
        this.WaitTillVisible(products_label);
        return this.findElement(products_label).isDisplayed();
    }

    public void selectCommercialFromMenu() {
        this.WaitTillVisible(txt_Commercial);
        this.click(txt_Commercial);
    }

    public void scrollAndSelectProduct(String product) {
        this.scrollDownNClick(MiniSplitProduct);
    }

    public void verifyProductsListed(String results) {
        this.WaitTillVisible(txt_Results);
        Assert.assertEquals(txt_Results.getText(), results);
    }

    public void checkDefaultPickUp(String DeliverTo, String pickUp) {
        this.WaitTillVisible(txt_DeliverTo.get(2));
        String actualDeliverTo = this.findElement(txt_DeliverTo.get(2)).getText();
        Assert.assertEquals(actualDeliverTo, DeliverTo);
    }

    public void selectDeliverToLink() {
        this.WaitTillVisible(Delivery_to_link);
        this.click(Delivery_to_link);
    }

    public void userEnterPostalCode(String postalCode) {
        this.WaitTillVisible(txt_PostalCode);
        this.enter(txt_PostalCode, postalCode);
    }

    public void userSelectRadius() {
        WaitTime(50);
       try {
           if (txt_Radius1.isDisplayed()) {
               this.click(txt_Radius1);
           } else {
               this.click(txt_Radius);
           }
       }catch (Exception e){
           this.click(txt_Radius);
           Log.info("element not visible to user");
       }

    }

    public void select120miles(String miles) {
        this.WaitTillVisible(txt_120Miles);
        this.click(txt_120Miles);
    }

    public void clickGoBtn() {
        this.WaitTillVisible(btn_Go);
        this.click(btn_Go);
    }

    public void checkViewMoreBtn() {
        this.scrollDownNClick(btn_ViewMore);
    }

    public void unselectDefaultStore() {
        this.WaitTillVisible(btn_defaultStore);
        if (btn_defaultStore.getText().equalsIgnoreCase("UNSELECT")) {
            this.click(btn_defaultStore);
        } else {
            Log.info("Btn is already unselected");
        }
    }

    public void selectAnotherStore() {
        this.scrollDownNClick(btn_anotherStore);
    }

    public void clickDoneBtn() {
        this.scrollDownNClick(btn_Done);
    }

    public void addQty(String number) {
        //this.scrollDownNEnter(txt_qty,number);
        this.scrollDown(3,500);
        this.ClickNEnter(txt_qty,number);
    }

    public void ClickAddToCart() {
        this.scrollDownNClick(btn_AddToCart);
    }

    public void verifySuccessMsg() {
        this.WaitTillVisible(sucessMsg);
        Assert.assertEquals(this.findElement(sucessMsg).isDisplayed(), true);
    }

    public void verifyProductDetails(String productName, String CatNo, String ModelNo, String price) {
        List<String> actualProductDetails = new ArrayList<String>();
        this.WaitTillVisible(actualProductName);
        actualProductDetails.add(actualProductName.getText());
        actualProductDetails.add(actualCatNo.getText());
        actualProductDetails.add(actualModelNo.getText());
        actualProductDetails.add(actualPrice.getText());
        Log.info("actual Product Details" + actualProductDetails);
        List<String> expectedProductDetails = new ArrayList<String>();
        expectedProductDetails.add(productName);
        expectedProductDetails.add(CatNo);
        expectedProductDetails.add(ModelNo);
        expectedProductDetails.add(price);
        Log.info("expected Product Details" + expectedProductDetails);
        Assert.assertEquals(actualProductDetails, expectedProductDetails);
    }

    public void cickVewCartBtn() {
        this.WaitTillVisible(btn_ViewCart);
        this.click(btn_ViewCart);
    }
}