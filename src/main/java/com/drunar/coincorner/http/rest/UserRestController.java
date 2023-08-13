package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.http.handler.OptionalResponseHandler;
import com.drunar.coincorner.service.UserService;
import com.drunar.coincorner.validation.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.LruCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<UserReadDTO> findAll(UserFilter filter, Pageable pageable) {
        Page<UserReadDTO> page = userService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    @Tag(name="Find", description="User find by id")
    public ResponseEntity<UserReadDTO> findById(@PathVariable("id") Long id) {
        Optional<UserReadDTO> user = userService.findById(id);
        return OptionalResponseHandler.handleOptional(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Tag(name="Create", description="User creation")
    public UserReadDTO create(@Validated({Default.class, LruCache.CreateAction.class})
                                  @RequestBody UserCreateEditDTO user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    @Tag(name="Update", description="User update")
    public UserReadDTO update(@PathVariable("id") Long id,
                              @Validated({Default.class, UpdateAction.class})
                              @RequestBody UserCreateEditDTO user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Tag(name="Delete", description="User delete")
    public void delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
