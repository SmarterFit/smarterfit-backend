package com.smarterfit.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;


public abstract class WeekdaysValidator implements ConstraintValidator<WeekdaysValid, Set<Integer>> {

    @Override
    public boolean isValid(Set<Integer> days, ConstraintValidatorContext context) {
        if (days == null || days.isEmpty()) return false;
        return days.stream().allMatch(day -> (day >= 2 && day <= 8));
    }
}