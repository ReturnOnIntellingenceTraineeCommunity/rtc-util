package net.github.rtc.util.converter;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import net.github.rtc.util.entities.User;

import java.util.Locale;

@Ignore
public class ConverterTest {
    @Test
    public void testToJSON() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-config.xml");
        Converter converter = (Converter)context.getBean("converter");
        System.out.println(converter.toJSON(User.class, Locale.ENGLISH));
    }
}