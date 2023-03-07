package com.globallogic.automation.pageobjects.pages.Login;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class IPhoneLoginPage extends BasePage implements ILoginPage {

    private final By userName = By.id("username-textfield");
    private final By loginButton = By.id("signin-button");

    @Override
    public void loginWithUserName(String username) {
        findElement(userName).sendKeys(username);
        findElement(loginButton).click();
    }

    @Override
    public boolean isLoaded() {
        return false;
    }
}
