package com.drunar.coincorner.mapper;

import com.drunar.coincorner.database.entity.User;
import com.drunar.coincorner.dto.UserReadDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    private User user;

    @BeforeEach
    void init(){
        user = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .username("username")
                .firstname("firstname")
                .lastname("lastname")
                .birthDate(LocalDate.of(2000, 12, 12))
                .build();
    }

    @Test
    void shouldProperlyMapUserToUserReadDTO() {
        UserReadDTO dto = userMapper.userToUserReadDTO(user);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getUsername(), dto.getUsername());
        Assertions.assertEquals(user.getFirstname(), dto.getFirstname());
        Assertions.assertEquals(user.getLastname(), dto.getLastname());
        Assertions.assertEquals(user.getBirthDate(), dto.getBirthDate());

    }

    @Test
    void shouldProperlyMapUserReadDTOToUser() {
        UserReadDTO dto = userMapper.userToUserReadDTO(user);
        User user1 = userMapper.userDTOtoUser(dto);

        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user1.getId(), dto.getId());
        Assertions.assertEquals(user1.getEmail(), dto.getEmail());
        Assertions.assertEquals(user1.getUsername(), dto.getUsername());
        Assertions.assertEquals(user1.getFirstname(), dto.getFirstname());
        Assertions.assertEquals(user1.getLastname(), dto.getLastname());
        Assertions.assertEquals(user1.getBirthDate(), dto.getBirthDate());

    }

}
