package com.drunar.coincorner.service;

import com.drunar.coincorner.database.repository.UserRepository;
import com.drunar.coincorner.dto.UserReadDto;
import com.drunar.coincorner.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    public List<UserReadDto> findAll(){
       return userRepository.findAll().stream()
               .map(userReadMapper::map).toList();
    }

}
