package net.github.rtc.util;
import net.github.rtc.util.converter.AnnotatedFieldScanner;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by ivan on 28.04.14.
 */
public class AnnotatedFieldsScannerTest {
    @Test
    public void testScanner() throws Exception {
        List<Field> fields = AnnotatedFieldScanner.scan(User.class, "");
        System.out.println(fields);
    }
}
