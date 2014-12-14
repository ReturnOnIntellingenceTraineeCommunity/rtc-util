package net.github.rtc.util.converter;

import net.github.rtc.util.annotation.validation.*;
import org.reflections.Reflections;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;


//Scan class for validation annotations
@Component
public class AnnotatedFieldScanner implements InitializingBean{
    private Reflections hibernateAnnotations = new Reflections("org.hibernate.validator.constraints");
    private Reflections javaxConstrains = new Reflections("javax.validation.constraints");
    private Set<Class<? extends Annotation>> validationAnnotations = new HashSet<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        validationAnnotations.addAll(hibernateAnnotations.getSubTypesOf(Annotation.class));
        validationAnnotations.addAll(javaxConstrains.getSubTypesOf(Annotation.class));
        validationAnnotations.add(net.github.rtc.util.annotation.validation.Number.class);
    }

    //return map with field names and it's annotations
    public Map<String, List<Annotation>> scan(Class inClass, String parent) {
        Map<String, List<Annotation>> fields = new HashMap<>();

        if(inClass.isAnnotationPresent(Validatable.class)){
            for (Field field : inClass.getDeclaredFields()) {
                List<Annotation> fieldAnnotations = new ArrayList<>();
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
