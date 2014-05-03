package net.github.rtc.util.context;


import net.github.rtc.util.converter.Converter;
import net.github.rtc.util.converter.ValidationContext;
import net.github.rtc.util.entities.TestClass;
import net.github.rtc.util.entities.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Locale;

@Ignore
public class ValidationContextTest {
    @Test
    public void testContext(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        Converter converter = (Converter)ctx.getBean("converter");
        String json = null;
        try {
            json = converter.toJSON(TestClass.class, Locale.ENGLISH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Test
        public void addPackages(){
            ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
            ValidationContext context= (ValidationContext)ctx.getBean("validationContext");
            String json = null;
            context.addPackage("net.github.rtc.util.entities");
            json = context.get(User.class);

    }
}
