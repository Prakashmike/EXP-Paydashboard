package com.globallogic.automation.pageobjects.pages.Navigations;

import com.globallogic.automation.pageobjects.enums.Tabs;
import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class IPhoneNavigationPage extends BasePage implements INavigationPage {

    private final By converterTab = By.id("Converter");
    private final By surveyTab = By.id("Survey");
    private final By aboutTab = By.id("About");

    @Override
    public void goToTab(Tabs tab) {
        switch (tab){
            case FirstTab:
                findElement(converterTab).click();
                break;
            case SecondTab:
                findElement(surveyTab).click();
                break;
            case ThirdTab:
                findElement(aboutTab).click();
                break;
            default:
                log.error("Tab not found");
                throw new IllegalArgumentException("Tab not found");
        }
    }
}
