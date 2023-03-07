package com.globallogic.automation.pageobjects.pages.ThirdTab;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AndroidThirdTabPage extends BasePage implements IThirdTabPage {

    private By logo = By.id("logo");
    private By version = By.id("text_about");

    @Override
    public boolean isLoaded() {
        log.debug("isLoaded");
        return findElement(logo).isDisplayed();
    }

    @Override
    public boolean verifyAppVersion(String value) {
        return findElement(version).getText().contains(value);
    }
}
