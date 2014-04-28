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

    public static List<Field> scan(Class inClass, String parent) {
        List<Field> fieldsList = new ArrayList<Field>();
        Set<Class<? extends Annotation>> validationAnnotations = reflections.getSubTypesOf(Annotation.class);

        if(inClass.isAnnotationPresent(Validatable.class)){
            for (Field field : inClass.getDeclaredFields()) {
                Set<Class<? extends Annotation>> fieldAnnotations = new HashSet<Class<? extends Annotation>>();
                for(Annotation annotation : field.getAnnotations()){fieldAnnotations.add(annotation.annotationType());}
                if(!Collections.disjoint(validationAnnotations, fieldAnnotations)){
                    String fieldName = (parent.equals("")) ?
                            field.getName() : String.format("%s.%s", parent, field.getName());
                    fieldsList.add(field);
                    List<Field> childFields = scan(field.getType(), fieldName);
                    if(childFields!=null) fieldsList.addAll(childFields);
                }
            }
            return fieldsList;
        }
        return null;
    }
}
