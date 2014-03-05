package org.util.rtc.converter;
import org.springframework.context.MessageSource;
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

public class Converter{

    private MessageSource messageSource;

    public Converter(MessageSource messageSource){
        this.messageSource = messageSource;
    }

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
        String message="";
        if(annotation instanceof required){
            message=messageSource.getMessage("required",null,locale);
        }
        if(annotation instanceof min){
            message=messageSource.getMessage("min",null,locale);
            message += getValueAnnotation(annotation, field);
        }
        if(annotation instanceof maxlength){
            message=messageSource.getMessage("maxlength",null,locale);
            message += getValueAnnotation(annotation, field);
        }
        if(annotation instanceof minlength){
            message=messageSource.getMessage("minlength",null,locale);
            message += getValueAnnotation(annotation, field);
        }
        if(annotation instanceof range){
            message=messageSource.getMessage("range",null,locale);
            message += getValueAnnotation(annotation, field);
        }
        if(annotation instanceof rangelength){
            message=messageSource.getMessage("rangelength",null,locale);
            message += getValueAnnotation(annotation, field);
        }
        if(annotation instanceof email){
            message=messageSource.getMessage("email",null,locale);
        }

        return message;
    }



    public  String toJSON(Class inClass, Locale locale) throws IOException {

        Map <String,Map> validationMap = new HashMap<>(); //the major map
        Map<String,Map> rules = new HashMap<>();   //information from annotation
        Map<String,Map> messages = new HashMap<>();//error messages

        for(Field field : inClass.getDeclaredFields()){
            Map<String,String> validationField = new HashMap<>();
            Map<String,String> messageField = new HashMap<>();
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
