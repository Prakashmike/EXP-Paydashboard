package ReportProcessingTool

import groovy.io.FileType
import groovy.io.FileVisitResult

/**
 * This class is the main utility to execute any common and data processing task.
 */
class TemplateUtility {

    /**
     * Get current date
     */
    public static def getDate = { mDate ->
        if (mDate != null) {
            new Date(mDate)
        }
    }

    /**
     * Converts millisecond value into string representation
     */
    public static def getDuration = { mTotal ->
        def hours = mTotal.intdiv(3600000)
        def hourLeftOver = mTotal % 3600000
        def minutes = hourLeftOver.intdiv(60000)
        def minuteLeftOver = hourLeftOver % 60000
        def seconds = minuteLeftOver.intdiv(1000)
        def miliseconds = minuteLeftOver % 1000
        if (hours <= 0) {
            minutes + ' minutes ' + seconds + '.' + miliseconds + ' seconds'
        } else if (hours <= 0 && minutes <= 0) {
            seconds + '.' + miliseconds + ' seconds'
        } else {
            hours + ' hours ' + minutes + ' minutes ' + seconds + '.' + miliseconds + ' seconds'
        }
    }

    /**
     * Finds all test packages
     */
    public static def getPackages = { mJsonList ->
        def packageList = []
        mJsonList.each {
            if (!(packageList.contains(it.'name'))) {
                packageList.add(it.'name')
            }
        }
        packageList
    }

    /**
     * Checks if GherkinStep passed/failed
     */
    public static def isGherkinStepPassed = { mGherkinStep ->
        if (mGherkinStep.contains(": PASSED")) {
            "PASSED"
        } else if (mGherkinStep.contains(": WARNING")) {
            "warning"
        } else {
            "FAILED"
        }
    }

    /**
     * Checks if object has data
     */
    public static def isNoData = { mCheckObject ->
        mCheckObject.size() <= 0
    }

    /**
     * Removes all skipped tests
     */
    public static def removeSkippedTests = { mJsonList ->
        def unsortedList = []
        mJsonList.each {
            if (!(it.result.contains("skipped"))) {
                unsortedList.add(it)
            }
        }
        unsortedList
    }

    /**
     * Parses the field name Output to get data for binding
     */
    public static def scanOutput = { mGherkin, mResult, mExceptions ->
        def sortedWhereList = []
        def gherkinStatement = []
        int whereCounter = 0
        int gherkinCounter = 0
        int narrativeAndCount = mGherkin.findAll { it.startsWith('And ') }.each {}.size()
        int logAndCount = 0
        boolean containsGiven = false
        boolean containsWhen = false
        boolean containsThen = false
        boolean passed = mResult ? mResult.contains('passed') : false
        boolean failed = mResult ? mResult.contains('failed') : false

        /**
         * Organizes the gherkin steps
         */
        if (mGherkin) {
            mGherkin.each {
                if ((it =~ /Given/) || (it =~ /When/) || (it =~ /Then/) || (it =~ /And/)) {
                    if (it =~ /Given/) {
                        containsGiven = true
                    }
                    if (it =~ /When/) {
                        containsWhen = true
                    }
                    if (it =~ /Then/) {
                        containsThen = true
                    }
                    if (it =~ /And/) {
                        logAndCount++
                    }
                    if (sortedWhereList[0] != null && (it =~ /Given/)) {
                        gherkinStatement.add(sortedWhereList[whereCounter])
                        whereCounter++
                    }
                    if (passed){
                        gherkinStatement.add(it + ": PASSED")
                    } else if (failed){
                        gherkinStatement.add(it + ": FAILED")
                    }
                    gherkinCounter++
                }
            }
        } else {
            gherkinStatement.add("UNABLE TO FIND GHERKIN STATEMENTS - possible environment or test setup failure - see logs")
            if (mExceptions) {
                gherkinStatement.add("EXCEPTION OUTPUT:  $mExceptions")
            }
        }

        /**
         * Warning for missing gherkin step logs
         */
        if (passed || failed) {
            if (!containsGiven) {
                gherkinStatement.add("Test Script Error: Missing logGiven : WARNING")
            }
            if (!containsWhen) {
                gherkinStatement.add("Test Script Error: Missing logWhen : WARNING")
            }
            if (!containsThen) {
                gherkinStatement.add("Test Script Error: Missing logThen : WARNING")
            }
            int diffAndCount = narrativeAndCount - logAndCount / (whereCounter == 0 ? 1 : whereCounter)
            while (diffAndCount--) {
               gherkinStatement.add("Test Script Error: Missing logAnd : WARNING")
            }
        }

        gherkinStatement
    }

    /**
     * Separate the gherkins from the narrative
     */
    public static def splitGherkin = { mNarrative ->
        if (mNarrative) {
            mNarrative.split('/n')
        } else {
            "No gherkin statements found - test start up may have failed"
        }

    }

    /**
     * Function to search for given file while excluding given directories in search
     * @excludeDirs comma separated names of folders to skip while searching
     * @param fileToFind
     * @return found file
     */
    static def searchForFile(def excludeDirs = [],
                             String fileToFind) {

        //Get full path that leads up to the src subdirectory (i.e return path without the src subdirectory)
        def rootPath = (System.properties.'user.dir')
        println("Searching for $fileToFind starting in this path: ${rootPath} ")

        File fileWithPath
        def logSearchDir = new File(rootPath)


        logSearchDir.traverse(
                type: FileType.FILES,
                preDir: { if (it.name in excludeDirs) return FileVisitResult.SKIP_SUBTREE },
                nameFilter: /${fileToFind}/
        ) { if (!fileWithPath) fileWithPath = it }

        return fileWithPath

    }

    /**
     * Function to copy html and css files from resource folder to the given destination files
     * @param destHtml the destination html file to copy to
     * @param destCss the destination css to copy to
     * @return N/A
     */
    def copyToFileSystemHTMLandCSS(File destHtml, File destCss) {
        String fileSeparator = System.getProperty("file.separator")

        ClassLoader classLoader = getClass().getClassLoader()
        classLoader.getResource("CustomSpockReport" + fileSeparator + "StructureTemplate.html").withInputStream { htmlResourceData ->
            destHtml.newWriter().withWriter { templateDestData ->
                templateDestData << htmlResourceData
            }
        }

        classLoader.getResource("CustomSpockReport" + fileSeparator + "StructureTemplate.css").withInputStream { cssResourceData ->
            destCss.newWriter().withWriter { cssTemplateDest ->
                cssTemplateDest << cssResourceData
            }
        }
    }
}
