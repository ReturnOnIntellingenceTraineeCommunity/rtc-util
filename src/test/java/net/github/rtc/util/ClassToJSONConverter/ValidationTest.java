package net.github.rtc.util.ClassToJSONConverter;

import net.github.rtc.util.User;
import net.github.rtc.util.converter.ClassToJSONConverter;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.Annotation;
import java.util.Locale;


public class ValidationTest {
    @Test
    public void testMakeValidationsFromPackages() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-config.xml");
        ClassToJSONConverter validation = (ClassToJSONConverter)context.getBean("classToJSONConverter");
        java.lang.annotation.Annotation[] a = User.class.getAnnotations();
        validation.fromClassToJSON(User.class, Locale.ENGLISH);
        System.out.println(validation.getJSON(User.class));
    }
}
