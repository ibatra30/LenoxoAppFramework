package utils;

import IntegrationTests.coreLogic.android.AndroidCoreLogic;
import IntegrationTests.coreLogic.base.CoreLogic;
import IntegrationTests.coreLogic.iOS.IOSCoreLogic;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import listeners.ExtentTestNGIReporterListener;
import logger.Log;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static utils.InitMethod.*;


/**
 * contains all the methods to create a new session and destroy the
 * session after the test(s) execution is over. Each test extends
 * this class.
 */
public class CreateSession {

    public WebDriver driver = null;
    Properties configFile;
    protected static Properties lobConfigProp = new Properties();
    public static Properties localeConfigProp = new Properties();
    protected FileInputStream configFis, lobConfigFis, localeConfigFis;
    public Properties testDataFile;
    private final String CONFIG_FILE_PATH = "//src//main//java//config//config.properties";

    protected File file = new File("");
    Properties configProp = new Properties();
    String OS;
    String SAUCE_REMOTE_URL;
    public ExtentReports extent = new ExtentReports();


    /**
     * this method creates the android driver
     *
     * @param buildPath  - path to pick the location of the app
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public synchronized void androidDriver(String buildPath) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", lobConfigProp.getProperty("DeviceName"));
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", lobConfigProp.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", "com.lennox.DaveNet");
        capabilities.setCapability("appActivity", "crc647cb4db12adeb0334.SplashActivity");
        capabilities.setCapability("name","LennoxHeaterProductCheckoutTestAndroid");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("automationName", "UiAutomator2");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
    }


    public synchronized void SauceLabAndroidDriver(String buildPath) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String username = configProp.getProperty("SauceLab_UserName");
        String accesskey = configProp.getProperty("SauceLab_AccessKey");
        SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl + "/wd/hub";
        capabilities.setCapability("deviceName", lobConfigProp.getProperty("DeviceName"));
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", lobConfigProp.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", "com.lennox.DaveNet");
        capabilities.setCapability("appActivity", "crc647cb4db12adeb0334.SplashActivity");
        capabilities.setCapability("name", "LennoxHeaterProductCheckoutTestAndroid");
        capabilities.setCapability("app", "storage:filename=aos.apk");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("autoAcceptAlerts", true);
        Log.info("session with saucelab started: " + SAUCE_REMOTE_URL);
        driver = new AndroidDriver(new URL(SAUCE_REMOTE_URL), capabilities);
    }


    /**
     * this method creates the iOS driver
     *
     * @param buildPath -  path to pick the location of the app
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public void iOSDriver(String buildPath) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "8.2");
        capabilities.setCapability("appiumVersion", "1.3.7");
        capabilities.setCapability("name", "LennoxHeaterProductCheckoutTestIos");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5s");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), capabilities);

    }

    public synchronized void SauceLabIos(String buildPath) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String username = configProp.getProperty("SauceLab_UserName");
        String accesskey = configProp.getProperty("SauceLab_AccessKey");
        SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl + "/wd/hub";

        capabilities.setCapability("platformName", "iOS");
        // capabilities.setCapability("appiumVersion", "1.19.1");
        //capabilities.setCapability("platformVersion", lobConfigProp.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", "com.lennox.DaveNet");
        capabilities.setCapability("appActivity", "crc647cb4db12adeb0334.SplashActivity");
        capabilities.setCapability("app", "storage:filename=ios.ipa");
        capabilities.setCapability("name", "LennoxHeaterProductCheckoutTestIos");
        capabilities.setCapability("realDevice", true);
        capabilities.setCapability("testobject_device", "iPhone.*");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("autoAcceptAlerts", true);
        Log.info("session with saucelab started: " + SAUCE_REMOTE_URL);
        driver = new IOSDriver<>(new URL(SAUCE_REMOTE_URL), capabilities);
    }

    public void propertiesFileLoad(String platform) throws Exception {
        configFis = new FileInputStream(file.getAbsoluteFile()
                + CONFIG_FILE_PATH);
        configProp.load(configFis);

        File f = new File(file.getAbsoluteFile() + "//src//main//java//config//" + platform
                + ".properties");

        if (f.exists() && !f.isDirectory()) {
            lobConfigFis = new FileInputStream(file.getAbsoluteFile()
                    + "/src//main//java//config//" + platform + ".properties");
            lobConfigProp.load(lobConfigFis);

            localeConfigFis = new FileInputStream(file.getAbsoluteFile()
                    + "//src//main//java//testData//" + "Testdata.properties");
            localeConfigProp.load(localeConfigFis);
        } else {
            throw new Exception("Properties files loading failed ");
        }
    }

    public String choosebuild(String invokeDriver) {
        String appPath = null;
        if (invokeDriver.equals("android")) {
            appPath = configProp.getProperty("AndroidAppPath");
            return appPath;
        } else if (invokeDriver.equals("iOS")) {
            appPath = configProp.getProperty("iOSAppPath");
            return appPath;
        } else if (invokeDriver.equals("SauceLabAndroid")) {
            appPath = configProp.getProperty("SauceLabAppPath");
            return appPath;
        } else if (invokeDriver.equals("SauceLabIos")) {
            appPath = configProp.getProperty("SauceLabAppPath");
            return appPath;
        }
        return appPath;
    }

    public String screenshot() throws IOException{
        WebDriver driver1 = new Augmenter().augment(driver);
        int number = 1;
        File file  = ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(SCREENSHOT_FOLDER + number+".png"));
        String path = SCREENSHOT_FOLDER + number+".png";
        return path;
    }

    //@AfterMethod
    public void flushReport(ITestResult result) throws IOException {
        extent.flush();
    }

    @AfterClass
    public void teardown() {
        Log.info("Shutting down driver");
        driver.quit();
        extent.flush();
    }
}