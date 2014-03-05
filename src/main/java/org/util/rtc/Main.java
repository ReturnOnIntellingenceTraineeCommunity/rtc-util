package org.util.rtc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.util.rtc.converter.Converter;
import org.util.rtc.entity.User;
import org.util.rtc.validation.ParseValidation;

import java.io.IOException;
import java.util.Locale;

public class Main {
    public static void main(String args[]) throws IOException {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-config.xml");

        ParseValidation validator = (ParseValidation)context.getBean("validator");
        validator.fromClassToJSON(User.class,new Locale("ukr"));
         System.out.print(validator.getJSON(User.class));

    }

}
