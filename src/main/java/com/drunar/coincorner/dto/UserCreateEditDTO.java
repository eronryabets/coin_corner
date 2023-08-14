package com.drunar.coincorner.dto;

import com.drunar.coincorner.database.entity.Role;
import com.drunar.coincorner.validation.EmailExists;
import com.drunar.coincorner.validation.UsernameExists;
import com.drunar.coincorner.validation.group.CreateActions;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Set;

@Value
@EmailExists(groups = CreateActions.class)
@UsernameExists(groups = CreateActions.class)
public class UserCreateEditDTO {

    @Email(message = "Should have the format of an email address")
    @NotEmpty(message = "Email should be filled in")
    String email;

    @Size(min = 3, max = 64, message = "Username size should be within the range of 3 to 64")
    String username;

    @NotBlank(groups = CreateActions.class)
    String rawPassword;

    @NotEmpty(message = "Firstname should be filled in")
    String firstname;

    @NotEmpty(message = "Lastname should be filled in")
    String lastname;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    LocalDate birthDate;

    MultipartFile image;

    Set<Role> roles;
}
