package com.drunar.coincorner.http.rest;

import com.drunar.coincorner.database.filter.UserFilter;
import com.drunar.coincorner.dto.PageResponse;
import com.drunar.coincorner.dto.UserCreateEditDTO;
import com.drunar.coincorner.dto.UserReadDTO;
import com.drunar.coincorner.http.handler.OptionalResponseHandler;
import com.drunar.coincorner.service.UserServiceImpl;
import com.drunar.coincorner.validation.group.CreateActions;
import com.drunar.coincorner.validation.group.UpdateAction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name="User Rest Controller", description="User CRUD operations.")
@RequiredArgsConstructor
public class UserRestController {

    private final UserServiceImpl userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<UserReadDTO> findAll(UserFilter filter, Pageable pageable) {
        Page<UserReadDTO> page = userService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserReadDTO> findById(@PathVariable("id") Long id) {
        Optional<UserReadDTO> user = userService.findById(id);
        return OptionalResponseHandler.handleOptional(user);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDTO create(@Validated({Default.class, CreateActions.class}) @RequestBody UserCreateEditDTO user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserReadDTO update(@PathVariable("id") Long id,
                              @Validated({Default.class, UpdateAction.class})
                              @RequestBody UserCreateEditDTO user) {
        return userService.update(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/avatar")
    public ResponseEntity<byte[]> findAvatar(@PathVariable("id") Long id) {
        return userService.findAvatar(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

}
