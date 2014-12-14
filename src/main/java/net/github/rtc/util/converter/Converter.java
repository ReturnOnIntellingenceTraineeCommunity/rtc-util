package net.github.rtc.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Class that converts another class that marked
 * by annotations to JSON for JQUERY validate
 */
@Component
public class Converter {
    @Autowired
    private AnnotatedFieldScanner scanner;
    @Autowired
    private ValidationRuleFactory ruleFactory;


    /**
     * Convert class to JSON
     * @param inClass what class&
     * @param locale locale of messages for JQuery validate
     * @return JSON for JQuery validate
     * @throws IOException
     */
    public String toJSON(Class<?> inClass, Locale locale) throws IOException {
        Map<String, Map> validationMap = buildValidationMap(inClass, locale); //the major map

        //transform map to Json
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, validationMap);
        String result = strWriter.toString();
        return result.substring(1, result.length() - 1) + ",";
    }

    private Map<String, Map> buildValidationMap(Class<?> inClass, Locale locale) {
        ruleFactory.setLocale(locale);
        Map<String, Map> validationMap = new HashMap<>(); //the major map

        Map<Object, Map> fieldRules = new HashMap<>();   //information from annotation
        Map<String, Map> fieldMessages = new HashMap<>();//error messages
        Map<String, List<Annotation>> inClassFields = scanner.scan(inClass, "");
        for(String field : inClassFields.keySet()) {
            Map<String, Object> rule = new HashMap<>();
            Map<String, String> message = new HashMap<>();
            for(Annotation annotation : inClassFields.get(field)) {
                ruleFactory.addRuleFromAnnotation(rule, message, annotation);
            }
            fieldRules.put(field, rule);
            fieldMessages.put(field, message);
        }
        validationMap.put("messages", fieldMessages);
        validationMap.put("rules", fieldRules);
        return validationMap;
    }
}
