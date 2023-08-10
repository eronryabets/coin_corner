package com.drunar.coincorner.service;

import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserReadDTO> findAll(){
        //TODO: UserFilter filter, Pageable pageable
        return userRepository.findAll().stream()
                .map(userMapper::userToUserReadDTO).toList();
    }

    public List<UserReadDTO> findAll(UserFilter filter){
        return userRepository.findAllByFilter(filter).stream()
                .map(userMapper::userToUserReadDTO).toList();
    }

    public Optional<UserReadDTO> findById(Long id){
        return userRepository.findById(id).map(userMapper::userToUserReadDTO);
    }

    @Transactional
    public UserReadDTO create(UserCreateEditDTO userDto){
        return Optional.of(userDto)
                .map(userMapper::userCreateEditDTOtoUser)
                .map(userRepository::save)
                .map(userMapper::userToUserReadDTO)
                .orElseThrow();
    }

    @Transactional
    public Optional<UserReadDTO> update(Long id, UserCreateEditDTO userDto){
        return userRepository.findById(id)
                .map(entity -> userMapper.userCreateEditDTOCopyToUser(userDto, entity))
                .map(userRepository::saveAndFlush)
                .map(userMapper::userToUserReadDTO);
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

}