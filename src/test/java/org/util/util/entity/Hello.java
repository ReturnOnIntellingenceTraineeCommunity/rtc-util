package org.util.util.entity;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Vladislav Pikus
 */

public class Hello {
    @NotEmpty
    private String value;
}
