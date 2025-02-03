package com.dev.willen.eduplanner.controllers;

import com.dev.willen.eduplanner.erros.StandardError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExcetionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> loginError(EntityNotFoundException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<StandardError> responseStatus(ResponseStatusException ex, HttpServletRequest request) {
        StandardError error = new StandardError(LocalDateTime.now(), ex.getStatusCode().value(), ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }
}
