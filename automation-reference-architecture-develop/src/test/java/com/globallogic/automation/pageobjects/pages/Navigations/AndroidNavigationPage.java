package com.globallogic.automation.pageobjects.pages.Navigations;

import com.globallogic.automation.pageobjects.enums.Tabs;
import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class AndroidNavigationPage extends BasePage implements INavigationPage {

    private By firstTab = By.id("navigation_converter");
    private By secondTab = By.id("navigation_survey");
    private By thirdTab = By.id("navigation_about");

    //private By DemoPackage=By.xpath("//android.widget.TextView[@content-desc=\"Access'ibility\"]");
    @Override
    public void goToTab(Tabs tab) {
        switch (tab){
            case FirstTab:
                findElement(firstTab).click();
                break;
            case SecondTab:
                findElement(secondTab).click();
                break;
            case ThirdTab:
                findElement(thirdTab).click();
                break;
            //case DemoPackage:
               // findElement(DemoPackage).click();
            default:
                log.error("Tab not found");
                throw new IllegalArgumentException("Tab not found");
        }
    }
}
