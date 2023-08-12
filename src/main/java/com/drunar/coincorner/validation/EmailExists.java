package com.drunar.coincorner.validation;

import com.drunar.coincorner.validation.impl.EmailUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailExists {

    String message() default "A user with this email is already registered. Please choose a different email.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
