package com.drunar.coincorner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
public class UserCreateEditDTO {

    @Email
    String email;

    @Size(min = 3, max = 64)
    String username;

    String firstname;

    String lastname;

    @DateTimeFormat(pattern = "yyy-MM-dd")
    LocalDate birthDate;
}
