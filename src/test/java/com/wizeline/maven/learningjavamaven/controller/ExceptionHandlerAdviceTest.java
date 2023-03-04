package com.wizeline.maven.learningjavamaven.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ExceptionHandlerAdviceTest {

    @InjectMocks
    private ExceptionHandlerAdvice exceptionHandlerAdvice;
    @Test
    public void DadoHandlerAcces_CuandoSerechazalaAutentificacion_EnconcesReturnForbiddenHttpStatus() {
        AccessDeniedException accessDeniedException = new AccessDeniedException("Acceso denegado");

        ResponseEntity<String> responseEntity = exceptionHandlerAdvice.handleAccessDeniedException(accessDeniedException);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }

}



