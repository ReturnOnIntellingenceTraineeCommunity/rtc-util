package org.util.rtc.entity;


import org.util.rtc.annotation.*;

public class User implements IUser {
    @minlength(5)
    public String name;
    @min(10)
    public int age;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }
}
