package org.util.rtc.validation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.util.rtc.entity.Person;
import org.util.rtc.entity.User;

import java.util.Locale;


public class ValidationTest {
    @Test
    public void testMakeValidationsFromPackages() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-config.xml");
        Validation validation = (Validation)context.getBean("validation");
        validation.fromClassToJSON(Person.class, Locale.ENGLISH);
        validation.fromClassToJSON(User.class, Locale.ENGLISH);
        System.out.println(validation.getJSON(Person.class));
        System.out.println(validation.getJSON(User.class));
    }
}
