package com.extensionrepository.annotation;

import com.extensionrepository.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsPasswordMatchingValidator implements ConstraintValidator<IsPasswordMatching, Object> {
    @Override
    public void initialize(IsPasswordMatching constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object userClass, ConstraintValidatorContext context) {
        if (userClass instanceof UserDto) {
            return ((UserDto) userClass)
                    .getPassword()
                    .equals(((UserDto) userClass)
                            .getConfirmPassword());
        }
        return false;
    }
}
