package com.globallogic.automation.pageobjects.pages.Navigations;

import com.globallogic.automation.pageobjects.enums.Tabs;
import com.globallogic.automation.pageobjects.pages.BasePage.IBaseInterface;

public interface INavigationPage {

    /**
     * To navigate between different tabs
     *
     * @param tab tab to navigate
     */
    void goToTab(Tabs tab);
}
