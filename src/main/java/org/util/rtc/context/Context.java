package org.util.rtc.context;


import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, String> map = new HashMap<String, String>();

    public void add(String key, String value){
        map.put(key, value);
    }

    public String get(String key){
        return map.get(key);
    }
}
