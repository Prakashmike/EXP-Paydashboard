package com.globallogic.automation.pageobjects.pages.DemoPage;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.By;

public class AndriodDemoPage extends BasePage implements IDemo {

    private By clickAccessbility = By.xpath("//android.widget.TextView[@content-desc=\"Access'ibility\"]");
    private By clkAccesNoProvider = By.xpath("//android.widget.TextView[@content-desc=\"Accessibility Node Provider\"]");


    @Override
    public void DemoClick(String value, String Option) {
        findElement(clickAccessbility).getText().contains(value);
        findElement(clickAccessbility).click();




        log.info("Accessability option was clicked successfully");
        findElement(clkAccesNoProvider).getText().contains(Option);

        findElement(clkAccesNoProvider).click();
        log.info("Accessability Node option was clicked successfully");

    }


    @Override
    public boolean isLoaded() {
        return false;
    }
}

