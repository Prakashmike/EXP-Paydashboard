package annotations.VersionTraces

import com.globallogic.automation.pageobjects.utilities.LoggerHelper
import org.apache.log4j.Logger
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo
import org.spockframework.runtime.model.Tag

class VersionTracesExtension extends AbstractAnnotationDrivenExtension<VersionTraces> {

    /**
     * For holding traced features
     */
    def tracedFeatures = [:]

    /**
     * Logger instance for writing log file and also printing it in the console
     */
    static Logger log = LoggerHelper.getLogger(VersionTracesExtension.class)


    @Override
    void visitSpecAnnotation(VersionTraces annotation, SpecInfo spec) {
        String testIDs = System.getProperty('testIDs')
        String platform = System.getProperty('platform')
        String deviceType = System.getProperty('deviceType')

        String idWithVersion
        if(annotation.value().split(",").size() > 1){
            int index = platform.equalsIgnoreCase("iPhone") ? 0 : 1
            idWithVersion = annotation.value().split(",")[index]
        }else{
            idWithVersion = annotation.value()
        }
        String id = idWithVersion.split("v")[0]

        if (testIDs != null) {
            spec.excluded = true
            testIDs.split(",").each { testId ->
                if (annotation.value().contains(testId.toString())){
                    spec.excluded = false
                }
            }
        }
        log.info("Spec = ${spec.name}, excluded = ${spec.excluded}")
        tracedFeatures[spec.name] = id
        spec.setNarrative(id)

        def dataMap = [:]
        dataMap.put('environment',"${platform} - ${deviceType}")
        Tag tag = new Tag("data","dataMap",dataMap)
        spec.addTag(tag)
    }

    @Override
    void visitSpec(SpecInfo spec) {
        super.visitSpec(spec)
        log.info("visitSpec = ${spec.name}")
        spec.addListener(new VersionTracesRunListener(tracedFeatures))
    }
}
