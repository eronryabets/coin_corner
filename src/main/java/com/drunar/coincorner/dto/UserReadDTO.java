package com.drunar.coincorner.dto;

import com.drunar.coincorner.database.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
public class UserReadDTO {
    Long id;
    String email;
    String username;
    String firstname;
    String lastname;
    LocalDate birthDate;
    String image;
    Set<Role> roles;
    @JsonIgnore
    List<WalletReadDTO> walletsDTO;
}