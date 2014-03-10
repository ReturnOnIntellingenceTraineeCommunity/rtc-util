package org.util.rtc.validation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.util.rtc.entity.User;

import java.util.Locale;


public class ValidationTest {
    @Test
    public void testMakeValidationsFromPackages() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-config.xml");
        Validation validation = (Validation)context.getBean("validation");
        validation.fromClassToJSON(User.class, Locale.ENGLISH);
    }
}
