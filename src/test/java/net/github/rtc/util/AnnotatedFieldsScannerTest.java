package net.github.rtc.util;
import net.github.rtc.util.converter.AnnotatedFieldScanner;
import net.github.rtc.util.entities.User;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 28.04.14.
 */
public class AnnotatedFieldsScannerTest {
    @Test
    public void testScanner() throws Exception {
        Map<String, List<Annotation>> fields = AnnotatedFieldScanner.scan(User.class, "");
        System.out.println(fields);
    }

    @Test
    public void testPackageScan(){
        List<Map<String, List<Annotation>>> classesFields = AnnotatedFieldScanner.scanPackage("net.github.rtc.util.entities");
        System.out.println(classesFields);
    }

}
