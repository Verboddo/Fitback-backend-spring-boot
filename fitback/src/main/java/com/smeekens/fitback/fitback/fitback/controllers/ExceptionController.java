package com.smeekens.fitback.fitback.fitback.controllers;

import com.smeekens.fitback.fitback.fitback.exceptions.BadRequestException;
import com.smeekens.fitback.fitback.fitback.exceptions.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    public ResponseEntity<Object> exception(NotAuthorizedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }
}
