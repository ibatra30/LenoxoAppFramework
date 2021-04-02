package IntegrationTests.screens.iOS;

import logger.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.GenericMethods;


/**
 * contains all the locators present on the login Screen
 */
public class LoginScreen extends GenericMethods {

    public LoginScreen(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Locators on the login screen
    @FindBy(xpath = "//XCUIElementTypeImage[@name=\"LennoxPros Logo\"]")
    WebElement logo_Lennox;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"Please sign in or create an account\"]")
    WebElement link_Login;

    @FindBy(xpath = "//XCUIElementTypeStaticText[@name=\"User ID (Email)\"]")
    WebElement txt_UserName;

    @FindBy(xpath = "//XCUIElementTypeOther[@name=\"Login | LennoxPROs.com\"]/XCUIElementTypeOther[3]/XCUIElementTypeOther[1]/XCUIElementTypeTextField")
    WebElement editbox_UserName;

    @FindBy(className = "XCUIElementTypeSecureTextField")
    WebElement editbox_Password;

    @FindBy(xpath = "//XCUIElementTypeButton[@name=\"Sign In\"]")
    WebElement btn_SignIn;


    public void checkIfLogoIsVisible(String logo) {
        try {
            WaitTillVisible(logo_Lennox);
            Assert.assertEquals(logo_Lennox.getText(), logo);
            Log.info("Logo is displayed");
        } catch (Exception e) {
            Log.info("logo not displayed");
        }
    }

    public void clickSignIn() {
        waitForVisibility(link_Login);
        findElement(link_Login).click();
    }

    public void checkUserNameLabelDisplayed() {
        WaitTillVisible(txt_UserName);
        Assert.assertEquals(txt_UserName.isDisplayed(), true);
    }

    public void enterUsername(String username) {
        waitForVisibility(editbox_UserName);
        enter(editbox_UserName, username);
    }

    public void enterPassword(String password) {
        waitForVisibility(editbox_Password);
        enter(editbox_Password, password);
    }

    public void submitSignIn() {
        waitForVisibility(btn_SignIn);
        findElement(btn_SignIn).click();
    }
}