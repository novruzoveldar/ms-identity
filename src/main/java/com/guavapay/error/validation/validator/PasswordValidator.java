package com.guavapay.error.validation.validator;

import com.guavapay.constants.Constants;
import com.guavapay.error.validation.constraint.ValidPassword;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return !StringUtils.hasText(password) && password.matches(Constants.PWORD_REGEX);
    }
}
