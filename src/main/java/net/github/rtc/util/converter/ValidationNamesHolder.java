package net.github.rtc.util.converter;

import net.github.rtc.util.annotation.validation.Number;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

@Component
public class ValidationNamesHolder implements InitializingBean {

    private final Map<Class<? extends Annotation>, String> annotationValidationNames = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        annotationValidationNames.put(NotNull.class, "required");
        annotationValidationNames.put(NotEmpty.class, "required");
        annotationValidationNames.put(Email.class, "email");
        annotationValidationNames.put(Pattern.class, "pattern");
        annotationValidationNames.put(Number.class, "number");
    }

    public String getName(Class<? extends Annotation> aClass){
      return annotationValidationNames.get(aClass);
    }
}
