package org.util.rtc.validation;

import org.util.rtc.context.Context;
import org.util.rtc.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class ParseValidation implements Serializable {

    private Context context;
    private Converter converter;

    public  ParseValidation(Context context, Converter converter){
        this.context = context;
        this.converter = converter;
    }


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
