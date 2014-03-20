package org.util.rtc.entity;

import org.hibernate.validator.constraints.*;

/**
 * @author Vladislav Pikus
 */
public class Person {
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
}
