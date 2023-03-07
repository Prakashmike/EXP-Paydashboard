import annotations.Details

runner {
        include Details
}

report {
    enabled true
    String fileSeparator = System.getProperty("file.separator")
    logFileDir 'build' + fileSeparator + 'resources' + fileSeparator + 'test' + fileSeparator + 'SpockReport'
    logFileName 'spock-report.json'
    logFileSuffix ''
}