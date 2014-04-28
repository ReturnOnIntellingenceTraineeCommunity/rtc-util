package net.github.rtc.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

/**
 * Class that converts another class that marked by annotations to
 * JSON for JQuery validate and put it to a context class
 */
@Component
public class ClassToJSONConverter implements Serializable {

    @Autowired
    private Context context;
    @Autowired
    private Converter converter;

    public ClassToJSONConverter(){}

    /**
     * Convert class to JSON for JQuery validate
     * @param clazz what class
     * @param locale what locale that will be used for Jquery validate messages
     */
    public void fromClassToJSON(Class clazz, Locale locale){
        try {
            context.add(clazz, converter.toJSON(clazz, locale));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get JSON of some class
     * @param inClass of what class&
     * @return
     */
    public String getJSON(Class inClass){
        return context.get(inClass);
    }

}
