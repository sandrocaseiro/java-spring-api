package dev.sandrocaseiro.apitemplate.validations;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Map;

public class NotEmptyValidator implements ConstraintValidator<NotEmpty, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null)
            return false;
        else if (value instanceof List)
            return !((List)value).isEmpty();
        else if (value instanceof Map)
            return !((Map)value).isEmpty();
        else if (value instanceof String)
            return StringUtils.hasText((String)value);

        return true;
    }
}
