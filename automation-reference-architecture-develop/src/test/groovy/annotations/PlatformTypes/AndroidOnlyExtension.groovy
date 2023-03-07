package annotations.PlatformTypes


import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo

class AndroidOnlyExtension extends AbstractAnnotationDrivenExtension<AndroidOnly> {

    @Override
    void visitFeatureAnnotation(AndroidOnly annotation, FeatureInfo feature) {
        String platform = System.getProperty('platform')
        System.out.println("visitFeatureAnnotation AndroidOnlyExtension " + platform)
        if(!platform.equalsIgnoreCase("Android")){
            feature.excluded = true
        }
    }
}
