package org.util.rtc.converter;

import org.util.rtc.entity.User;

public class ConverterTest {
    @org.junit.Test
    public void testToJSON() throws Exception {
        //Converter converter = new Converter();
        Converter.toJSON(new User());
    }
}
