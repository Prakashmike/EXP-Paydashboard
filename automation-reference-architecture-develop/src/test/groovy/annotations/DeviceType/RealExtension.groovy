package annotations.DeviceType

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo

class RealExtension extends AbstractAnnotationDrivenExtension<Real> {

    @Override
    void visitFeatureAnnotation(Real annotation, FeatureInfo feature) {
        String deviceType = System.getProperty('deviceType')
        System.out.println("visitFeatureAnnotation RealExtension " + deviceType)
        if(deviceType.equalsIgnoreCase("Simulator")){
            feature.excluded = true
        }
    }
}
