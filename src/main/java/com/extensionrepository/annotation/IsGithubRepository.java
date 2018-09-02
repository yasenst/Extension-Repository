package com.extensionrepository.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = IsGithubRepositoryValidator.class)
public @interface IsGithubRepository {
    String message() default "Not a valid github repository";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
