package com.drunar.coincorner.dto;

import lombok.Value;

import java.time.LocalDate;
import java.util.List;

@Value
public class UserReadDTO {
    Long id;
    String email;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    List<WalletReadDTO> walletsDTO;
}