package annotations.PlatformTypes

import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE,ElementType.METHOD])

@ExtensionAnnotation(AndroidOnlyExtension)

@interface AndroidOnly {

}