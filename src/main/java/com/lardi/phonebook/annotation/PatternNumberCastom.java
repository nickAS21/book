package com.lardi.phonebook.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PatternNumberWithNull.class)
@Documented
public @interface PatternNumberCastom {

    String message() default "{com.AS21.constraints.Pattern_Tel_Failed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp();

}
