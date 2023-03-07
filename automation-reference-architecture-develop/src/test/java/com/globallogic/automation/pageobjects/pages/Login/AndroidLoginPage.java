package com.globallogic.automation.pageobjects.pages.Login;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;


public class AndroidLoginPage extends BasePage implements ILoginPage{

    private By userName = By.id("edt_username");
    private By btnLogin = By.id("btn_login");
    @Override
    public void loginWithUserName(String username) {
        findElement(userName).sendKeys(username);
        findElement(btnLogin).click();
    }

    @Override
    public boolean isLoaded() {
        return false;
    }
}
