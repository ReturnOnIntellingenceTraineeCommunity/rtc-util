package net.github.rtc.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.github.rtc.util.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class that converts another class that marked
 * by annotations to JSON for JQUERY validate
 */
@Component
public class Converter {

    @Autowired
    @Qualifier("eMessageSource")
    private MessageSource eMessageSource;

    @Autowired AnnotatedFieldScanner scanner;

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

    /**
     * Convert class to JSON
     * @param inClass what class&
     * @param locale locale of messages for JQuery validate
     * @return JSON for JQuery validate
     * @throws IOException
     */
    public String toJSON(Class<?> inClass, Locale locale) throws IOException {

        Map<String, Map> validationMap = new HashMap<String, Map>(); //the major map

        Map<Object, Map> fieldRules = new HashMap<Object, Map>();   //information from annotation
        Map<String, Map> fieldMessages = new HashMap<String, Map>();//error messages
        Map<String, List<Annotation>> inClassFields = scanner.scan(inClass, "");
        for(String field : inClassFields.keySet()) {
            Map<String, Object> rule = new HashMap<String, Object>();
            Map<String, String> message = new HashMap<String, String>();
            for(Annotation annotation : inClassFields.get(field)) {
                String annotationName = annotation.annotationType().getSimpleName().toLowerCase();
                rule.put(annotationName, getValueAnnotation(annotation));
                message.put(annotationName, getErrorMessage(annotationName, locale));
            }
            fieldRules.put(field, rule);
            fieldMessages.put(field, message);
        }

        validationMap.put("messages", fieldMessages);
        validationMap.put("rules", fieldRules);


        //transform map to Json
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, validationMap);
        String result = strWriter.toString();
        return result.substring(1, result.length() - 1) + ",";
    }
}
