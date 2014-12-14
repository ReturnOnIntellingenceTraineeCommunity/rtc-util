package net.github.rtc.util.user;

import net.github.rtc.util.annotation.validation.Validatable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Validatable
public class User {
    @NotEmpty
    @Length(min=10, max = 233, message = "length bla bla")
    String login;
}
