package ReportProcessingTool

/**
 * This class helps in removing all the unnecessary characters from json object.
 */

def spockJsonName = System.getProperty('spockJsonName','spock-report.json')
def customSpockJsonName =  System.getProperty('customSpockJsonName','Custom-spock-report.json')

def systemSpockJsonLocation = System.getProperty('spockJsonFile')
def systemCustomSpockJsonLocation = System.getProperty('customSpockJsonDir')

//search for json file in root path (where src file is) and get its data or use path full path name given in system properties
def spockJsonData = systemSpockJsonLocation ? new File(systemSpockJsonLocation) :
        TemplateUtility.searchForFile(spockJsonName)

println "SPOCK JSON File path: " + spockJsonData.path
System.setProperty('spockJsonPath', spockJsonData.getParent())
String fileSeparator = System.getProperty("file.separator")

//Specify Custom spock Json location to be in the same directory as Spock's Json file
//if destination not provided via system properties
def dest = systemCustomSpockJsonLocation ? new File(systemCustomSpockJsonLocation + fileSeparator + customSpockJsonName) :
        new File(spockJsonData.getParent() + fileSeparator + customSpockJsonName)

if(!systemCustomSpockJsonLocation){System.setProperty('customSpockJsonDir',dest.getParent())}
println 'CUSTOM SPOCK JSON path: ' + dest.path
def TestsInSrcCount = spockJsonData.readLines().size()
println 'Number of lines of data read in Spock Json: ' + TestsInSrcCount
def TISCounter = 0
def skip = false
def squareBracesCounter = 0
def backSlash = '\\\\'
def forwardSlashQuote = '/"'
def loadLogFileOpen = 'loadLogFile\\(\\['
def loadLogFileClose = ']\\)'

if(fileSeparator.contentEquals("\\")){
    fileSeparator = "\\\\"
}

dest.withWriter { destWriter ->
    destWriter << '['
    spockJsonData.eachLine { mLine ->
        if (mLine.contains('"output": [')){ skip = true }

        if(skip && mLine.contains('[')){
            squareBracesCounter++
        }

        if (!skip){
            if (TISCounter > TestsInSrcCount - 8) {
                destWriter << mLine.replaceAll(backSlash, fileSeparator)
                        .replaceAll(forwardSlashQuote, fileSeparator)
                        .replaceAll(loadLogFileOpen, '')
                        .replaceAll(loadLogFileClose, '') + System.getProperty("line.separator")
            } else {
                destWriter << mLine.replaceAll(backSlash, fileSeparator)
                        .replaceAll(forwardSlashQuote, fileSeparator)
                        .replaceAll(loadLogFileOpen, '')
                        .replaceAll(loadLogFileClose, ',') + System.getProperty("line.separator")
            }}
        TISCounter++
        if(skip && mLine.contains(']')){
            squareBracesCounter--
        }
        if (skip && squareBracesCounter == 0 && mLine.contains('],')){ skip = false }
    }
    destWriter << ']'
}
