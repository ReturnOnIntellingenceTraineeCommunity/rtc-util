package org.util.rtc.validation;

import org.util.rtc.context.Context;
import org.util.rtc.converter.Converter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

public class ParseValidation implements Serializable {

    private Context context = new Context();

    public void fromClassToJSON(Class clazz, Locale locale){
        try {
            context.add(clazz, Converter.toJSON(clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParseValidation(){}
}
