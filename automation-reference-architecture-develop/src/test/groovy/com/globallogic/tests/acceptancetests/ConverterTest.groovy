//package com.globallogic.tests.acceptancetests
//
//import annotations.Details
//import annotations.VersionTraces.VersionTraces
//import basespec.BaseSpecification
//import com.globallogic.automation.pageobjects.enums.Tabs
//import com.globallogic.automation.pageobjects.pages.FirstTab.IFirstTabPage
//
//@VersionTraces("2,2")
//class ConverterTest extends BaseSpecification {
//
//    @Details ('Ability to convert Celsius to Fahrenheit')
//    def "Ability to convert Celsius to Fahrenheit" () {
//
//        given: "User is on Converter Tab"
//        String username = "prakash"
//        mApp.loginWithUserName(username)
//        IFirstTabPage firstTabPage = (IFirstTabPage) mApp.navigationHandler.goToTab(Tabs.FirstTab)
//        assert firstTabPage.isUserNameVisible(username)
//
//        when: "User enter value in Celsius"
//        firstTabPage.enterCelsiusValue("5")
//
//        and: "Click converter button"
//        firstTabPage.clickConvert()
//        closeKeyBoard()
//
//        then: "Value should be converted to Fahrenheit"
//        assert firstTabPage.verifyConvertedValue("33.4")
//    }
//}
