//package com.globallogic.tests.acceptancetests
//
//import annotations.Details
//import annotations.VersionTraces.VersionTraces
//import basespec.BaseSpecification
//import com.globallogic.automation.pageobjects.enums.Tabs
//import com.globallogic.automation.pageobjects.pages.SecondTab.ISecondTabPage
//
//@VersionTraces("3,3")
//class SurveyTest extends BaseSpecification {
//
//    @Details ('Ability to select options')
//    def "Ability to select options" () {
//
//        given: "User is on Survey Tab"
//        String username = "prakash"
//        mApp.loginWithUserName(username)
//        ISecondTabPage secondTabPage = (ISecondTabPage) mApp.navigationHandler.goToTab(Tabs.SecondTab)
//
//        when: "User select option"
//        String option = "I like Celsius"
//        secondTabPage.selectOption(option)
//
//        then: "Selected option should be display"
//        assert secondTabPage.isSelectedOptionDisplayed(option)
//    }
//}
