package annotations

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.Tag

class DetailsExtension extends AbstractAnnotationDrivenExtension<Details> {

    void visitFeatureAnnotation(Details details, FeatureInfo feature) {

        def dataMap = [:]

        dataMap.put('user', (System.getProperty('executedBy') ?: System.getProperty('user.name')))
        if (System.getProperty("device")) {
            dataMap.put('device', System.getProperty("device"))
        }

        Tag combined = new Tag("data", "dataMap", dataMap)
        feature.addTag(combined)
        feature.setName(details.value())
    }
}
