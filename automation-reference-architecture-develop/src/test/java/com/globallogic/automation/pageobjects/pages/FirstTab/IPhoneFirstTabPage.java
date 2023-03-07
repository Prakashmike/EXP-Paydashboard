package com.globallogic.automation.pageobjects.pages.FirstTab;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class IPhoneFirstTabPage extends BasePage implements IFirstTabPage {

    private final By celsiusTextField = By.id("celcius-textfield");
    private final By fahrenheitTextField = By.id("fahrenheit-textfield");
    private final By convertButton = By.id("convert-button");

    @Override
    public boolean isLoaded() {
        return findElement(convertButton).isDisplayed();
    }

    @Override
    public boolean isUserNameVisible(String username) {
        return findElement(By.id(username.toUpperCase())).isDisplayed();
    }

    @Override
    public void enterCelsiusValue(String value) {
        findElement(celsiusTextField).sendKeys(value);
    }

    @Override
    public void clickConvert() {
        findElement(convertButton).click();
    }

    @Override
    public boolean verifyConvertedValue(String value) {
        float floatValue = Float.parseFloat(value);
        float floatResult = Float.parseFloat(findElement(fahrenheitTextField).getText());
        return floatResult == floatValue;
    }
}
