package net.github.rtc.util;

import net.github.rtc.util.converter.AnnotatedFieldScanner;
import net.github.rtc.util.user.User;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
public class AnnotatedFieldsScannerTest {

    @Autowired
    private AnnotatedFieldScanner annotatedFieldScanner;

    @Test
    public void testScanner() {
        Map<String, List<Annotation>> result =  annotatedFieldScanner.scan(User.class, "");
        Assert.assertTrue(result.containsKey("login"));
        List<Annotation> annotations = result.get("login");
        Assert.assertTrue(annotations.size()==2);
    }


}
