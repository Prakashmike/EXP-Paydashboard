package annotations.PlatformTypes


import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo

class IPhoneOnlyExtension extends AbstractAnnotationDrivenExtension<iPhoneOnly> {

    @Override
    void visitFeatureAnnotation(iPhoneOnly annotation, FeatureInfo feature) {
        String platform = System.getProperty('platform')
        System.out.println("visitFeatureAnnotation IPhoneOnlyExtension " + platform)
        if(!platform.equalsIgnoreCase("iPhone")){
            feature.excluded = true
        }
    }
}
