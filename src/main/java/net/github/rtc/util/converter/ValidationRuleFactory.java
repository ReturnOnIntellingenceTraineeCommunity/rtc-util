package net.github.rtc.util.converter;


import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//building rules and messages json for jquery validate
@Component
public class ValidationRuleFactory implements InitializingBean {

    @Autowired
    private ValidationNamesHolder namesHolder;

    @Autowired
    @Qualifier("eMessageSource")
    private MessageSource eMessageSource;

    private final Map<Class<? extends Annotation>, AnnotationRuleBuilder> annotationConverters = new HashMap<>();

    private  Locale locale;

    private interface AnnotationRuleBuilder {
        void addRule(Map<String, Object> rules, Map<String, String> messages, Annotation annotation);
    }

    public void setLocale(final Locale locale) {
        this.locale = locale;
    }

    //Set custom logic for validation annotation rules and messages
    @Override
    public void afterPropertiesSet() throws Exception {
        annotationConverters.put(Min.class, new AnnotationRuleBuilder() {
            @Override
            public void addRule(final Map<String, Object> rules, Map<String, String> messages,
              final Annotation annotation) {
                final String ruleName = "min";
                Object value =  ((Min) annotation).value();
                rules.put(ruleName, value);
                messages.put(ruleName, getMessage(ruleName, value, annotation));
            }
        });
        annotationConverters.put(Max.class, new  AnnotationRuleBuilder() {
            @Override
            public void addRule(final Map<String, Object> rules, Map<String, String> messages,
              final Annotation annotation) {
                final String ruleName = "max";
                Object value =  ((Max) annotation).value();
                rules.put(ruleName, value);
                messages.put(ruleName, getMessage(ruleName, value, annotation));
            }
        });
        annotationConverters.put(Length.class, new  AnnotationRuleBuilder() {
            @Override
            public void addRule(final Map<String, Object> rules, Map<String, String> messages,
              final Annotation annotation) {
                int maxValue =  ((Length) annotation).max();
                int minValue =  ((Length) annotation).min();
                String maxLength = "maxlength";
                String minLength = "minlength";
                if(maxValue!=0){
                    rules.put(maxLength, maxValue);
                    messages.put(maxLength, getMessage(maxLength, maxValue, annotation));
                }
                if(minValue!=0){
                    rules.put(minLength, minValue);
                    messages.put(minLength, getMessage(minLength, minValue, annotation));
                }
            }
        });
        annotationConverters.put(Pattern.class, new AnnotationRuleBuilder() {
            @Override
            public void addRule(
              final Map<String, Object> rules, final Map<String, String> messages, final Annotation annotation) {
                final String ruleName = "pattern";
                Object value =  ((Pattern) annotation).regexp();
                rules.put(ruleName, value);
                messages.put(ruleName, getMessage(ruleName, value, annotation));
            }
        });
    }

    //Getting error message
    private String getMessage(String ruleName , Object value, Annotation annotation) {
        String defaultErrorMessage = "Error!";
        Class clazz = annotation.annotationType();
        Method message;
        try {
            message = clazz.getMethod("message", new Class[]{});
            if(message!=null){
                String invokedMessage = message.invoke(annotation, null).toString();
                if(!message.getDefaultValue().equals(invokedMessage)){
                    return invokedMessage;
                }else {
                    throw new IllegalAccessException();
                }
            }
        } catch (InvocationTargetException | IllegalAccessException  | NoSuchMethodException e) {
            try {
                return eMessageSource.getMessage(ruleName, new Object[] { value }, locale);
            }catch (Exception ne){
                return defaultErrorMessage;
            }
        }
        return defaultErrorMessage;
    }

    //actually extending rules and messages map
    public void addRuleFromAnnotation(Map<String, Object> rules,  Map<String, String> messages,
      Annotation annotation) {
        if(annotationConverters.containsKey(annotation.annotationType())){
            annotationConverters.get(annotation.annotationType()).addRule(rules, messages, annotation);
        }else{
            String ruleName = namesHolder.getName(annotation.annotationType());
            if(ruleName != null){
                rules.put(ruleName, true);
                messages.put(ruleName, getMessage(ruleName, true,annotation));
            }
        }

    }
}
