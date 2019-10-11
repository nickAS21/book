package com.lardi.phonebook.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatternNumberWithNull implements ConstraintValidator<PatternNumberCastom, String> {
    String regexp;

    public void initialize(PatternNumberCastom constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    /**
     * Pattern: value null -  Possible
     * @param value
     * @param constraintContext
     * @return
     */
    public boolean isValid(String value, ConstraintValidatorContext constraintContext) {
        return (value == null || value.isEmpty()) || value.matches(this.regexp);
    }

}
