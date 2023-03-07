package com.globallogic.automation.pageobjects.pages.SecondTab;

import com.globallogic.automation.pageobjects.pages.BasePage.IBaseInterface;

public interface ISecondTabPage extends IBaseInterface {

    void selectOption(String value);
    boolean isSelectedOptionDisplayed(String option);
}
