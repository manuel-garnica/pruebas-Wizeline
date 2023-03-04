package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;

import java.util.List;
import java.util.Optional;

public interface DeporteService {
    List<DeporteDTO> obtenerTodosDeportes();
    Optional<DeporteDTO> obtenerDeportePorId(String id);
    DeporteDTO crearDeporte(DeporteDTO deporte);
    DeporteDTO actualizarDeporte(String id, DeporteDTO deporte);
    void eliminarDeporte(String id);

}
