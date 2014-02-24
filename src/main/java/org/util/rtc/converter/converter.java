package org.util.rtc.converter;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;


public class Converter     {





    public static void toJSON(User user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Writer strWriter = new StringWriter();
        mapper.writeValue(strWriter, user);
        String userDataJSON = strWriter.toString();
        System.out.println(userDataJSON);

    }

  

}
