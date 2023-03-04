package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.EstadosdeMexico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DireecionControllerTest {

    @InjectMocks
    private DireecionController direccionController;

    @Spy
    private EstadosdeMexico estadosdeMexico;



    @Test
    @DisplayName("obtenerEstadosArray debe retornar el arreglo de estados")
    void DadoaservicioEstadodeMexico_CuendoObjetoEstado_EntonceRegresaArraydeEstados() {
        ResponseEntity<String[]> response = direccionController.EstadoArray();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("obtenerEstadosLista debe retornar la lista de estados")
    void DadoaservicioEstadodeMexico_CuendoObjetoEstado_EntonceRegresaListadeEstados() {

        ResponseEntity<List<String>> response = direccionController.EstadoLista();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("obtenerEstadosMapa debe retornar el mapa de estados")
    void DadoaservicioEstadodeMexico_CuendoObjetoEstado_EntonceRegresaMapasdeEstados() {

        ResponseEntity<Map<Integer, String>> response = direccionController.EstadoMapa();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
