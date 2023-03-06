package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseGenericoDTO;
import com.wizeline.maven.learningjavamaven.service.DeporteService;
import io.github.bucket4j.Bucket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeporteControllerTest {
    @Mock
    private DeporteService deporteService;

    @InjectMocks
    private DeporteController deporteController;
    @Mock
    private Bucket bucket;
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



    @Nested
    class ObtenerDeportes{
        @Test
        @DisplayName("obtenerDeportes devuelve una lista de deportes")
        void DadoServicioObtenerDeportes_CuandoRepositorioObtieneTodosLosDatos_EntoncesdevuelveListaDeportes() {
            DeporteDTO deporteDTO = new DeporteDTO();
            deporteDTO.setId("id");
            deporteDTO.setNombre("Futbol");
            deporteDTO.setDescripcion("Juego de 11");
            deporteDTO.setFechaRegistro(new Date());
            DeporteDTO deporteDTO2 = new DeporteDTO();
            deporteDTO2.setId("id2");
            deporteDTO2.setNombre("Futbol 7");
            deporteDTO2.setDescripcion("Juego de 7");
            deporteDTO2.setFechaRegistro(new Date());


            when(deporteService.obtenerTodosDeportes()).thenReturn(Arrays.asList(
                    deporteDTO2,
                    deporteDTO
            ));

            ResponseEntity<List<DeporteDTO>> response = deporteController.obtenerDeportes();

            // Assert
            assertAll(
                    () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                    () -> assertEquals(2, response.getBody().size())
            );
        }

        @Test
        @DisplayName("obtenerDeportes devuelve un error 429 cuando se excede el límite de requests")
        void DadoServicioObtenerDeportes_CuandoNoExedaLas5Peticiones_EntoncesdevuelveListaDeportes() {
            // Act
            for (int i = 0; i < 5; i++) {
                deporteController.obtenerDeportes();
            }
            ResponseEntity<List<DeporteDTO>> response = deporteController.obtenerDeportes();

            // Assert
            assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
        }
    }

    @Nested
    class ObtenerDeporteporid{
        @Test
        @DisplayName("obtenerDeportes devuelve una lista de deportes")
        void DadoServicioObtenerDeportes_CuandoRepositorioObtieneTodosLosDatos_EntoncesdevuelveListaDeportesFiltrandoporid() {
            DeporteDTO deporteDTO = new DeporteDTO();
            deporteDTO.setId("id");
            deporteDTO.setNombre("Futbol");
            deporteDTO.setDescripcion("Juego de 11");
            deporteDTO.setFechaRegistro(new Date());
            Optional<DeporteDTO> optionalDeporte= Optional.of(deporteDTO);

            when(deporteService.obtenerDeportePorId(anyString())).thenReturn(Optional.of(deporteDTO));
            ResponseEntity<DeporteDTO> responseEntity = deporteController.obtenerDeportePorId("id");
            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
            );
        }
        @Test
        @DisplayName("obtenerDeportes devuelve una lista de deportes")
        void DadoServicioObtenerDeportes_CuandoRepositorioNoEncuentraDatos_EntoncesDevuelveElMensajeNoEncontrado() {
            DeporteDTO deporteDTO = new DeporteDTO();
            deporteDTO.setId("id");
            deporteDTO.setNombre("Futbol");
            deporteDTO.setDescripcion("Juego de 11");
            deporteDTO.setFechaRegistro(new Date());
            Optional<DeporteDTO> optionalDeporte= Optional.of(deporteDTO);

            when(deporteService.obtenerDeportePorId(anyString())).thenReturn(Optional.empty());
            ResponseEntity<DeporteDTO> responseEntity = deporteController.obtenerDeportePorId("id");

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode())
                    );
        }
        }
@Nested
    class actualizarDeporte {
    @Test
    void DadoactualizarDeporte_CuendoServiceactualizaDeporte_EntoncesRegresaObjetoActualizado() {

        DeporteDTO deporteDTO = new DeporteDTO();
        deporteDTO.setId("id");
        deporteDTO.setNombre("Fútbol");
        when(deporteService.actualizarDeporte(anyString(), any()))
                .thenReturn(deporteDTO);

        ResponseEntity<ResponseGenericoDTO> responseEntity = deporteController.actualizarDeporte("id", deporteDTO);

        assertAll(
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody())
        );
    }

    @Test
    void DadoactualizarDeporte_CuendoServicioactualizaDeportenoloencuenta_EntoncesRegresaRegresaelestatusNotFound() {

        DeporteDTO deporteDTO = new DeporteDTO();
        when(deporteService.actualizarDeporte(anyString(), any()))
                .thenThrow(new RuntimeException());

        ResponseEntity<ResponseGenericoDTO> responseEntity = deporteController.actualizarDeporte("id", deporteDTO);

        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode()),
                () -> assertNotNull(responseEntity.getBody())

        );
    }




}
    @Test
    void DadoServicioElimina_CuandoservicioElimina_EntoncesregresaunOk (){
        ResponseEntity<Void> responseEntity= deporteController.borrarDeporte("id");
    }


}