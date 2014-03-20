package org.util.rtc.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.util.rtc.annotation.Validatable;

/**
 * @author Vladislav Pikus
 */

public class Hello {
    @NotEmpty
    private String value;
}
