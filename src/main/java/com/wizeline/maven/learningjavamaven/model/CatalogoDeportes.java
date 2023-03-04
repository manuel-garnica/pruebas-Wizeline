package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.service.DeporteService;
import com.wizeline.maven.learningjavamaven.service.DeporteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CatalogoDeportes {
    private List<DeporteDTO> deporteDTO;
    private static CatalogoDeportes instance;

    private CatalogoDeportes(DeporteService deporteService) {
        try {
            deporteDTO = deporteService.obtenerTodosDeportes();
        }catch (Exception e){
            e.printStackTrace();
        }
        // Constructor privado para evitar la creación de instancias desde fuera de la clase

    }

    public static CatalogoDeportes getInstance(DeporteService deporteService) {
        if (instance == null) {
            synchronized (CatalogoDeportes.class) {
                if (instance == null) {
                    instance = new CatalogoDeportes(deporteService);
                }
            }
        }
        return instance;
    }
    public static CatalogoDeportes getInstance() {
        if (instance == null) {
            synchronized (CatalogoDeportes.class) {
                if (instance == null) {
                }
            }
        }
        return instance;
    }
    public List<DeporteDTO> getDeporteDTO() {
        return deporteDTO;
    }

    public void setDeporteDTO(List<DeporteDTO> deporteDTO) {
        this.deporteDTO = deporteDTO;
    }

}