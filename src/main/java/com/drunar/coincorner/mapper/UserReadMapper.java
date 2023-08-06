package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {
        return new UserReadDto(object.getId(),
                object.getEmail(),
                object.getUsername(),
                object.getFirstname(),
                object.getLastname(),
                object.getBirthDate());
    }
}
