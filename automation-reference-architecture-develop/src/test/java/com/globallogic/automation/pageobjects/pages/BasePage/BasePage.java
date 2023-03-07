package com.globallogic.automation.pageobjects.pages.BasePage;

import com.globallogic.automation.pageobjects.objectFactory.DriverFactory;
import com.globallogic.automation.pageobjects.utilities.LoggerHelper;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.spockframework.util.Assert;

import java.net.MalformedURLException;
import java.util.List;

public abstract class BasePage {
    /**
     * AppiumDriver instance to be used by child classes.
     */
    protected AppiumDriver mDeviceDriver;

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    protected Logger log = LoggerHelper.getLogger(getClass());

    /**
     * Constant for 30 seconds timeout.
     */
    private static int TIMEOUT = 30;

    /**
     * Constructor for BasePage class,
     * instantiates appium driver instance to be used by child classes
     */
    protected BasePage() {
        try {
            log.info("Creating Device Driver...");
            mDeviceDriver = DriverFactory.getInstance().getDeviceDriver();
            log.info("Device Driver Created Successfully...");
        } catch (MalformedURLException e) {
            log.error(String.format("Error while creating Device Driver::%s", e.getMessage()));
        }
    }

    /**
     * Utility method to find and return the web element based on given param.
     *
     * @param id is the 'id' of web element.
     * @return instance of found WebElement,
     * null if not found any.
     */
    protected WebElement findElement(By id) {
        log.debug(String.format("findElement( %s )", id));
        try {
            log.info("Waiting for element to be visible...");
            WebDriverWait wait = new WebDriverWait(mDeviceDriver, TIMEOUT);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(id));
        } catch (Exception e) {
            log.error(String.format("Error while finding element:%s", e.getMessage()));
            Assert.fail(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Utility method to wait for the web element based on given param.
     *
     * @param id is the 'id' of web element.
     */
    protected void waitForElement(By id) {
        log.debug(String.format("waitForElement( %s )", id));
        WebDriverWait wait = new WebDriverWait(mDeviceDriver, TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(id));
    }

    protected List getElements(By elementId) {
        log.debug(String.format("getElements( elementId: %s )", elementId));
        waitForElement(elementId);
        return mDeviceDriver.findElements(elementId);
    }

}
