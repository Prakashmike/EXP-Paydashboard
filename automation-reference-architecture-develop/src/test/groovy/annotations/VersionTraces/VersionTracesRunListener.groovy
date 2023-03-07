package annotations.VersionTraces

import com.globallogic.automation.pageobjects.utilities.LoggerHelper
import com.globallogic.automation.pageobjects.utilities.ScreenshotUtility
import org.apache.log4j.Logger
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.IterationInfo

/**
 * Listener for Version Traces
 */
class VersionTracesRunListener extends AbstractRunListener {
    /**
     * For holding traced features
     */
    def tracedFeatures = [:]
    /**
     * Currently running test id
     */
    String mCurrentTestId
    /**
     * Currently running scenario name
     */
    String mCurrentScenarioName

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    Logger log = LoggerHelper.getLogger(VersionTracesRunListener.class)

    /**
     * TraceRunListener constructor
     * @param tracedFeatures features map
     */
    VersionTracesRunListener(tracedFeatures) {
        this.tracedFeatures = tracedFeatures
    }

    /**
     * Called before execution of each feature test
     *
     * @param feature test to execute
     */
    @Override
    void beforeFeature(FeatureInfo feature) {
        super.beforeFeature(feature)
        log.info("Starting test = ${feature.name}")
        if (this.tracedFeatures.containsKey(feature.spec.name)) {
            mCurrentTestId = this.tracedFeatures[feature.spec.name]
        }
    }

    @Override
    void afterFeature(FeatureInfo feature) {
        super.afterFeature(feature)
        log.info("Completed test = ${feature.name}")
        String formattedScenarioName = mCurrentScenarioName.replaceAll(" ", "_").toLowerCase()

        //Stopping Screen recording
        ScreenshotUtility.stopScreenRecording(mCurrentTestId,formattedScenarioName)
    }

    /**
    * Called before execution of each iteration
    *
    * @param iteration to execute
    */
    @Override
    void beforeIteration(IterationInfo iteration) {
        super.beforeIteration(iteration)
        if (iteration.dataValues != null && iteration.dataValues.size() > 0) {
            mCurrentScenarioName = "${iteration.name} ${iteration.dataValues[0]}"
        } else {
            mCurrentScenarioName = iteration.name
        }
    }

    /**
     * Called when error occurred
     *
     * @param error ErrorInfo instance
     */
    @Override
    void error(ErrorInfo error) {
        super.error(error)
        log.error(error.getException().getMessage())
        if (mCurrentScenarioName != null) {
            String formattedScenarioName = mCurrentScenarioName.replaceAll(" ", "_").toLowerCase()
            ScreenshotUtility.captureScreenshot(mCurrentTestId, "error_${formattedScenarioName}")
        }
    }
}
