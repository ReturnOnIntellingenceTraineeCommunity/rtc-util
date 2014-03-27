package org.util.rtc.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.util.rtc.annotation.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class Converter {

    @Autowired
    private MessageSource eMessageSource;

    private interface AnnotationConverter {
        Object convert(Annotation annotation);
    }

    private static final Map<String, AnnotationConverter> annotationConverters = new HashMap<String, AnnotationConverter>();

    private static final Map<String, String> ALIAS = new HashMap<String, String>();

    static {
        ALIAS.put("NotEmpty", "required");
        ALIAS.put("CreditCardNumber", "creditcard");
        ALIAS.put("Email", "email");
        ALIAS.put("Length", "rangelength");
        ALIAS.put("NotBlank", "required");
        ALIAS.put("Range", "range");
        ALIAS.put("URL", "url");

        annotationConverters.put("min", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((min) annotation).value();
            }
        });
        annotationConverters.put("max", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((max) annotation).value();
            }
        });
        annotationConverters.put("maxlength", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((maxlength) annotation).value();
            }
        });
        annotationConverters.put("minlength", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((minlength) annotation).value();
            }
        });
        annotationConverters.put("range", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((range) annotation).value();
            }
        });
        annotationConverters.put("rangelenght", new AnnotationConverter() {
            @Override
            public Object convert(Annotation annotation) {
                return ((rangelength) annotation).value();
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
                Range length = ((Range) annotation);
                return String.format("[%s, %s]", length.min(), length.max());
            }
        });
    }

    @Autowired
    private MessageSource messageSource;

    private String getRealNameAnnotation(Annotation annotation) {
        String name = annotation.annotationType().getSimpleName();
        return (ALIAS.containsKey(name)) ? ALIAS.get(name) : name;
    }

    private Object getValueAnnotation(Annotation annotation) { //get value of anotation
        Object obj;
        String nameAnnotation = annotation.annotationType().getSimpleName();
        if (annotationConverters.containsKey(nameAnnotation)) {
            obj = annotationConverters.get(nameAnnotation).convert(annotation);
        } else {
            obj = true;
        }
        return obj;
    }


    private String getMessageAnnotation(Annotation annotation, Locale locale) {
        String name = getRealNameAnnotation(annotation);
        if (annotationConverters.containsKey(name)) {
            return eMessageSource.getMessage(name, null, locale) + " " + getValueAnnotation(annotation);
        }
        return eMessageSource.getMessage(name, null, locale);
    }

    private boolean doClass(Class inClass, Locale locale, Map<Object, Map> rules, Map<String, Map> messages, String parent) {
        boolean flag = false;
        for (Annotation annotation : inClass.getDeclaredAnnotations()) {
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
                    for (Annotation annotation : field.getDeclaredAnnotations()) {                        //get information about annotation
                        String annotationName = getRealNameAnnotation(annotation);
                        validationField.put(annotationName, getValueAnnotation(annotation));    //information about 1 annotation
                        messageField.put(annotationName, getMessageAnnotation(annotation, locale));
                        rules.put(fieldName, validationField);                                    //all anotation of 1 field
                        messages.put(fieldName, messageField);
                    }
                }
            }
            return false;
        }
        return true;
    }

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
