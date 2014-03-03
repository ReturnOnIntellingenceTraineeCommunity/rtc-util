package org.util.rtc.annotation;

import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface minlength {
    int value();
}
