package net.github.rtc.util.annotation;

import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
@java.lang.annotation.Retention(RetentionPolicy.RUNTIME)
public @interface Minlength {
    int value();
}
