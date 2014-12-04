package net.github.rtc.util.annotation.validation;

import sun.misc.Regexp;

import java.util.Map;

/**
 * Created by Berdniky on 02.12.2014.
 */
@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Pattern {
    String value();
}
