package org.util.rtc.entity;


import org.util.rtc.annotation.date;
import org.util.rtc.annotation.email;
import org.util.rtc.annotation.min;
import org.util.rtc.annotation.number;

import java.util.Date;
import java.util.List;

public class User implements IUser {
    @min(3)
    private String fio;
    @number
    private String phone;
    @email
    private String email;
    @date
    private Date birthDate;
    private String city;
    private String university;
    private String faculty;
    private String major;
    private List<String> technologies;
    private Integer writtenEng;
    private Integer oralEng;
    private String note;
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
