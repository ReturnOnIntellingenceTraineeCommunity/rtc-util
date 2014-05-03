package net.github.rtc.util.converter;


import net.github.rtc.util.annotation.Validatable;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Class that contains map which key is some class and value
 * is a JSON that was build according to this class
 */
@Component
public class ValidationContext implements Serializable{
    @Autowired
    Converter converter;

    private Map<Class, String> map = new HashMap<Class, String>();

    public ValidationContext(){}

    @Autowired
    public ValidationContext(Converter converter, String packagePath){
        this.converter = converter;
        addPackage(packagePath);
    }

    public void add(Class inClass){
        try {
            String validationJSON = converter.toJSON(inClass, Locale.ENGLISH);
            map.put(inClass, validationJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String get(Class inClass){
        return map.get(inClass);
    }

    public void addPackage(String path){
        Reflections reflections = new Reflections(path);
        Set<Class<? extends Object>> inClasses = reflections.getTypesAnnotatedWith(Validatable.class);
        for(Class inClass : inClasses){
            try {
                String validationJSON = converter.toJSON(inClass, Locale.ENGLISH);
                map.put(inClass, validationJSON);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
