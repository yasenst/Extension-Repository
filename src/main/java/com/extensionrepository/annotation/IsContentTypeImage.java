package com.extensionrepository.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = IsContentTypeImageValidator.class)
public @interface IsContentTypeImage {
    String message() default "Image should be png, jpg or jpeg";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
