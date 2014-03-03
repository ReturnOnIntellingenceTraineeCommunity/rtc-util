package org.util.rtc.converter;

import org.junit.Test;
import org.util.rtc.annotation.maxlength;
import org.util.rtc.annotation.min;
import org.util.rtc.annotation.minlength;
import org.util.rtc.entity.User;
import org.util.rtc.converter.*;

import static org.junit.Assert.assertTrue;

/**
 * Created by Dell on 27.02.14.
 */
public class ConverterTest {
    @Test
    public void testToJSON() throws Exception {
        System.out.println(Converter.toJSON(User.class));
        //assertTrue(Converter.toJSON(User.class).equals("{"rules":{"age":{"min":"10","maxlength":"20"},"name":{"minlength":"5"}}}");
    }
}
