package com.upgrad.doctorservice.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatePattern implements ConstraintValidator<DateFormatValidator, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile("^([0-9]{4})-([0-9]{2})-([0-9]){2}$");
        Matcher matcher = pattern.matcher(value);
        try {
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }
}
