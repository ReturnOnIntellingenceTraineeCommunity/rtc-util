package net.github.rtc.util.converter;

import net.github.rtc.util.annotation.Validatable;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by ivan on 28.04.14.
 */
@Component
public class AnnotatedFieldScanner {
    private Reflections reflections = new Reflections("net.github.rtc.util.annotation");
    private Set<Class<? extends Annotation>> validationAnnotations = reflections.getSubTypesOf(Annotation.class);

    public Map<String, List<Annotation>> scan(Class inClass, String parent) {
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
}
