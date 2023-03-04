package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.repository.DeporteRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeporteServiceImplTest {

    @InjectMocks
    private DeporteServiceImpl deporteService;
    @Mock
    private DeporteRepository deporteRepositoryMock;
    static DeporteDTO deporteDTO = new DeporteDTO();
    static DeporteDTO deporteDTO2 = new DeporteDTO();

    @BeforeAll
    static  void beforeAll(){
        deporteDTO.setId("id");
        deporteDTO.setNombre("Futbol");
        deporteDTO.setDescripcion("Juego de 11");
        deporteDTO.setFechaRegistro(new Date());

        deporteDTO2.setId("id2");
        deporteDTO2.setNombre("Futbol 7");
        deporteDTO2.setDescripcion("Juego de 7");
        deporteDTO2.setFechaRegistro(new Date());
    }
    @Test
    void DadoRepositorioDeportesEntregaUnaListadeDeportes_CuandoObtieneTodosLosDeportes_RegresaMismosElementos() {
        List<DeporteDTO> deportes = new ArrayList<>();
        deportes.add(deporteDTO);
        deportes.add(deporteDTO2);
        when(deporteRepositoryMock.findAll()).thenReturn(deportes);
        List<DeporteDTO> resultado = deporteService.obtenerTodosDeportes();

        assertEquals(2, resultado.size());
        assertEquals(deporteDTO.getNombre(), resultado.get(0).getNombre());
        assertEquals(deporteDTO2.getNombre(), resultado.get(1).getNombre());
    }

    @Test
    void DadoRepositorioDeportesEntregaUnDeportePorId_CuandoObtieneElDeportes_RegresaelElemento() {

        when(deporteRepositoryMock.findById("1")).thenReturn(Optional.of(deporteDTO));

        Optional<DeporteDTO> resultado = deporteService.obtenerDeportePorId("1");

        assertTrue(resultado.isPresent());
        assertEquals(deporteDTO.getNombre(), resultado.get().getNombre());
    }

    @Test
    void crearDeporteTest() {

        when(deporteRepositoryMock.save(any())).thenReturn(deporteDTO);

        DeporteDTO resultado = deporteService.crearDeporte(deporteDTO);

        assertEquals(deporteDTO.getNombre(), resultado.getNombre());
    }

    @Nested
    class ActualizarDeporte{


    @Test
    void DadoRepositorioDeportesEntregaUnDeportePorId_CuandoObtieneSeActualizaElDeporte_RegresaelElementoActualizado() {
        when(deporteRepositoryMock.findById("1")).thenReturn(Optional.of(deporteDTO));
        when(deporteRepositoryMock.save(deporteDTO)).thenReturn(deporteDTO);
        DeporteDTO resultado = deporteService.actualizarDeporte("1", deporteDTO);
        assertEquals(deporteDTO.getNombre(), resultado.getNombre());
    }

    @Test
    void DadoRepositorioDeportesEntregaUnDeportePorId_CuandoObtieneNOElDeportes_RegresaUnaExceptionConError() {
        when(deporteRepositoryMock.findById("1")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> deporteService.actualizarDeporte("1", new DeporteDTO()));
    }
    }

    @Test
    void DadoRepositorioDeportesEntregaUnDeportePorId_CuandoObtieneSeEliminaElDeporte_RegresastatusOK() {
        deporteService.eliminarDeporte("1");
        verify(deporteRepositoryMock, Mockito.times(1)).deleteById("1");
    }
}
