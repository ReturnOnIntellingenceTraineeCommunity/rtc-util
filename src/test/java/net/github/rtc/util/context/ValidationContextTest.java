package net.github.rtc.util.context;


import junit.framework.Assert;
import net.github.rtc.util.converter.ValidationContext;
import org.junit.Test;
import net.github.rtc.util.entities.User;

import java.util.HashMap;
import java.util.Map;

public class ValidationContextTest {
    @Test
    public void testContext(){
        Map<Class, String> map = new HashMap<Class, String>();
        map.put(User.class, "first_value");
        ValidationContext context = new ValidationContext();
        context.add(User.class);
        Assert.assertTrue(map.get(User.class).equals(context.get(User.class)));
    }
}
