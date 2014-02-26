package org.util.rtc.context;


import junit.framework.Assert;
import org.junit.Test;
import org.util.rtc.entity.User;

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
