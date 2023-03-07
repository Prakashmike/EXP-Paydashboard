package basespec

import ReportProcessingTool.ReportGenerator
import com.globallogic.automation.pageobjects.Application
import com.globallogic.automation.pageobjects.objectFactory.DriverFactory
import com.globallogic.automation.pageobjects.utilities.*
import org.apache.log4j.Logger
import spock.lang.Specification

/**
 * Base class for all test specifications
 */
class BaseSpecification extends Specification {

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    static Logger log = LoggerHelper.getLogger(BaseSpecification.class)

    /**
     * Application instance
     */
    static Application mApp

    /**
     * Called at the start of each AT
     */
    def setup() {
        log.info("Setting up test environment")
        mApp = Application.getInstance()
        DriverFactory.getInstance().getDeviceDriver().launchApp()
        //Starting Screen recording
        ScreenshotUtility.startScreenRecording()
    }

    /**
     * Called at the end of each AT
     */
    def cleanup() {
        log.info("Performed clean up")
        DriverFactory.getInstance().getDeviceDriver().closeApp()
    }

    /**
     * Called at the start of each specification
     */
    def setupSpec() {
        log.info("Setting up the spec class")
        //Custom report generation for previous spock report
        ReportGenerator.main()
        if (mApp == null) {
            AppiumUtility.startAppium()
        }
    }

    /**
     * Called at the end of each specification
     */
    def cleanupSpec() {
        log.info("Performed cleanup after execution completed for the spec class")
    }

    /**
     * Method to close Keyboard
     */
    def closeKeyBoard(){
        log.info("closeKeyBoard()")
        DriverFactory.getInstance().getDeviceDriver().hideKeyboard()
    }
}
