package com.drunar.coincorner.validation.annotations;

import com.drunar.coincorner.validation.impl.WalletExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WalletExistValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WalletExists {

    String message() default "Wallet does not exist.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
