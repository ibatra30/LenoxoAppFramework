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
    @FindBy(xpath = "//android.widget.Image[@text='LennoxPros Logo']")
    WebElement logo_Lennox;

    @FindBy(xpath = "//android.view.View[@content-desc='Please sign in or create an account']")
    WebElement link_Login;

    @FindBy(xpath = "//android.view.View[@text='User ID (Email)']")
    WebElement txt_UserName;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='j_username']")
    WebElement editbox_UserName;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='j_password']")
    WebElement editbox_Password;

    @FindBy(xpath = "//android.widget.Button[@resource-id='loginSubmit']")
    WebElement btn_SignIn;

    public void alertHandle() {
        if (isAlertPresent()) {
            dismissAlert();
        } else {
            Log.info("error handling in alert");
        }
    }

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