package com.extensionrepository.annotation;

import com.extensionrepository.util.Constants;
import com.extensionrepository.dto.ExtensionDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsGithubRepositoryValidator implements ConstraintValidator<IsGithubRepository, Object>  {
    @Override
    public void initialize(IsGithubRepository constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object extensionClass, ConstraintValidatorContext context) {
        if (extensionClass instanceof ExtensionDto) {
            return ((ExtensionDto) extensionClass)
                    .getRepositoryLink()
                    .startsWith(Constants.GITHUB_URL);
        }
        return false;
    }
}
