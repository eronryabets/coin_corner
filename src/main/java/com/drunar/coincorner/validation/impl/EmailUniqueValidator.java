package com.drunar.coincorner.validation.impl;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.validation.EmailExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailUniqueValidator implements ConstraintValidator<EmailExists, UserCreateEditDTO> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserCreateEditDTO value, ConstraintValidatorContext context) {
        Optional<User> userByEmail = userRepository.findUserByEmail(value.getEmail());
        return !userByEmail.isPresent();
    }
}
