package org.util.util.entity;


import net.github.rtc.util.annotation.*;

import java.util.Date;
import java.util.List;


public class User implements IUser {
    @min(3)
    @max(45)
    private String fio;
    @number
    private String phone;
    @email
    @minlength(5)
    @maxlength(123)
    private String email;
    @date
    private Date birthDate;
    @range({2,5})
    private String city;
    private String university;
    private String faculty;
    private String major;
    private List<String> technologies;
    private Integer writtenEng;
    private Integer oralEng;
    private String note;
    private Hello hello;
    @min(1)
    private String password;
    public String getName() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }
}