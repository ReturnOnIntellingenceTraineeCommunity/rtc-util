package org.util.rtc.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class Converter{

    @Autowired
    private MessageSource eMessageSource;

    private interface AnnotationConverter {
        Object convert(Annotation annotation);
    }

    private static final List<String> AVAILABLE_ANNOTATIONS;
    private static final Map<String, AnnotationConverter> annotationConverters = new HashMap<String, AnnotationConverter>();

    static {
        AVAILABLE_ANNOTATIONS =  Arrays.asList("min", "max", "minlength", "maxlength", "range", "rangelength");
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
    }

    @Autowired
    private MessageSource messageSource;

    private Object getValueAnnotation (Annotation annotation){ //get value of anotation
        Object obj;
        String nameAnnotation = annotation.annotationType().getSimpleName();
        if(AVAILABLE_ANNOTATIONS.contains(nameAnnotation)){
            obj=annotationConverters.get(nameAnnotation).convert(annotation);
        }else{
            obj=true;
        }
        return obj;
    }



    private String getMessageAnnotation(Annotation annotation, Locale locale){
        String name = annotation.annotationType().getSimpleName();
        if(AVAILABLE_ANNOTATIONS.contains(name)){
            return eMessageSource.getMessage(name, null, locale)+" "+getValueAnnotation(annotation);
        }
        return eMessageSource.getMessage(name, null, locale);
    }

    public  String toJSON(Class inClass, Locale locale) throws IOException {

        Map <String,Map> validationMap = new HashMap<String,Map>(); //the major map
        Map<Object ,Map> rules = new HashMap<Object ,Map>();   //information from annotation
        Map<String,Map> messages = new HashMap<String,Map>();//error messages

        for(Field field : inClass.getDeclaredFields()){
            Map<String, Object> validationField = new HashMap<String, Object>();
            Map<String,String> messageField = new HashMap<String,String>();
            for(Annotation annotation: field.getDeclaredAnnotations() ){                        //get information about annotation
                String annotationName=annotation.annotationType().getSimpleName();
                validationField.put(annotationName, getValueAnnotation(annotation));    //information about 1 annotation
                messageField.put(annotationName, getMessageAnnotation(annotation, locale));
                rules.put(field.getName(),validationField);                                    //all anotation of 1 field
                messages.put(field.getName(),messageField);
            }
        }
        validationMap.put("messages", messages);
        validationMap.put("rules", rules);


        //transform map to Json
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, validationMap);
        String result = strWriter.toString();
        return result.substring(1, result.length()-1)+",";
    }
}
