package com.globallogic.automation.pageobjects.pages.SecondTab;

import com.globallogic.automation.pageobjects.pages.BasePage.BasePage;
import org.openqa.selenium.*;

public class IPhoneSecondTabPage extends BasePage implements ISecondTabPage {

    private final By optionTableView = By.id("survey-table-view");
    private final By optionTableViewCell = By.id("survey-item-cell");
    private final By surveyItemTitle = By.id("survey-item-title");
    private final By surveyResultView = By.id("survey-result-label");

    @Override
    public boolean isLoaded() {
        return findElement(optionTableView).isDisplayed();
    }

    @Override
    public void selectOption(String value) {
        for (Object object: findElement(optionTableView).findElements(optionTableViewCell)) {
            WebElement cell = (WebElement) object;
            if (cell.findElement(surveyItemTitle).getText().equals(value)) {
                cell.click();
                break;
            }
        }
    }

    @Override
    public boolean isSelectedOptionDisplayed(String option) {
        return findElement(surveyResultView).getText().equals(option);
    }
}
