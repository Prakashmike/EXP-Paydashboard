package ReportProcessingTool

import groovy.json.JsonSlurper
import groovy.text.SimpleTemplateEngine

import java.text.SimpleDateFormat

/**
 * This class is the main processor of json objects in order to populate them into a understandable html page.
 */
String fileSeparator = System.getProperty("file.separator")

def rootPath = (System.properties.'user.dir').toString()
def systemCustomSpockJsonDir = System.getProperty('customSpockJsonDir', rootPath)
def customSpockJsonName = System.getProperty('customSpockJsonName', 'Custom-spock-report.json')
def templateName = 'StructureTemplate.html'
def cssName = 'StructureTemplate.css'
def customSpockJsonDir = systemCustomSpockJsonDir + fileSeparator
//Copy html template and CSS From resources to the same directory in file system as Custom spock json file
def destHtmlTemplate = new File(customSpockJsonDir + templateName)
def destCss = new File(customSpockJsonDir + cssName)
new TemplateUtility().copyToFileSystemHTMLandCSS(destHtmlTemplate, destCss)

//Map to bind variables to html template
def binding = [:]

//Retrieve Custom Spock JSON for processing and check data
def jsonFile = new File(customSpockJsonDir + customSpockJsonName)
def noData = TemplateUtility.isNoData(jsonFile)

/**
 * If there is data, then do JSON parsing and template binding
 */
if (!noData) {

    def slurper = new JsonSlurper()
    def jsonList = slurper.parse(jsonFile)

    /**
     * Handles sorting the JSON objects by package name
     */
    def unsortedList = TemplateUtility.removeSkippedTests(jsonList)

    noData = TemplateUtility.isNoData(jsonList)

    if (!noData) {
        def packagesList = TemplateUtility.getPackages(unsortedList)

        def ptStatusTrackerList = []
        def ptStatus = "PASSED"
        def ptTracker = []
        def ptCounter = 0
        def sortedList = []
        def ptPassedCounter = 0
        def ptFailedCounter = 0
        def ptPassedList = []
        def ptFailedList = []
        def testPassCount = 0
        def testFailCount = 0

        for (int ptidx = 0; ptidx < packagesList.size(); ptidx++) {
            unsortedList.each {
                if (it.'name' == packagesList[ptidx]) {
                    sortedList.add(it)
                }
            }
        }

        def nameList = []
        def codebeamerIDList = []
        def scenarioList = []
        def startList = []
        def endList = []
        def durationList = []
        def totalTimeCount = 0
        def resultList = []
        def gherkinList = []
        def narrativeList = []
        def exceptionList = []
        def packageList = []
        def executedBy = ""
        def environment = ""

        sortedList.each { mJSONObj ->
            ptStatus = "PASSED"
            ptCounter = 0
            ptPassedCounter = 0
            ptFailedCounter = 0
            codebeamerIDList.add(mJSONObj?.narrative)
            narrativeList.add(mJSONObj?.narrative)
            def specTagValues = mJSONObj?.tags?.flatten()?.value
            environment = specTagValues?.find { it?.find { it?.key == 'environment' } }?.environment
            //Get device meta data from tags if tags available
            for (int i = 0; i < mJSONObj?.features?.size(); i++){
                def feature = mJSONObj?.features[i]
                def tagValues = feature.tags?.flatten()?.value

                ptCounter++
                if (feature.result.toUpperCase() == "FAILED") {
                    ptStatus = "FAILED"
                    ptFailedCounter++
                } else {
                    ptPassedCounter++
                }

                nameList.add(mJSONObj?.name)
                if (feature) {
                    scenarioList.add(feature.name)
                } else {
                    scenarioList.add(["UNABLE TO FIND TEST OUTPUT  - possible environment or test setup failure - see logs"])
                }
                startList.add(mJSONObj?.start)
                endList.add(mJSONObj?.end)
                durationList.add(TemplateUtility.getDuration(mJSONObj?.end - mJSONObj?.start))
                totalTimeCount = totalTimeCount + (feature.end - feature.start)
                resultList.add(feature.result?.toUpperCase())
                gherkinList.add(TemplateUtility.scanOutput(
                        TemplateUtility.splitGherkin(feature.narrative),
                        feature.result,
                        mJSONObj?.exceptions)
                )
                if (feature) {
                    exceptionList.add(feature?.exceptions)
                } else {
                    (mJSONObj?.exceptions) ? exceptionList.add(mJSONObj?.exceptions)
                            : exceptionList.add("Warning: Could not detect test output - possible environment or test setup failure - see logs")
                }
                packageList.add(mJSONObj?.'name')
                executedBy = tagValues?.find { it?.find { it?.key == 'user' } }?.user
            }

            if (ptPassedCounter == mJSONObj?.features?.size()){
                testPassCount++
            } else {
                testFailCount++
            }

            if (!mJSONObj?.features) {
                ptStatus = mJSONObj?.result?.toUpperCase()
            }

            ptTracker.add(ptCounter)
            ptStatusTrackerList.add(ptStatus)
            ptPassedList.add(ptPassedCounter)
            ptFailedList.add(ptFailedCounter)
        }

        def jsonListSize = nameList.size()

        /**
         * Handles binding variables to template for injection
         */
        binding = [
                'noData'             : noData,
                'nameList'           : nameList,
                'codebeamerIDList'   : codebeamerIDList,
                'scenarioList'       : scenarioList,
                'startList'          : startList,
                'endList'            : endList,
                'durationList'       : durationList,
                'resultList'         : resultList,
                'jsonListSize'       : jsonListSize,
                'executedBy'         : executedBy,
                'totalTime'          : TemplateUtility.getDuration(totalTimeCount),
                'gherkinList'        : gherkinList,
                'getDate'            : TemplateUtility.getDate,
                'narrativeList'      : narrativeList,
                'exceptionList'      : exceptionList,
                'packageList'        : packageList,
                'packageTracker'     : packagesList,
                'ptTracker'          : ptTracker,
                'ptStatusTracker'    : ptStatusTrackerList,
                'isGherkinStepPassed': TemplateUtility.isGherkinStepPassed,
                'ptPassedList'       : ptPassedList,
                'ptFailedList'       : ptFailedList,
                'testPassCount'      : testPassCount,
                'testFailCount'      : testFailCount,
                'environment'        : environment
        ]
    }
}

if (noData) {
    binding = [
            'noData': noData
    ]
}

/**
 * Handles setting up the Engine to create a report
 */
def engine = new SimpleTemplateEngine()
def template = engine.createTemplate(destHtmlTemplate).make(binding)

/**
 * Handles writing the summary spock report to an html file
 */
def outputFileName = 'CustomSpockSummary'
def extension = '.html'

def date = new Date()
def sdf = new SimpleDateFormat("-MM-dd-yyyy_HH_mm_ss")
def htmlReport = new File(customSpockJsonDir + outputFileName + sdf.format(date) + extension)
htmlReport.write(template.toString())

println("CUSTOM SPOCK REPORT: " + htmlReport)



//def engine1 = new SimpleTemplateEngine()
//def template1 = engine1.createTemplate()
//
//def outputFileName1 = 'CustomSpockSummary'
//def extension1 = '.txt'
//
//def date1 = new Date()
//def sdf1 = new SimpleDateFormat("-MM-dd-yyyy_HH_mm_ss")
//def txtreport= new File(customSpockJsonDir + outputFileName1 + sdf.format(date) + extension1)
//txtreport.write(template.toString())
//
//println("CUSTOM SPOCK REPORT: " + txtreport)



