package com.drunar.coincorner.service;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.mapper.UserCopyHelper;
import com.drunar.coincorner.mapper.UserMapper;
import com.drunar.coincorner.util.predicateBuilder.UserPredicateBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;
    private final UserCopyHelper userCopyHelper;

    public Page<UserReadDTO> findAll(UserFilter filter, Pageable pageable) {
        Predicate predicate = UserPredicateBuilder.buildPredicate(filter);

        return userRepository.findAll(predicate, pageable)
                .map(userMapper::userToUserReadDTO);
    }

    public List<UserReadDTO> findAll(){
        return userRepository.findAll().stream()
                .map(userMapper::userToUserReadDTO).toList();
    }

    public Optional<UserReadDTO> findById(Long id){
        return userRepository.findById(id).map(userMapper::userToUserReadDTO);
    }

    @Transactional
    public UserReadDTO create(UserCreateEditDTO userDto){
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return userCopyHelper.map(dto);
                })
                .map(userRepository::save)
                .map(userMapper::userToUserReadDTO)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDTO> update(Long id, UserCreateEditDTO userDto){
        return userRepository.findById(id)
                .map(entity -> {
                    uploadImage(userDto.getImage());
                     return    userCopyHelper.map(userDto, entity);
                })
                .map(userRepository::saveAndFlush)
                .map(userMapper::userToUserReadDTO);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if(!image.isEmpty()){
            imageService.upload(image.getOriginalFilename(),image.getInputStream());
        }
    }

    @Transactional
    public boolean delete(Long id){
        return userRepository.findById(id)
                .map(entity ->{
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                }).orElse(false);
    }

    public Optional<byte[]> findAvatar(Long id) {
        return userRepository.findById(id)
                .map(User::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        new ArrayList<>(user.getRoles())
                )).orElseThrow(()->new UsernameNotFoundException("Failed to retrieve user: " + username));
    }
}