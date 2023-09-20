package com.drunar.coincorner.service;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Page<UserReadDTO> findAll(UserFilter filter, Pageable pageable);

    List<UserReadDTO> findAll();

    Optional<UserReadDTO> findById(Long id);

    UserReadDTO create(UserCreateEditDTO userDto);

    Optional<UserReadDTO> update(Long id, UserCreateEditDTO userDto);

    boolean delete(Long id);

    Optional<byte[]> findAvatar(Long id);

    void newUserFromOAuth(OidcUserRequest userRequest);

    boolean checkEmailIfExists(String email);

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(String username);

    void lock(User user);

    boolean unlockWhenTimeExpired(User user);

    User getByUsername(String username);

    void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException;

    Optional<User> getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);
}