package com.globallogic.automation.pageobjects.pages.Navigations;

import com.globallogic.automation.pageobjects.enums.Tabs;
import com.globallogic.automation.pageobjects.objectFactory.PageObjectFactory;
import com.globallogic.automation.pageobjects.pages.BasePage.IBaseInterface;
import com.globallogic.automation.pageobjects.utilities.LoggerHelper;
import org.apache.log4j.Logger;

public class NavigationHandler {
    /**
     * PageObjectfactory instance that shall be accessible to all features to get page object based on provided device type.
     */
    PageObjectFactory mPageObjectFactory;

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    Logger log = LoggerHelper.getLogger(getClass());

    private INavigationPage navigationPage;

    public NavigationHandler(PageObjectFactory pageObjectFactory){
        mPageObjectFactory = pageObjectFactory;
        navigationPage = pageObjectFactory.getNavigationPage();
    }

   public IBaseInterface goToTab(Tabs tab){
        navigationPage.goToTab(tab);
        switch (tab){
            case FirstTab:
                return mPageObjectFactory.getFirstTabPage();
            case SecondTab:
                return mPageObjectFactory.getSecondTabPage();
            case ThirdTab:
                return mPageObjectFactory.getThirdTabPage();
            //case DemoPackage:
                //return mPageObjectFactory.getDemopage();
            default:
                log.error("Tab not found");
                throw new IllegalArgumentException("Tab not found");
        }
    }
}
