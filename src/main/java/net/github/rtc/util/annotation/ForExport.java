package net.github.rtc.util.annotation;

/**
 * Created by ivan on 21.05.14.
 * Annotation to detect what fields will be displayed for example in exel report
 */
@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ForExport {
    //Name field in table header
    String value();
    //If field is a class type contains list of fields that are marked with @ForExport in other entity
    String[] inculdeField() default {};
}
