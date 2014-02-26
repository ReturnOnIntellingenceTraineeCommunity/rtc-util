package org.util.rtc.converter;
import  org.util.rtc.annotation.max;
import  org.util.rtc.annotation.maxlength;
import  org.util.rtc.annotation.min;
import  org.util.rtc.annotation.minlength;
import  org.util.rtc.annotation.range;
import  org.util.rtc.annotation.rangelength;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

public class Converter     {


    public  static String getvalueanotatation (Annotation annotation,Field field){ //get value of anotation
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
            s= Integer.valueOf(g.lenght()).toString();
        }
        if(annotation instanceof minlength){
            minlength g = field.getAnnotation(minlength.class);
            s=Integer.valueOf(g.value()).toString();
        }
        if(annotation instanceof range){
            range g = field.getAnnotation(range.class);
            s= Integer.valueOf(g.range().toString()).toString();
        }
        if(annotation instanceof rangelength){
            rangelength g = field.getAnnotation(rangelength.class);
            s= Integer.valueOf(g.range().toString()).toString();
        }
        if (s=="") {s="true";}
        return s;
    }


    public static void toJSON(User user) throws IOException {
          StringBuilder sb = new StringBuilder();


        Map <String, Map> validationMap = new HashMap<String, Map>(); //the major map
        Map<String,Map> rules = new HashMap<String, Map>();                     //information from annotation
    //    Map<String,String> messages = new HashMap<String, String>();   //map for messages


        for(Field field : user.getClass().getFields()){
         //   messages.put(field.getName(),field.toString());
         Map<String,String> poledann = new HashMap<String, String>();
            for(Annotation annotation: field.getDeclaredAnnotations() ){  //get information about annotation
                String s=annotation.annotationType().getSimpleName();
                poledann.put(s, getvalueanotatation(annotation, field));    //ifformation about 1 annotation
                rules.put(field.getName(),poledann);                        //all anotation of 1 field


            }


        }
        validationMap.put("rules", rules);
 //       validationMap.put("messages", messages);



//transform map to Json
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, validationMap);
        String userDataJSON = strWriter.toString();          //result of transformation
        System.out.println(userDataJSON);
    }



}
