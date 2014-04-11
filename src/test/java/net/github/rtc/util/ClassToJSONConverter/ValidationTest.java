package net.github.rtc.util.ClassToJSONConverter;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.util.util.entity.Person;

import java.util.Locale;


public class ValidationTest {
    @Test
    public void testMakeValidationsFromPackages() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-config.xml");
        ClassToJSONConverter validation = (ClassToJSONConverter)context.getBean("classToJSONConverter");
        validation.fromClassToJSON(Person.class, Locale.ENGLISH);
        System.out.println(validation.getJSON(Person.class));
    }
}
