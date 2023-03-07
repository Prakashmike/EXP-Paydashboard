////package com.globallogic.tests.acceptancetests
//
//import annotations.Details
//import annotations.VersionTraces.VersionTraces
//import basespec.BaseSpecification
//import com.globallogic.automation.pageobjects.enums.Tabs
//import com.globallogic.automation.pageobjects.pages.ThirdTab.IThirdTabPage
//
//@VersionTraces("1,1")
//class AboutTest extends BaseSpecification {
//
//    @Details ('Ability to show app version')
//    def "Ability to show app version" () {
//
//        given: "User is on logged in"
//        String username = "prakash"
//        mApp.loginWithUserName(username)
//
//        when: "User is on About Tab"
//        IThirdTabPage thirdTabPage = (IThirdTabPage) mApp.navigationHandler.goToTab(Tabs.ThirdTab)
//
//        then: "App version should be display"
//        assert thirdTabPage.verifyAppVersion(mApp.isiOSDevice()?"1.1.0":"1.1.2")
//    }
//}
