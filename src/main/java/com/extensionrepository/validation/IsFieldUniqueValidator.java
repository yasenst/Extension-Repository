package com.extensionrepository.validation;

import com.extensionrepository.service.base.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsFieldUniqueValidator implements ConstraintValidator<IsFieldUnique, Object> {
    @Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(IsFieldUnique constraintAnnotation) {
        Class<? extends FieldValueExists> clazz = constraintAnnotation.service();
        this.fieldName = constraintAnnotation.fieldName();
        String serviceQualifier = constraintAnnotation.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        return !this.service.fieldValueExists(o, this.fieldName);
    }

}