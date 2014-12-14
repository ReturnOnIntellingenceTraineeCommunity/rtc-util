package net.github.rtc.util.entities;

import net.github.rtc.util.annotation.validation.*;
import net.github.rtc.util.annotation.validation.Number;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Validatable
public class TestClass {
    @Number
    private int  id;
    @Email(message = "bla bla")
    String email;
    @Email
    @NotEmpty
    String email2;
    @Length(max=255)
    String text;
    @Pattern(regexp = "[0-9]")
    String digitsStr;
}
