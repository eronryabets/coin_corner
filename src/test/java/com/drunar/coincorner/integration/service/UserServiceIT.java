package com.drunar.coincorner.integration.service;

import com.drunar.coincorner.database.entity.Role;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.integration.IntegrationTestBase;
import com.drunar.coincorner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;

    private final UserService userService;

    @Test
    void findAll() {
        List<UserReadDTO> result = userService.findAll();
        assertThat(result).hasSize(10);
    }

    @Test
    void findById() {
        Optional<UserReadDTO> maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("user1", user.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDTO userDTO = new UserCreateEditDTO(
                "test1@gmail.com",
                "username",
                "123",
                "firstname",
                "lastname",
                LocalDate.now(),
                new MockMultipartFile("test",new byte[0]),
                Collections.singleton(Role.USER)
        );

        UserReadDTO actualResult = userService.create(userDTO);

        assertEquals(userDTO.getUsername(), actualResult.getUsername());
        assertEquals(userDTO.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDTO.getFirstname(), actualResult.getFirstname());
        assertEquals(userDTO.getLastname(), actualResult.getLastname());

    }

    @Test
    void update() {
        UserCreateEditDTO userDTO = new UserCreateEditDTO(
                "test1@gmail.com",
                "username",
                "123",
                "firstname",
                "lastname",
                LocalDate.now(),
                new MockMultipartFile("test",new byte[0]),
                Collections.singleton(Role.USER)
        );

        Optional<UserReadDTO> actualResult = userService.update(USER_1, userDTO);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDTO.getUsername(), user.getUsername());
            assertEquals(userDTO.getBirthDate(), user.getBirthDate());
            assertEquals(userDTO.getFirstname(), user.getFirstname());
            assertEquals(userDTO.getLastname(), user.getLastname());
        });


    }

    @Test
    void delete() {
        assertFalse(userService.delete(-124L));
        assertTrue(userService.delete(USER_1));
    }
}