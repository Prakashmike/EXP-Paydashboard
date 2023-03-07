package com.globallogic.automation.pageobjects.objectFactory;

import com.globallogic.automation.pageobjects.enums.Platform;
import com.globallogic.automation.pageobjects.pages.DemoPage.AndriodDemoPage;
import com.globallogic.automation.pageobjects.pages.DemoPage.IDemo;
import com.globallogic.automation.pageobjects.pages.FirstTab.AndroidFirstTabPage;
import com.globallogic.automation.pageobjects.pages.FirstTab.IFirstTabPage;
import com.globallogic.automation.pageobjects.pages.FirstTab.IPhoneFirstTabPage;
import com.globallogic.automation.pageobjects.pages.Login.AndroidLoginPage;
import com.globallogic.automation.pageobjects.pages.Login.ILoginPage;
import com.globallogic.automation.pageobjects.pages.Login.IPhoneLoginPage;
import com.globallogic.automation.pageobjects.pages.Navigations.AndroidNavigationPage;
import com.globallogic.automation.pageobjects.pages.Navigations.INavigationPage;
import com.globallogic.automation.pageobjects.pages.Navigations.IPhoneNavigationPage;
import com.globallogic.automation.pageobjects.pages.SecondTab.AndroidSecondTabPage;
import com.globallogic.automation.pageobjects.pages.SecondTab.IPhoneSecondTabPage;
import com.globallogic.automation.pageobjects.pages.SecondTab.ISecondTabPage;
import com.globallogic.automation.pageobjects.pages.ThirdTab.AndroidThirdTabPage;
import com.globallogic.automation.pageobjects.pages.ThirdTab.IPhoneThirdTabPage;
import com.globallogic.automation.pageobjects.pages.ThirdTab.IThirdTabPage;
import com.globallogic.automation.pageobjects.utilities.LoggerHelper;
import org.apache.log4j.Logger;

public class PageObjectFactory {
    /**
     * Platform instance
     */
    private Platform mPlatform;

    /**
     * PageObjectFactory instance
     */
    private static PageObjectFactory ourInstance = new PageObjectFactory();

    /**
     * Gets the instance of PageObjectFactory
     *
     * @return PageObjectFactory instance
     */
    public static PageObjectFactory getInstance() {
        return ourInstance;
    }

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    static private Logger log = LoggerHelper.getLogger(PageObjectFactory.class);

    /**
     * PageObjectFactory constructor
     */
    private PageObjectFactory() {
        mPlatform = Platform.getPlatform(System.getProperty("platform"));
    }

    /**
     * Getter for platform
     *
     * @return platform
     */
    public Platform getPlatform() {
        return mPlatform;
    }

    /**
     * Gets NavigationPage object based on platform
     *
     * @return NavigationPage object
     */
    public INavigationPage getNavigationPage() {
        switch (mPlatform) {
            case Android:
                return new AndroidNavigationPage();
            case iPhone:
                return new IPhoneNavigationPage();
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }

    /**
     * Gets LoginPage object based on platform
     *
     * @return LoginPage object
     */
    public ILoginPage getLoginPage() {
        switch (mPlatform) {
            case Android:
                return new AndroidLoginPage();
            case iPhone:
                return new IPhoneLoginPage();
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }

    /**
     * Gets FirstTabPage object based on platform
     *
     * @return FirstTabPage object
     */
    public IFirstTabPage getFirstTabPage() {
        switch (mPlatform) {
            case Android:
                return new AndroidFirstTabPage();
            case iPhone:
                return new IPhoneFirstTabPage();
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }

    /**
     * Gets SecondTabPage object based on platform
     *
     * @return SecondTabPage object
     */
    public ISecondTabPage getSecondTabPage() {
        switch (mPlatform) {
            case Android:
                return new AndroidSecondTabPage();
            case iPhone:
                return new IPhoneSecondTabPage();
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }

    /**
     * Gets ThirdTabPage object based on platform
     *
     * @return ThirdTabPage object
     */
    public IThirdTabPage getThirdTabPage() {
        switch (mPlatform) {
            case Android:
                return new AndroidThirdTabPage();
            case iPhone:
                return new IPhoneThirdTabPage();
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }

    public IDemo getDemopage() {
        switch (mPlatform) {
            case Android:
                return new AndriodDemoPage();
            default:
                log.error("Platform not found");
                throw new IllegalArgumentException("Platform not found");
        }
    }
}
