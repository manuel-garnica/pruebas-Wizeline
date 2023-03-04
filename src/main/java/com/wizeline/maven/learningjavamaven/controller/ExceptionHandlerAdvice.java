package com.wizeline.maven.learningjavamaven.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
@Tag(name = "ExceptionHandlerAdvice", description = "ExceptionHandlerAdvice para el Acceso denegado")
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    @Operation(summary = "Regresa Error de acceso denegado")
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>("Acceso denegado", HttpStatus.FORBIDDEN);
    }

}