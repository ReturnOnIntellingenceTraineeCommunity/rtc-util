package org.util.rtc.converter;

import org.junit.Test;
import org.util.rtc.entity.User;
import org.util.rtc.converter.*;
/**
 * Created by Dell on 27.02.14.
 */
public class ConverterTest {
    @Test
    public void testToJSON() throws Exception {
        Assert.assertTrue(Converter.toJSON(user)).equals("{"rules":{"age":{"min":"10","maxlength":"20"},"name":{"minlength":"5"}}}");
    }
}
