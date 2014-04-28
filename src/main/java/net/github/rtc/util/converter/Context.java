package net.github.rtc.util.converter;


import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that contains map which key is some class and value
 * is a JSON that was build according to this class
 */
@Component
public class Context implements Serializable{
    private Map<Class, String> map = new HashMap<Class, String>();

    public void add(Class key, String value){
        map.put(key, value);
    }

    public String get(Class key){
        return map.get(key);
    }
}
