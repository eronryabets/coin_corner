package com.drunar.coincorner.service;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.dto.CustomUserDetails;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.mapper.UserCopyHelper;
import com.drunar.coincorner.mapper.UserMapper;
import com.drunar.coincorner.mapper.UserOAuthMapper;
import com.drunar.coincorner.util.predicateBuilder.UserPredicateBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final UserCopyHelper userCopyHelper;
    private final UserOAuthMapper userOAuthMapper;
    private final PasswordEncoder passwordEncoder;

    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

    @Override
    public Page<UserReadDTO> findAll(UserFilter filter, Pageable pageable) {
        Predicate predicate = UserPredicateBuilder.buildPredicate(filter);

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().and(Sort.by("id"))
        );

        return userRepository.findAll(predicate, pageable)
                .map(userMapper::userToUserReadDTO);
    }

    @Override
    public List<UserReadDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userToUserReadDTO).toList();
    }

    @Override
    @PreAuthorize("(#id == authentication.principal.user.id) or (hasAnyAuthority('ADMIN'))")
    public Optional<UserReadDTO> findById(Long id) {
        return userRepository.findById(id).map(userMapper::userToUserReadDTO);
    }

    @Override
    @Transactional
    public UserReadDTO create(UserCreateEditDTO userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCopyHelper.map(dto);
                })
                .map(userRepository::save)
                .map(userMapper::userToUserReadDTO)
                .orElseThrow();
    }

    @Override
    @Transactional
    public Optional<UserReadDTO> update(Long id, UserCreateEditDTO userDto) {
        return userRepository.findById(id)
                .map(entity -> {
                    String uploadImage = uploadImage(userDto.getImage());
                    return userCopyHelper.map(userDto, entity, uploadImage);
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::userToUserReadDTO);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        return userRepository.findById(id)
                .map(entity -> {
                    imageService.delete(entity.getImage());
                    userRepository.delete(entity);
                    return true;
                }).orElse(false);
    }

    @SneakyThrows
    private String uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            return imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
        return image.getOriginalFilename();
    }

    @Override
    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    @Override
    @Transactional
    public void newUserFromOAuth(OidcUserRequest userRequest) {
        Optional<User> user = userRepository.findByUsername(userRequest.getIdToken().getClaim("email"));
        if (user.isEmpty()) {
            create(userOAuthMapper.map(userRequest));
        }

    }

    @Override
    @Transactional
    public boolean checkEmailIfExists(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        return userByEmail.isPresent();
    }

    @Override
    @Transactional
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        userRepository.updateFailedAttempts(newFailAttempts, user.getUsername());
    }

    @Override
    @Transactional
    public void resetFailedAttempts(String username) {
        userRepository.updateFailedAttempts(0, username);
    }

    @Override
    @Transactional
    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean unlockWhenTimeExpired(User user) {
        LocalDateTime lockTime = user.getLockTime();
        LocalDateTime currentTime = LocalDateTime.now();

        Duration duration = Duration.between(lockTime, currentTime);
        long durationMillis = duration.toMillis();

        if (durationMillis >= LOCK_TIME_DURATION) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            userRepository.save(user);

            return true;
        }

        return false;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    @Transactional
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> {
                            user.setResetPasswordToken(token);
                            userRepository.save(user);},
                        () -> {throw new UsernameNotFoundException("Could not find any user with the email " + email);}
                );
    }

    @Override
    public Optional<User> getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }


}