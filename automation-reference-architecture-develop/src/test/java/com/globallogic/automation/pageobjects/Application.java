package com.globallogic.automation.pageobjects;

import com.globallogic.automation.pageobjects.enums.Platform;
import com.globallogic.automation.pageobjects.objectFactory.PageObjectFactory;
import com.globallogic.automation.pageobjects.pages.DemoPage.IDemo;
import com.globallogic.automation.pageobjects.pages.Login.ILoginPage;
import com.globallogic.automation.pageobjects.pages.Navigations.NavigationHandler;

public class Application {
    /**
     * Application instance
     */
    private static Application ourInstance = new Application();

    /**
     * PageObjectfactory instance that shall be accessible to all features to get page object based on provided
     * device type.
     */
    public PageObjectFactory mPageObjectFactory;

    /**
     * Gets the instance of Application
     *
     * @return Application instance
     */
    public static Application getInstance() {
        return ourInstance;
    }

    /**
     * Getter for Page object factory
     *
     * @return instance of PageObjectFactory
     */
    public PageObjectFactory getPageObjectFactory() {
        return mPageObjectFactory;
    }

    /**
     * Application constructor
     */
    private Application() {
        mPageObjectFactory = PageObjectFactory.getInstance();
    }

    public NavigationHandler getNavigationHandler(){
        return new NavigationHandler(mPageObjectFactory);
    }

    /**
     * Login with username
     * @param username is username entered
     */
    public ILoginPage loginWithUserName(String username){
       ILoginPage loginPage = mPageObjectFactory.getLoginPage();
       loginPage.loginWithUserName(username);
       return loginPage;
    }

    /**
     *
     * @param value
     * @param Option
     * @return
     */
    public  IDemo DemoClick(String value,String Option){
        IDemo demo = mPageObjectFactory.getDemopage();
        demo.DemoClick(value, Option);
        return demo;
    }


    /**
     * Check whether the device is iOS or not
     *
     * @return true if device is iOS otherwise false
     */
    public boolean isiOSDevice() {
        return mPageObjectFactory.getPlatform().equals(Platform.iPhone);
    }
}
