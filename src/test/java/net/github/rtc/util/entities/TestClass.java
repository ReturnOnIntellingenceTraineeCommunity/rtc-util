package net.github.rtc.util.entities;

import net.github.rtc.util.annotation.validation.*;
import net.github.rtc.util.annotation.validation.Number;

/**
 * Created by ivan on 30.04.14.
 */
@Validatable
public class TestClass {
    @Minlength(5)
    @Maxlength(30)
    private String login;
    @Number
    private int  id;
    @Required
    User u;
}
