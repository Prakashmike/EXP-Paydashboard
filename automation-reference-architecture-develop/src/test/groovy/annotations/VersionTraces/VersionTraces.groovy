package annotations.VersionTraces

import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])

@ExtensionAnnotation(VersionTracesExtension)
/**
 * Annotation to track the test id and its version.
 */
@interface VersionTraces {
    String value() default "0000v00,0000v00"
}