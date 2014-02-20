package org.util.rtc.converter;

import org.util.rtc.entity.IUser;
import org.util.rtc.entity.User;

import java.lang.reflect.Field;

public class converter {
    public void setClass(Class className){
        System.out.println(className);
    }

    public static void set(Class<? extends IUser> o) {

        for(Field field : o.getFields()){
            System.out.println(field.getDeclaredAnnotations());
            System.out.println(field);
        }
        // String resolt="{\n";
        //   resolt=resolt+"\"Name\": \""+o.getName()+"\", \n";
        //  resolt=resolt+"\"family\": \""+o.getFamily() + "\", \n";
        // resolt=resolt+"\"family\": "+o.getAge() + ", \n";
        // resolt=resolt+"\"family\": "+o.getTag() + "\n}";

        //      return resolt;
    }

    public static void main(String[] args) {
        converter convec = new converter();
        convec.setClass(convec.getClass());
        convec.set(User.class);
    }
}