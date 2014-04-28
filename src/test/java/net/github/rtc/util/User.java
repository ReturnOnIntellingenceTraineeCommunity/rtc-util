package net.github.rtc.util;


import net.github.rtc.util.annotation.*;
import net.github.rtc.util.annotation.Number;

import java.util.List;


public class User {
    @Min(3)
    @Max(45)
    private String fio;
    @Number
    private String phone;
    @Email
    @Minlength(5)
    @Maxlength(123)
    private String email;
    @Date
    private java.util.Date birthDate;
    @Range({2,5})
    private String city;
    private String university;
    private String faculty;
    private String major;
    private List<String> technologies;
    private Integer writtenEng;
    private Integer oralEng;
    private String note;
    @Min(1)
    private String password;
    public String getName() {
        return null;
    }


}
