package com.drunar.coincorner.http.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class OptionalResponseHandler {

    public static <T> ResponseEntity<T> handleOptional(Optional<T> optional) {
        return optional
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
