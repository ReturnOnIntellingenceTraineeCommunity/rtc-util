package net.github.rtc.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import net.github.rtc.util.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Class that converts another class that marked
 * by annotations to JSON for JQUERY validate
 */
@Component
public class Converter {

    @Autowired
    private MessageSource eMessageSource;

    private interface AnnotationConverter {
        Object convert(Annotation annotation);
    }

    private static final Map<String, AnnotationConverter> annotationConverters = new HashMap<String, AnnotationConverter>();

    static {
        annotationConverters.put("Min", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((Min) annotation).value();
            }
        });
        annotationConverters.put("Max", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((Max) annotation).value();
            }
        });
        annotationConverters.put("Maxlength", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((Maxlength) annotation).value();
            }
        });
        annotationConverters.put("Minlength", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((Minlength) annotation).value();
            }
        });
        annotationConverters.put("Range", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((Range) annotation).value();
            }
        });
        annotationConverters.put("Rangelenght", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((Rangelength) annotation).value();
            }
        });

        annotationConverters.put("Length", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                Length length = ((Length) annotation);
                return String.format("[%s, %s]", length.min(), length.max());
            }
        });
        annotationConverters.put("Range", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                org.hibernate.validator.constraints.Range length = ((org.hibernate.validator.constraints.Range) annotation);
                return String.format("[%s, %s]", length.min(), length.max());
            }
        });
    }

    private Object getValueAnnotation(Annotation annotation) {
        Object obj;
        String nameAnnotation = annotation.annotationType().getSimpleName();
        if (annotationConverters.containsKey(nameAnnotation)) {
            obj = annotationConverters.get(nameAnnotation).convert(annotation);
        } else {
            obj = true;
        }
        return obj;
    }


    private String getErrorMessage(String annotationName, Locale locale) {
        return eMessageSource.getMessage(annotationName, null, locale);
    }

    private boolean doClass(Class inClass, Locale locale, Map<Object, Map> rules, Map<String, Map> messages, String parent) {
        boolean flag = false;
        for (Annotation annotation : inClass.getAnnotations()) {
            if (annotation instanceof Validatable) {
                flag = true;
                break;
            }
        }
        if (flag) {
            for (Field field : inClass.getDeclaredFields()) {
                String fieldName = (parent.equals("")) ? field.getName() : String.format("%s.%s", parent, field.getName());
                if (doClass(field.getType(), locale, rules, messages, fieldName)) {
                    Map<String, Object> validationField = new HashMap<String, Object>();
                    Map<String, String> messageField = new HashMap<String, String>();
                    for (Annotation annotation : field.getDeclaredAnnotations()) {
                        String annotationName = annotation.annotationType().getName();
                        validationField.put(annotationName, getValueAnnotation(annotation));
                        messageField.put(annotationName, getErrorMessage(annotationName, locale));
                        rules.put(fieldName, validationField);
                        messages.put(fieldName, messageField);
                    }
                }
            }
            return false;
        }
        return true;
    }

    /**
     * Convert class to JSON
     * @param inClass what class&
     * @param locale locale of messages for JQuery validate
     * @return JSON for JQuery validate
     * @throws IOException
     */
    public String toJSON(Class inClass, Locale locale) throws IOException {

        Map<String, Map> validationMap = new HashMap<String, Map>(); //the major map
        Map<Object, Map> rules = new HashMap<Object, Map>();   //information from annotation
        Map<String, Map> messages = new HashMap<String, Map>();//error messages
        doClass(inClass, locale, rules, messages, "");
        validationMap.put("messages", messages);
        validationMap.put("rules", rules);


        //transform map to Json
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, validationMap);
        String result = strWriter.toString();
        return result.substring(1, result.length() - 1) + ",";
    }
}
