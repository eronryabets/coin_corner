package com.drunar.coincorner.service;

import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserReadDTO> findAll(){
       return userRepository.findAll().stream()
               .map(userMapper::userToUserDTO).toList();
    }

}
