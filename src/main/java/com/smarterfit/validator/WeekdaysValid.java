package com.smarterfit.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WeekdaysValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface WeekdaysValid {
    String message() default "Invalid weekday(s): must be between 2 (Monday) and 8 (Sunday)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}