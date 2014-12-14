package net.github.rtc.util.context;

import junit.framework.Assert;
import net.github.rtc.util.annotation.validation.Validatable;
import net.github.rtc.util.converter.ValidationContext;
import net.github.rtc.util.entities.TestClass;
import net.github.rtc.util.user.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
public class ValidationContextTest {
    @Autowired
    private ValidationContext validationContext;

    final String expectedRule = "\"rules\":{\"id\":{\"number\":true},\"text\":{\"maxlength\":255}," +
      "\"email\":{\"email\":true},\"digitsStr\":{\"pattern\":\"[0-9]\"},\"email2\":{\"email\":true," +
      "\"required\":true}},\"messages\":{\"id\":{\"number\":\"Please enter a valid number.\"}," +
      "\"text\":{\"maxlength\":\"Please enter no more than 255 symbols\"},\"email\":{\"email\":\"bla bla\"}," +
      "\"digitsStr\":{\"pattern\":\"Please use pattern [0-9]\"},\"email2\":{\"email\":\"Please enter a valid email " +
      "address.\",\"required\":\"This field is required.\"}},";

    @Test
    public void testGet(){
        Assert.assertEquals(expectedRule, validationContext.get(TestClass.class));
    }

    @Test
    public void testAddPackage(){
        validationContext.addPackage("net.github.rtc.util.user");
        Assert.assertNotNull(validationContext.get(User.class));
    }

    @Test
    public void testAdd(){
        validationContext.add(FastClass.class);
        Assert.assertNotNull(validationContext.get(FastClass.class));
    }

    @Validatable
    private class FastClass{
        @NotEmpty
        private String test;
    }
}
