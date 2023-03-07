package com.globallogic.automation.pageobjects.pages.ThirdTab;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class IPhoneThirdTabPage extends BasePage implements IThirdTabPage {

    private final By logo = By.id("app-logo-view");
    private final By version = By.id("version-value-label");

    @Override
    public boolean isLoaded() {
        log.debug("isLoaded");
        return findElement(logo).isDisplayed();
    }

    @Override
    public boolean verifyAppVersion(String value) {
        return findElement(version).getText().equals(value);
    }
}
