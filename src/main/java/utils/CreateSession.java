package utils;

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

import static utils.InitMethod.OUTPUT_FOLDER;


/**
 * contains all the methods to create a new session and destroy the
 * session after the test(s) execution is over. Each test extends
 * this class.
 */
@Listeners({ExtentTestNGIReporterListener.class})
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


    /**
     * this method starts Appium server. Calls startAppiumServer method to start the session depending upon your OS.
     *
     * @throws Exception Unable to start appium server
     */
    //@BeforeSuite
    public void invokeAppium() throws Exception {
        String OS = System.getProperty("os.name").toLowerCase();
        try {
            startAppiumServer(OS);
            Log.info("Appium server started successfully");
        } catch (Exception e) {
            Log.logError(getClass().getName(), "startAppium", "Unable to start appium server");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * this method stops Appium server.Calls stopAppiumServer method to
     * stop session depending upon your OS.
     *
     * @throws Exception Unable to stop appium server
     */
    //@AfterSuite
    public void stopAppium() throws Exception {
        try {
            stopAppiumServer(OS);
            Log.info("Appium server stopped successfully");

        } catch (Exception e) {
            Log.logError(getClass().getName(), "stopAppium", "Unable to stop appium server");
            throw new Exception(e.getMessage());
        }
    }


    /**
     * this method creates the driver depending upon the passed parameter (android or iOS)
     * and loads the properties files (config and test data properties files).
     *
     * @param os         android or iOS
     * @param methodName - name of the method under execution
     * @throws Exception issue while loading properties files or creation of driver.
     */
    @Parameters({"os"})
    @BeforeMethod
    public void createDriver(String os, Method methodName) throws Exception {

        propertiesFileLoad(os);

        File propertiesFile = new File(file.getAbsoluteFile() + "//src//main//java//log4j.properties");
        PropertyConfigurator.configure(propertiesFile.toString());
        Log.info("--------------------------------------");


        if (os.equalsIgnoreCase("android")) {
            String buildPath = choosebuild(os);
            androidDriver(buildPath, methodName);
            Log.info("Android driver created");
        } else if (os.equalsIgnoreCase("iOS")) {
            String buildPath = choosebuild(os);
            iOSDriver(buildPath, methodName);
            Log.info("iOS driver created");
        } else if (os.equalsIgnoreCase("SauceLabAndroid")) {
            String buildPath = choosebuild(os);
            SauceLabAndroidDriver(buildPath, methodName);
            Log.info("Android driver created");
        } else if (os.equalsIgnoreCase("SauceLabIos")) {
            String buildPath = choosebuild(os);
            SauceLabIos(buildPath, methodName);
            Log.info("iOS driver created");
        }
    }

    /**
     * this method quit the driver after the execution of test(s)
     */
    @AfterMethod
    public void teardown() {
        Log.info("Shutting down driver");
        driver.quit();
    }


    /**
     * this method creates the android driver
     *
     * @param buildPath  - path to pick the location of the app
     * @param methodName - name of the method under execution
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public synchronized void androidDriver(String buildPath, Method methodName) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", lobConfigProp.getProperty("DeviceName"));
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", lobConfigProp.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", "com.lennox.DaveNet");
        capabilities.setCapability("appActivity", "crc647cb4db12adeb0334.SplashActivity");
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("automationName", "UiAutomator2");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
    }


    public synchronized void SauceLabAndroidDriver(String buildPath, Method methodName) throws MalformedURLException {
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
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability("app", "storage:filename=aos.apk");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("automationName", "UiAutomator2");
        Log.info("session with saucelab started: " + SAUCE_REMOTE_URL);
        driver = new AndroidDriver(new URL(SAUCE_REMOTE_URL), capabilities);
    }


    /**
     * this method creates the iOS driver
     *
     * @param buildPath-  path to pick the location of the app
     * @param methodName- name of the method under execution
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public void iOSDriver(String buildPath, Method methodName) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "8.2");
        capabilities.setCapability("appiumVersion", "1.3.7");
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 5s");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new IOSDriver(new URL("http://localhost:4723/wd/hub"), capabilities);

    }

    public synchronized void SauceLabIos(String buildPath, Method methodName) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String username = configProp.getProperty("SauceLab_UserName");
        String accesskey = configProp.getProperty("SauceLab_AccessKey");
        SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl + "/wd/hub";

        // capabilities.setCapability("deviceName", lobConfigProp.getProperty("DeviceName"));
        capabilities.setCapability("platformName", "iOS");
        // capabilities.setCapability("appiumVersion", "1.19.1");
        //capabilities.setCapability("platformVersion", lobConfigProp.getProperty("platformVersion"));
        capabilities.setCapability("appPackage", "com.lennox.DaveNet");
        capabilities.setCapability("appActivity", "crc647cb4db12adeb0334.SplashActivity");
        capabilities.setCapability("app", "storage:filename=ios.ipa");
        capabilities.setCapability("name", methodName.getName());
        capabilities.setCapability("realDevice", true);
        capabilities.setCapability("testobject_device", "iPhone.*");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);
        capabilities.setCapability("autoAcceptAlerts", true);
        Log.info("session with saucelab started: " + SAUCE_REMOTE_URL);
        driver = new IOSDriver<>(new URL(SAUCE_REMOTE_URL), capabilities);
    }

    /**
     * this method starts the appium  server depending on your OS.
     *
     * @param os your machine OS (windows/linux/mac)
     * @throws IOException          Signals that an I/O exception of some sort has occurred
     * @throws ExecuteException     An exception indicating that the executing a subprocesses failed
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     *                              or otherwise occupied, and the thread is interrupted, either before
     *                              or during the activity.
     */
    public void startAppiumServer(String os) throws ExecuteException, IOException, InterruptedException {
        if (os.contains("windows")) {
            CommandLine command = new CommandLine("cmd");
            command.addArgument("/c");
            command.addArgument("C:/Program Files/nodejs/node.exe");
            command.addArgument("C:/Appium/node_modules/appium/bin/appium.js");
            command.addArgument("--address", false);
            command.addArgument("127.0.0.1");
            command.addArgument("--port", false);
            command.addArgument("4723");
            command.addArgument("--full-reset", false);

            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000);
        } else if (os.contains("mac os x")) {
            CommandLine command = new CommandLine("/Applications/Appium.app/Contents/Resources/node/bin/node");
            command.addArgument("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js", false);
            command.addArgument("--address", false);
            command.addArgument("127.0.0.1");
            command.addArgument("--port", false);
            command.addArgument("4723");
            command.addArgument("--full-reset", false);
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000);
        } else if (os.contains("linux")) {
            //Start the appium server
            System.out.println("ANDROID_HOME : ");
            System.getenv("ANDROID_HOME");
            //	System.out.println("PATH :" +System.getenv("PATH"));
            CommandLine command = new CommandLine("/bin/bash");
            command.addArgument("-c");
            command.addArgument("~/.linuxbrew/bin/node");
            command.addArgument("~/.linuxbrew/lib/node_modules/appium/lib/appium.js", true);
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000); //Wait for appium server to start

        } else {
            Log.info(os + "is not supported yet");
        }
    }

    /**
     * this method stops the appium  server.
     *
     * @param os your machine OS (windows/linux/mac).
     * @throws IOException      Signals that an I/O exception of some sort has occurred.
     * @throws ExecuteException An exception indicating that the executing a subprocesses failed.
     */
    public void stopAppiumServer(String os) throws ExecuteException, IOException {
        if (os.contains("windows")) {
            CommandLine command = new CommandLine("cmd");
            command.addArgument("/c");
            command.addArgument("Taskkill /F /IM node.exe");

            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
        } else if (os.contains("mac os x")) {
            String[] command = {"/usr/bin/killall", "-KILL", "node"};
            Runtime.getRuntime().exec(command);
            Log.info("Appium server stopped");
        } else if (os.contains("linux")) {
            // need to add it
        }
    }

    /**
     * this method loads properties files config and file having test data.
     *
     * @param platform android or ios, to load specific test data file.
     * @throws Exception property files are not loaded successfully
     */
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

            String locale = lobConfigProp.getProperty("LOCALE");

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

    public void screenshot() throws IOException{
        Random random = new Random();
        WebDriver driver1 = new Augmenter().augment(driver);
        File file  = ((TakesScreenshot)driver1).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File(OUTPUT_FOLDER + random.nextInt()+".png"));
    }
}
