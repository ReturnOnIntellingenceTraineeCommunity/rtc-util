package net.github.rtc.util.converter;

import net.github.rtc.util.annotation.Validatable;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by ivan on 28.04.14.
 */
public class AnnotatedFieldScanner {
    private static Reflections reflections = new Reflections("net.github.rtc.util.annotation");
    private static Set<Class<? extends Annotation>> validationAnnotations = reflections.getSubTypesOf(Annotation.class);

    public static Map<String, List<Annotation>> scan(Class inClass, String parent) {
        Map<String, List<Annotation>> fields = new HashMap<String, List<Annotation>>();

        if(inClass.isAnnotationPresent(Validatable.class)){
            for (Field field : inClass.getDeclaredFields()) {
                List<Annotation> fieldAnnotations = new ArrayList<Annotation>();
                for(Annotation annotation : field.getAnnotations()){
                    if(validationAnnotations.contains(annotation.annotationType())){
                        fieldAnnotations.add(annotation);
                    }
                }
                if(!fieldAnnotations.isEmpty()){
                    String fieldName = (parent.equals("")) ?
                            field.getName() : String.format("%s.%s", parent, field.getName());
                    fields.put(fieldName, fieldAnnotations);
                    Map<String, List<Annotation>>  childFields = scan(field.getType(), fieldName);
                    if(childFields!=null) fields.putAll(childFields);
                }
            }
            return fields;
        }
        return null;
    }

    public static List<Map<String, List<Annotation>>> scanPackage(String path) {
        List<Map<String, List<Annotation>>> scannedClasses = new ArrayList<Map<String, List<Annotation>>>();

        Reflections reflections = new Reflections(path);
        Set<Class<? extends Object>> inClasses = reflections.getTypesAnnotatedWith(Validatable.class);
        for(Class inClass : inClasses){
            scannedClasses.add(scan(inClass, ""));
        }

        return scannedClasses;
    }

}
