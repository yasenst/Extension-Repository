package com.extensionrepository.annotation;

import com.extensionrepository.dto.ExtensionDto;
import com.extensionrepository.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsContentTypeImageValidator implements ConstraintValidator<IsContentTypeImage, Object> {
    @Override
    public void initialize(IsContentTypeImage constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object extensionClass, ConstraintValidatorContext context) {
        if (extensionClass instanceof ExtensionDto) {
            if (((ExtensionDto) extensionClass).getImage().getOriginalFilename().equals("")) {
                return true;
            }
            return ((ExtensionDto) extensionClass)
                    .getImage().getContentType().equals("image/png")
                    || ((ExtensionDto) extensionClass)
                    .getImage().getContentType().equals("image/jpg")
                    || ((ExtensionDto) extensionClass)
                    .getImage().getContentType().equals("image/jpeg");
        }
        return false;
    }
}
