package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.Role;
import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Predicate.not;

@Component
@RequiredArgsConstructor
public class UserCopyHelper implements Mapper<UserCreateEditDTO, User>{

    private final PasswordEncoder passwordEncoder;

    public User map(UserCreateEditDTO fromObject, User toObject, String uploadImage) {
        copy(fromObject, toObject, uploadImage);
        return toObject;
    }

    public User map(UserCreateEditDTO object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDTO object, User user) {
        user.setEmail(object.getEmail());
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setAccountNonLocked(true);

        updateRoles(user, object.getRoles());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }

    private void updateRoles(User user, Set<Role> newRoles) {
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
            user.getRoles().add(Role.USER);
        } else {
            if (!user.getRoles().equals(newRoles)) {
                user.getRoles().clear();
                user.getRoles().addAll(newRoles);
            }
        }
    }

    private void copy(UserCreateEditDTO object, User user, String uploadImage) {
        user.setEmail(object.getEmail());
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());

        updateRoles(user, object.getRoles());

        Optional.ofNullable(object.getRawPassword())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        Optional.ofNullable(object.getImage())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(uploadImage));
    }


}
