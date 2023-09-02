package com.drunar.coincorner.http.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.drunar.coincorner.http.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception) {
        log.error("Access Denied", exception);
        return "error/accessDenied";
    }

    @ExceptionHandler(Exception.class)
    public String handleOtherExceptions(Exception exception) {
        log.error("Failed to return response", exception);
        return "error/error";
    }
}
