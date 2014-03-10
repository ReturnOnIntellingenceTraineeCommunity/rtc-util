package org.util.rtc.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.util.rtc.context.Context;
import org.util.rtc.converter.Converter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

@Component
public class Validation implements Serializable {

    @Autowired
    private Context context;
    @Autowired
    private Converter converter;

    public Validation(){}


    public void fromClassToJSON(Class clazz, Locale locale){
        try {
            context.add(clazz, converter.toJSON(clazz, locale));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJSON(Class inClass){
        return context.get(inClass);
    }

}
