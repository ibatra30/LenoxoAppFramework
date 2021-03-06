package utils;

import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import logger.Log;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.apache.http.client.methods.RequestBuilder.post;

public class GenericMethods {
    private WebDriver driver;

    // common timeout for all tests can be set here
    public final int timeOut = 150;

    public GenericMethods(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * method verify whether element is present on screen
     *
     * @param targetElement element to be present
     * @return true if element is present else throws exception
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either before
     *                              or during the activity.
     */
    public Boolean isElementPresent(By targetElement) throws InterruptedException {
        Boolean isPresent = driver.findElements(targetElement).size() > 0;
        return isPresent;
    }

    /**
     * method to hide keyboard
     */
    public void hideKeyboard() {
        ((AppiumDriver) driver).hideKeyboard();
    }

    /**
     * method to go back by Android Native back click
     */
    public void back() {
        ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    /**
     * method to wait for an element to be visible
     *
     * @param targetElement element to be visible
     * @return true if element is visible else throws TimeoutException
     */
    public boolean waitForVisibility(WebElement targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.visibilityOf(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is not visible: " + targetElement);
            throw e;

        }
    }

    public WebDriverWait WaitTime(long timeOutInSeconds) {
        return  new WebDriverWait(driver, timeOutInSeconds);
    }

    public void WaitTillVisible(WebElement element) {
        new WebDriverWait(driver, 200).until(ExpectedConditions.visibilityOf(element));
    }

    public void enter(WebElement element, String input) {
        element.clear();
        element.sendKeys(input);
        System.out.println("Element is getting entered " + input);
    }

    /**
     * method to wait for an element until it is invisible
     *
     * @param targetElement element to be invisible
     * @return true if element gets invisible else throws TimeoutException
     */
    public boolean waitForInvisibility(By targetElement) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(targetElement));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element is still visible: " + targetElement);
            System.out.println();
            System.out.println(e.getMessage());
            throw e;

        }
    }

    /**
     * method to tap on the screen on provided coordinates
     *
     * @param xPosition x coordinate to be tapped
     * @param yPosition y coordinate to be tapped
     */
    public void tap(double xPosition, double yPosition) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> tapObject = new HashMap<String, Double>();
        tapObject.put("startX", xPosition);
        tapObject.put("startY", yPosition);
        js.executeScript("mobile: tap", tapObject);
    }


    /**
     * method to find an element
     *
     * @param element to be found
     * @return WebElement if found else throws NoSuchElementException
     */
    public WebElement findElement(WebElement element) {
        try {
            return element;
        } catch (NoSuchElementException e) {
            Log.logError(this.getClass().getName(), "findElement", "Element not found" + element);
            throw e;
        }
    }

    /**
     * method to find all the elements of specific locator
     *
     * @param element to be found
     * @return return the list of elements if found else throws NoSuchElementException
     */
    public List<WebElement> findElements(List<WebElement> element) {
        try {
            return element;
        } catch (NoSuchElementException e) {
            Log.logError(this.getClass().getName(), "findElements", "element not found" + element);
            throw e;
        }
    }

    /**
     * method to get message test of alert
     *
     * @return message text which is displayed
     */
    public String getAlertText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            return alertText;
        } catch (NoAlertPresentException e) {
            throw e;
        }
    }

    /**
     * method to verify if alert is present
     *
     * @return returns true if alert is present else false
     */
    public boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeOut);
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            throw e;
        }
    }

    /**
     * method to Accept Alert if alert is present
     */

    public void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /**
     * method to Dismiss Alert if alert is present
     */

    public void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    /**
     * method to get network settings
     */
    public void getNetworkConnection() {
        System.out.println(((AndroidDriver) driver).getConnection());
    }

    public void scrollDownNClick(WebElement element) {
        int retry = 0;
        {
            while (retry <= 10) {
                try {
                    WaitTime(60);
                    element.click();
                    break;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    Dimension dimension = driver.manage().window().getSize();
                    int scrollStart = (int) (dimension.getHeight() * 0.5);
                    int scrollEnd = (int) (dimension.getHeight() * 0.2);

                    new TouchAction((PerformsTouchActions) (AppiumDriver) driver)
                            .press(PointOption.point(0, scrollStart))
                            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                            .moveTo(PointOption.point(0, scrollEnd))
                            .release().perform();
                    retry++;
                }
            }
        }
    }

    public void scrollDownNEnter(WebElement element,String text) {
        int retry = 0;
        {
            while (retry <= 7) {
                try {
                    WaitTime(60);
                    this.click(element);
                    element.sendKeys(text);
                    hideKeyboard();
                    break;
                } catch (org.openqa.selenium.NoSuchElementException e) {
                    Dimension dimension = driver.manage().window().getSize();
                    int scrollStart = (int) (dimension.getHeight() * 0.5);
                    int scrollEnd = (int) (dimension.getHeight() * 0.2);

                    new TouchAction((PerformsTouchActions) (AppiumDriver) driver)
                            .press(PointOption.point(0, scrollStart))
                            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                            .moveTo(PointOption.point(0, scrollEnd))
                            .release().perform();
                    retry++;
                }
            }
        }
    }


    public void ClickNEnter(WebElement element,String text){
        try{
            WaitTime(60);
            this.click(element);
            element.sendKeys(text);
            hideKeyboard();
        }
        catch (org.openqa.selenium.NoSuchElementException e){
            throw e;
        }
    }

    public void scrollDownNVerify(By locator) {
        WebElement element;
        int retry = 0;
        {
            while (retry <= 7) {
                try {
                    element = driver.findElement(locator);
                    element.isDisplayed();
                    break;
                } catch (org.openqa.selenium.NoSuchElementException e) {

                    Dimension dimension = driver.manage().window().getSize();
                    int scrollStart = (int) (dimension.getHeight() * 0.5);
                    int scrollEnd = (int) (dimension.getHeight() * 0.2);

                    new TouchAction((PerformsTouchActions) (AppiumDriver) driver)
                            .press(PointOption.point(0, scrollStart))
                            .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                            .moveTo(PointOption.point(0, scrollEnd))
                            .release().perform();
                    retry++;
                }
            }
        }
    }

    public boolean verifyElementPreset(By locator) {
        boolean resultFlag = false;
        try {
            WebElement element = driver.findElement(locator);
            if (element.isDisplayed()) {
                resultFlag = true;
            } else {
                resultFlag = false;
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            Log.info("element not located in the page");
        }
        return resultFlag;
    }

    public void scrollIntoView(String Text) {
        ((AndroidDriver<MobileElement>) (AppiumDriver) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\"" + Text + "\").instance(0))").click();
    }


    /**
     * method to set network settings
     *
     * @param airplaneMode pass true to activate airplane mode else false
     * @param wifi         pass true to activate wifi mode else false
     * @param data         pass true to activate data mode else false
     */
    public void setNetworkConnection(boolean airplaneMode, boolean wifi, boolean data) {

        long mode = 1L;

        if (wifi) {
            mode = 2L;
        } else if (data) {
            mode = 4L;
        }

        ConnectionState connectionState = new ConnectionState(mode);
        ((AndroidDriver) driver).setConnection(connectionState);
        System.out.println("Your current connection settings are :" + ((AndroidDriver) driver).getConnection());
    }


    /**
     * method to get all the context handles at particular screen
     */
    public void getContext() {
        ((AppiumDriver) driver).getContextHandles();
    }

    /**
     * method to set the context to required view.
     *
     * @param context view to be set
     */
    public void setContext(String context) {

        Set<String> contextNames = ((AppiumDriver) driver).getContextHandles();

        if (contextNames.contains(context)) {
            ((AppiumDriver) driver).context(context);
            Log.info("Context changed successfully");
        } else {
            Log.info(context + "not found on this page");
        }

        Log.info("Current context" + ((AppiumDriver) driver).getContext());
    }

    /**
     * method to long press on specific element by passing locator
     *
     * @param locator element to be long pressed
     */
    public void longPress(By locator) {
        try {
            WebElement element = driver.findElement(locator);

            TouchAction touch = new TouchAction((MobileDriver) driver);
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withElement(ElementOption.element(element));
            touch.longPress(longPressOptions).release().perform();
            Log.info("Long press successful on element: " + element);
        } catch (NoSuchElementException e) {
            Log.logError(this.getClass().getName(), "findElement", "Element not found" + locator);
            throw e;
        }

    }

    /**
     * method to check if element is displayed
     */
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            if (element.isDisplayed()) {
                return true;
            } else {
                Log.info("element not visible " + element);
                return false;
            }
        } catch (NoAlertPresentException e) {
            throw e;
        }
    }

    /**
     * method to long press on specific x,y coordinates
     *
     * @param x x offset
     * @param y y offset
     */
    public void longPress(int x, int y) {
        TouchAction touch = new TouchAction((MobileDriver) driver);
        PointOption pointOption = new PointOption();
        pointOption.withCoordinates(x, y);
        touch.longPress(pointOption).release().perform();
        Log.info("Long press successful on coordinates: " + "( " + x + "," + y + " )");

    }

    /**
     * method to long press on element with absolute coordinates.
     *
     * @param locator element to be long pressed
     * @param x       x offset
     * @param y       y offset
     */
    public void longPress(By locator, int x, int y) {
        try {
            WebElement element = driver.findElement(locator);
            TouchAction touch = new TouchAction((MobileDriver) driver);
            LongPressOptions longPressOptions = new LongPressOptions();
            longPressOptions.withPosition(new PointOption().withCoordinates(x, y)).withElement(ElementOption.element(element));
            touch.longPress(longPressOptions).release().perform();
            Log.info("Long press successful on element: " + element + "on coordinates" + "( " + x + "," + y + " )");
        } catch (NoSuchElementException e) {
            Log.logError(this.getClass().getName(), "findElement", "Element not found" + locator);
            throw e;
        }

    }

    /**
     * method to swipe on the screen on provided coordinates
     *
     * @param startX   - start X coordinate to be tapped
     * @param endX     - end X coordinate to be tapped
     * @param startY   - start y coordinate to be tapped
     * @param endY     - end Y coordinate to be tapped
     * @param duration duration to be tapped
     */

    public void swipe(double startX, double startY, double endX, double endY, double duration) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashMap<String, Double> swipeObject = new HashMap<String, Double>();
        // swipeObject.put("touchCount", 1.0);
        swipeObject.put("startX", startX);
        swipeObject.put("startY", startY);
        swipeObject.put("endX", endX);
        swipeObject.put("endY", endY);
        swipeObject.put("duration", duration);
        js.executeScript("mobile: swipe", swipeObject);
    }


    public static String UiScrollable(String uiSelector) {
        return "new UiScrollable(new UiSelector().scrollable(true)" +
                ".scrollIntoView(new UiSelector().text(uiSelector)";
    }

    /**
     * method to open notifications on Android
     */

    public void openNotifications() {
        ((AndroidDriver) driver).openNotifications();
    }

    /**
     * method to launchApp
     */

    public void launchApp() {
        ((AppiumDriver) driver).launchApp();
    }


    /**
     * method to click on Element By Name
     *
     * @param elementByName - String element name to be clicked
     */

    public void click(WebElement elementByName) {
        WaitTillVisible(elementByName);
        elementByName.click();
    }

    /**
     * method to scroll down on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollDown(int swipeTimes, int durationForSwipe) {
        Dimension dimension = driver.manage().window().getSize();

        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * 0.5);
            int end = (int) (dimension.getHeight() * 0.3);
            int x = (int) (dimension.getWidth() * .5);


            new TouchAction((AppiumDriver) driver).press(PointOption.point(x, start)).moveTo(PointOption.point(x, end))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }

    public void scrollToWebElement(WebElement locator) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView();", locator);
        } catch (Exception e) {
            System.err.println("Unable to scroll to webelement.  WebElement is not visible.");
        }
    }


    /**
     * method to scroll up on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollUp(int swipeTimes, int durationForSwipe) {
        Dimension dimension = driver.manage().window().getSize();

        for (int i = 0; i <= swipeTimes; i++) {
            int start = (int) (dimension.getHeight() * 0.3);
            int end = (int) (dimension.getHeight() * 0.5);
            int x = (int) (dimension.getWidth() * .5);


            new TouchAction((AppiumDriver) driver).press(PointOption.point(x, start)).moveTo(PointOption.point(x, end))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(durationForSwipe)))
                    .release().perform();
        }
    }


}
