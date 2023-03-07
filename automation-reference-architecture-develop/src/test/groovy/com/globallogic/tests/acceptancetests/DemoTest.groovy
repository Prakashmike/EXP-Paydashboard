package com.globallogic.tests.acceptancetests

import annotations.Details
import annotations.VersionTraces.VersionTraces
import basespec.BaseSpecification

@VersionTraces("4,4")
class DemoTest extends BaseSpecification {

    @Details('Click the Tab and Verify')
    def "Ability to click Demo"() {

        given: "Verify the Text and click the Tab"
        String value = "Access'ibility"
        String Option = "Accessibility Node Provider"
        mApp.DemoClick(value, Option)
        mApp.println("App was successfully launched")

//        IDemo demopage = (IDemo) mApp.navigationHandler.goToTab(Tabs.DemoPackage)
//        demopage.DemoClick(value,Option)


    }
}
