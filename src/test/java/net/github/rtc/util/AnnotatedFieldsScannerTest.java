package net.github.rtc.util;

import net.github.rtc.util.converter.AnnotatedFieldScanner;
import net.github.rtc.util.entities.User;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

/**
 * Created by ivan on 28.04.14.
 */
@Component
@Ignore
public class AnnotatedFieldsScannerTest {
    @Autowired
    AnnotatedFieldScanner scanner;
    @Test
    public void testScanner() throws Exception {
        Map<String, List<Annotation>> fields = scanner.scan(User.class, "");
        System.out.println(fields);
    }


}
