package org.util.rtc.context;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Context {
    private Map<Class, String> map = new HashMap<Class, String>();

    public void add(Class key, String value){
        map.put(key, value);
    }

    public String get(Class key){
        return map.get(key);
    }
}
