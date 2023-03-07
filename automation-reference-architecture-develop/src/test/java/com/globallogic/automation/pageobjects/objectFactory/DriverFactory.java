package com.globallogic.automation.pageobjects.objectFactory;

import com.globallogic.automation.pageobjects.STRINGID;
import com.globallogic.automation.pageobjects.enums.*;
import com.globallogic.automation.pageobjects.utilities.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.*;

/**
 * Utility class that creates appium driver based on platform.
 */
public class DriverFactory {
    /**
     * Instance of DriverFactory singleton class.
     */
    private static final DriverFactory ourInstance = new DriverFactory();

    /**
     * AppiumDriver instance that is used by all the page object classes.
     */
    private AppiumDriver mDeviceDriver;

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    private final Logger log = LoggerHelper.getLogger(DriverFactory.class);

    /**
     * ApplicationProperties instance
     */
    private final ApplicationProperties mAppProperties = ApplicationProperties.getInstance();

    /**
     * Method to get singleton class instance.
     *
     * @return instance of DriverFactory class.
     */
    public static DriverFactory getInstance() {
        return ourInstance;
    }

    /**
     * Constructor of DriverFactory class.
     */
    private DriverFactory() {
    }

    /**
     * Method to get appium driver depending on platform saved in system properties.
     *
     * @return instance of AppiumDriver
     * @throws MalformedURLException exception
     */
    public AppiumDriver getDeviceDriver() throws MalformedURLException {
        if (mDeviceDriver != null) {
            return mDeviceDriver;
        }
        Platform platform = Platform.getPlatform(System.getProperty("platform"));
        log.info(String.format("Driver creation for platform:%s", platform.toString()));
        URL appiumUrl = new URL("http://localhost:4723/wd/hub");
        switch (platform) {
            case iPhone:
                mDeviceDriver = new IOSDriver(appiumUrl, getIOSCapabilities());
                break;
            case Android:
                mDeviceDriver = new AndroidDriver(appiumUrl, getAndroidCapabilities());
                break;
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }

        return mDeviceDriver;
    }

    /**
     * Method to provide instance of DesiredCapabilities for iOS platform device type.
     *
     * @return instance of DesiredCapabilities class
     */
    private DesiredCapabilities getIOSCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("noReset", mAppProperties.getPropertyForKey(STRINGID.NO_RESET));
        capabilities.setCapability("bundleId", mAppProperties.getPropertyForKey(STRINGID.BUNDLE_ID));
        capabilities.setCapability("app", mAppProperties.getPropertyForKey(getAppPathKey()));
        capabilities.setCapability("automationName", mAppProperties.getPropertyForKey(STRINGID.IOS_AUTOMATION_NAME));
        capabilities.setCapability("platformName", mAppProperties.getPropertyForKey(STRINGID.IOS_PLATFORM_NAME));
        capabilities.setCapability("deviceName", mAppProperties.getPropertyForKey(STRINGID.IOS_DEVICE_NAME));
        capabilities.setCapability("showIOSLog", mAppProperties.getPropertyForKey(STRINGID.SHOW_IOS_LOG));
        if (DeviceType.getDeviceType(System.getProperty(STRINGID.DEVICE_TYPE)) == DeviceType.Real) {
            capabilities.setCapability("udid", mAppProperties.getPropertyForKey(STRINGID.IOS_UDID));
        }
        capabilities.setCapability("newCommandTimeout", 0);
        return capabilities;
    }

    /**
     * Method to provide instance of DesiredCapabilities for Android platform device type.
     *
     * @return instance of DesiredCapabilities class
     */
    private DesiredCapabilities getAndroidCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appPackage", mAppProperties.getPropertyForKey(STRINGID.ANDROID_APP_PACKAGE));
        capabilities.setCapability("appActivity", mAppProperties.getPropertyForKey(STRINGID.ANDROID_APP_ACTIVITY));
        capabilities.setCapability("platformName", mAppProperties.getPropertyForKey(STRINGID.ANDROID_PLATFORM_NAME));
        capabilities.setCapability("deviceName", mAppProperties.getPropertyForKey(STRINGID.ANDROID_DEVICE_NAME));
        capabilities.setCapability("showANDROIDLog", mAppProperties.getPropertyForKey(STRINGID.SHOW_ANDROID_LOG));
        capabilities.setCapability("automationName", mAppProperties.getPropertyForKey(STRINGID.ANDROID_AUTOMATION_NAME));
        capabilities.setCapability("newCommandTimeout", 600);
        String path = System.getProperty("user.dir");
        capabilities.setCapability("app", path + mAppProperties.getPropertyForKey(getAppPathKey()));
        return capabilities;
    }

    /**
     * Method to get app path key based on device type
     *
     * @return app path key
     */
    private String getAppPathKey() {
        switch (DeviceType.getDeviceType(System.getProperty(STRINGID.DEVICE_TYPE))) {
            case Real:
                return Platform.getPlatform(System.getProperty(STRINGID.PLATFORM)) == Platform.Android ? STRINGID.APK_PATH : STRINGID.IPA_PATH;
            case Simulator:
                return STRINGID.APP_PATH;
            default:
                log.error("Device Type not found");
                throw new IllegalArgumentException("Device Type not found");
        }
    }
}
