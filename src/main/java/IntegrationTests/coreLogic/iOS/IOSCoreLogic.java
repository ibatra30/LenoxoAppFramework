package IntegrationTests.coreLogic.iOS;

import IntegrationTests.screens.iOS.LennoxScreensLocators;
import logger.Log;
import org.openqa.selenium.WebDriver;
import IntegrationTests.coreLogic.base.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import static java.lang.Thread.sleep;


/**
 * contains method to login on iOS app
 */
public class IOSCoreLogic extends CoreLogic {
    LennoxScreensLocators lennoxScreensLocators;

    public IOSCoreLogic(WebDriver driver) {
        lennoxScreensLocators = new LennoxScreensLocators(driver);
    }

    String actual;

    @Override
    public void verifyLoginFlow(String userName, String password, String logo, String txtUsername, String repair)
            throws InterruptedException {
        sleep(5000);
        handlePopUp();
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

    private void handlePopUp() {
        int retry = 0;{
            Log.info("Handline alert");
            while (retry <= 3) {
                try {
                    if (lennoxScreensLocators.isAlertPresent()) {
                        lennoxScreensLocators.dismissAlert();
                        retry++;
                        break;
                    } else {
                        Log.info("error handling in alert");
                    }
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    Log.info("no alert present");
                }
            }
        }
    }

    @Override
    public void clearCheckoutCart()
            throws InterruptedException {
    }

    @Override
    public void selectProductFromProductList(String postalCode, String product, String results, String miles, String heaterProduct, String storeName)
            throws InterruptedException {
    }

    @Override
    public void addProductToCart(String qty)
            throws InterruptedException {
    }

    @Override
    public void checkOutProduct(String poNumber)
            throws InterruptedException {
    }
}

