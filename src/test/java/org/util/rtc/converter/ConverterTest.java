package org.util.rtc.converter;

import org.junit.Test;
import org.util.rtc.entity.User;

/**
 * Created by Dell on 27.02.14.
 */
public class ConverterTest {
    @Test
    public void testToJSON() throws Exception {
        Converter.toJSON(new User());
    }
}
