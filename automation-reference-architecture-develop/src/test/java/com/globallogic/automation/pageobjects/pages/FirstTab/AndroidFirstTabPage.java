package com.globallogic.automation.pageobjects.pages.FirstTab;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class AndroidFirstTabPage extends BasePage implements IFirstTabPage {

    private By title = By.id("toolbar_title");
    private By celsiusText = By.id("edt_celsius");

    private By fahrenheitText = By.id("text_converter");
    private By converterButton = By.id("btn_convert");
    @Override
    public boolean isLoaded() {
        return findElement(converterButton).isDisplayed();
    }

    @Override
    public boolean isUserNameVisible(String username) {
        return findElement(title).isDisplayed() && findElement(title).getText().equals(username);
    }

    @Override
    public void enterCelsiusValue(String value) {
        findElement(celsiusText).sendKeys(value);
    }

    @Override
    public void clickConvert() {
        findElement(converterButton).click();
    }

    @Override
    public boolean verifyConvertedValue(String value) {
        float floatValue = Float.parseFloat(value);
        float floatResult = Float.parseFloat(findElement(fahrenheitText).getText());
        return floatResult == floatValue;
    }

}
