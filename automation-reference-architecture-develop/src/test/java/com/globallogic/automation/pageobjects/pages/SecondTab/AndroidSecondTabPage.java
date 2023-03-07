package com.globallogic.automation.pageobjects.pages.SecondTab;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import com.globallogic.automation.pageobjects.pages.FirstTab.IFirstTabPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AndroidSecondTabPage extends BasePage implements ISecondTabPage {

    private By textView = By.id("txtView");
    private By spinner = By.id("spinner");
    private By optionList = By.id("select_dialog_listview");
    private By option = By.id("android:id/text1");
    private By surveyText = By.id("text_survey");

    @Override
    public boolean isLoaded() {
        log.debug("isLoaded");
        return findElement(textView).isDisplayed();
    }

    @Override
    public void selectOption(String value) {
        findElement(spinner).click();
        List arr = getElements(option);
        for (Object obj : arr) {
            WebElement element = (WebElement) obj;
            if (element.getText().contains(value)) {
                element.click();
                break;
            }
        }
    }

    @Override
    public boolean isSelectedOptionDisplayed(String option) {
        return findElement(surveyText).isDisplayed() && findElement(surveyText).getText().contains(option);
    }
}
