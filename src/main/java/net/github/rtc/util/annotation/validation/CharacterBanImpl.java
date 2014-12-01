package net.github.rtc.util.annotation.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Berdniky on 01.12.2014.
 */
public class CharacterBanImpl implements ConstraintValidator<CharacterBan, String> {
    @Override
    public void initialize(CharacterBan characterBan) {
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt) {
        if(field == null) {
            return false;
        }
        return field.matches("[a-zA-Z0-9_]");
    }
}
