package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.service.DeporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DeporteControllerTest {
    @Mock
    private DeporteService deporteService;

    @InjectMocks
    private DeporteController deporteController;
    @Test
    void obtenerDeportesSingleton() {
        ResponseEntity<List<DeporteDTO>> responseEntity= deporteController.obtenerDeportesSingleton();

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }
}