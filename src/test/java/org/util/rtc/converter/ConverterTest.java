package org.util.rtc.converter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.util.rtc.entity.User;

import java.util.Locale;


public class ConverterTest {
    @Test
    public void testToJSON() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-config.xml");
        Converter converter = (Converter)context.getBean("converter");
        System.out.println(converter.toJSON(User.class, Locale.ENGLISH));
    }
}
