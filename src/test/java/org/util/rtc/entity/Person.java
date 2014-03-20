package org.util.rtc.entity;

import org.hibernate.validator.constraints.*;
import org.util.rtc.annotation.Validatable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Vladislav Pikus
 */
@Validatable
public class Person {
    private Collection<Hello> hellos = new ArrayList<Hello>();

    @NotEmpty
    @Length(min = 3, max = 30)
    private String firstName;

    @NotEmpty
    private String lastName;

    @CreditCardNumber
    @Range(min = 11, max = 111)
    private Integer creditCard;

    @Email
    @NotBlank
    private String email;

    @URL
    private String url;

    @NotEmpty
    private User user;
}
