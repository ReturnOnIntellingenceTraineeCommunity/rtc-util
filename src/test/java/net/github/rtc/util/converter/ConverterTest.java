package net.github.rtc.util.converter;

import junit.framework.Assert;
import net.github.rtc.util.entities.TestClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Locale;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
public class ConverterTest {
    @Autowired
    Converter converter;

    final String expectedRule = "\"rules\":{\"text\":{\"maxlength\":255},\"email\":{\"email\":true}," +
      "\"digitsStr\":{\"pattern\":\"[0-9]\"},\"email2\":{\"email\":true,\"required\":true}}," +
      "\"messages\":{\"text\":{\"maxlength\":\"Please enter no more than 255 symbols\"}," +
      "\"email\":{\"email\":\"bla bla\"},\"digitsStr\":{\"pattern\":\"Please use pattern [0-9]\"}," +
      "\"email2\":{\"email\":\"Please enter a valid email address.\",\"required\":\"This field is required.\"}},";

    @Test
    public void testContext(){
        String json;
        try {
            json = converter.toJSON(TestClass.class, Locale.ENGLISH);
            Assert.assertEquals(expectedRule, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
