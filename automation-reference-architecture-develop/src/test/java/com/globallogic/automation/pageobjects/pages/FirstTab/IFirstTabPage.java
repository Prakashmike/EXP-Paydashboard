package com.globallogic.automation.pageobjects.pages.FirstTab;

import com.globallogic.automation.pageobjects.pages.BasePage.IBaseInterface;

public interface IFirstTabPage extends IBaseInterface {

     boolean isUserNameVisible(String username);
     void enterCelsiusValue(String value);
     void clickConvert();


     boolean verifyConvertedValue(String value);
}
