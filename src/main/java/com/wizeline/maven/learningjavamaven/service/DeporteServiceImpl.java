package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.observer.Observable;
import com.wizeline.maven.learningjavamaven.observer.observers.CatalogoDeportesObserver;
import com.wizeline.maven.learningjavamaven.repository.DeporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeporteServiceImpl implements DeporteService{
        @Autowired
        private DeporteRepository deporteRepository;

        public List<DeporteDTO> obtenerTodosDeportes() {
            return deporteRepository.findAll();
        }

        public Optional<DeporteDTO> obtenerDeportePorId(String id) {
            return deporteRepository.findById(id);
        }

        public DeporteDTO crearDeporte(DeporteDTO deporte) {
            return deporteRepository.save(deporte);
        }

        public DeporteDTO actualizarDeporte(String id, DeporteDTO deporte) {
            Optional<DeporteDTO> deporteExistente = deporteRepository.findById(id);
            if (deporteExistente.isPresent()) {
                DeporteDTO deporteActualizado = deporteExistente.get();
                deporteActualizado.setNombre(deporte.getNombre());
                deporteActualizado.setDescripcion(deporte.getDescripcion());
                return deporteRepository.save(deporteActualizado);
            } else {
                throw new RuntimeException("Deporte no encontrado");
            }
        }

        public void eliminarDeporte(String id) {
            deporteRepository.deleteById(id);
        }
    }
