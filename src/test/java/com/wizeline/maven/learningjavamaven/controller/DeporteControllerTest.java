package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.service.DeporteService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {DeporteController.class})
@ExtendWith(MockitoExtension.class)
class DeporteControllerTest {
    @Mock
    private DeporteService deporteService;

    @InjectMocks
    private DeporteController deporteController;

    @Test
    void obtenerDeportesSingleton() {
        ResponseEntity<List<DeporteDTO>> responseEntity = deporteController.obtenerDeportesSingleton();

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }

    @Test
    void obtenerDeportesSingleton2() {
        ResponseEntity<List<DeporteDTO>> responseEntity = deporteController.obtenerDeportesSingleton();

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }


    @Test
    void testObtenerDeportesdesdeService() throws Exception {
        when(deporteService.obtenerTodosDeportes()).thenReturn(new ArrayList<>());
        ResponseEntity<List<DeporteDTO>> responseEntity = deporteController.obtenerDeportes();
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }
}