package com.drunar.coincorner.dto;

import lombok.Value;

import java.time.LocalDate;

@Value
public class UserReadDTO {
    Long id;
    String email;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
}