package org.util.rtc.entity;


import org.util.rtc.annotation.*;

public class User implements IUser {
    @minlength(5)
    private String name;
    @min(10)
    @maxlength(20)
    private int age;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }
}
