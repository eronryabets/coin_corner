package com.drunar.coincorner.validation;

import com.drunar.coincorner.validation.impl.UsernameUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameExists {

    String message() default "A user with this username is already registered. Please choose a different username.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
