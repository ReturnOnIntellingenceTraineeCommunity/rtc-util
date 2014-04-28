package net.github.rtc.util.context;


import junit.framework.Assert;
import net.github.rtc.util.converter.Context;
import org.junit.Test;
import net.github.rtc.util.User;

import java.util.HashMap;
import java.util.Map;

public class ContextTest {
    @Test
    public void testContext(){
        Map<Class, String> map = new HashMap<Class, String>();
        map.put(User.class, "first_value");
        Context context = new Context();
        context.add(User.class, "first_value");
        Assert.assertTrue(map.get(User.class).equals(context.get(User.class)));
    }
}
