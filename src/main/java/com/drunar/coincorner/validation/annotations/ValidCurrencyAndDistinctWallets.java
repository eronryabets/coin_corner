package com.drunar.coincorner.validation.annotations;

import com.drunar.coincorner.validation.impl.ValidCurrencyAndDistinctWalletsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidCurrencyAndDistinctWalletsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrencyAndDistinctWallets {

    String message() default "Wallets have invalid currency or are the same.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
