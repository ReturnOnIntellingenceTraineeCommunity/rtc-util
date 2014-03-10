package org.util.rtc.converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.util.rtc.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

@Component
public class Converter{

    @Autowired
    private MessageSource messageSource;

    private String getValueAnnotation (Annotation annotation,Field field){ //get value of anotation
        String s="";
        if(annotation instanceof max){
            max g = field.getAnnotation(max.class);
            s= Integer.valueOf(g.value()).toString();
        }
        if(annotation instanceof min){
            min g = field.getAnnotation(min.class);
            s=Integer.valueOf(g.value()).toString();
        }
        if(annotation instanceof maxlength){
            maxlength g = field.getAnnotation(maxlength.class);
            s= Integer.valueOf(g.value()).toString();
        }
        if(annotation instanceof minlength){
            minlength g = field.getAnnotation(minlength.class);
            s=Integer.valueOf(g.value()).toString();
        }
        if(annotation instanceof range){
            range g = field.getAnnotation(range.class);
            s= Integer.valueOf(g.value().toString()).toString();
        }
        if(annotation instanceof rangelength){
            rangelength g = field.getAnnotation(rangelength.class);
            s= Integer.valueOf(g.value().toString()).toString();
        }
        if (s=="") {s="true";}
        return s;
    }

    private String getMessageAnnotation(Annotation annotation, Field field, Locale locale){
        String annotationName= annotation.annotationType().getSimpleName();
        return messageSource.getMessage("min", null, locale)+getValueAnnotation(annotation, field);
    }




    public  String toJSON(Class inClass, Locale locale) throws IOException {

        Map <String,Map> validationMap = new HashMap<String,Map>(); //the major map
        Map<String,Map> rules = new HashMap<String,Map>();   //information from annotation
        Map<String,Map> messages = new HashMap<String,Map>();//error messages

        for(Field field : inClass.getDeclaredFields()){
            Map<String,String> validationField = new HashMap<String,String>();
            Map<String,String> messageField = new HashMap<String,String>();
            for(Annotation annotation: field.getDeclaredAnnotations() ){                        //get information about annotation
                String annotationName=annotation.annotationType().getSimpleName();
                validationField.put(annotationName, getValueAnnotation(annotation, field));    //information about 1 annotation
                messageField.put(annotationName, getMessageAnnotation(annotation, field, locale));
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
        String userDataJSON = strWriter.toString();          //result of transformation
        return userDataJSON;
    }
}
