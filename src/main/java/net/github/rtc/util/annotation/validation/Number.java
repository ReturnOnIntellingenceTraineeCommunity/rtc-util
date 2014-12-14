package net.github.rtc.util.annotation.validation;


import javax.validation.constraints.Pattern;

@java.lang.annotation.Target(java.lang.annotation.ElementType.FIELD)
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Pattern(regexp = "[0-9]")
public @interface Number {
}
