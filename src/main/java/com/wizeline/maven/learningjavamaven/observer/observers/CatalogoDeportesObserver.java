package com.wizeline.maven.learningjavamaven.observer.observers;

import com.wizeline.maven.learningjavamaven.model.CatalogoDeportes;
import com.wizeline.maven.learningjavamaven.model.DeporteDTO;
import com.wizeline.maven.learningjavamaven.service.DeporteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CatalogoDeportesObserver implements Observer  {
    private DeporteService deporteService;
    public CatalogoDeportesObserver(DeporteService deporteService){
        this.deporteService=deporteService;
    }

    @Override
    public void update(boolean fueEditado) {
        System.out.println( " Actualizar Singleton  CatalogoDeportesObserver ");
        CatalogoDeportes catalogoDeportes =CatalogoDeportes.getInstance();
        try {
            catalogoDeportes.setDeporteDTO(deporteService.obtenerTodosDeportes());

        }catch (Exception e){
            List<DeporteDTO> lista= new ArrayList<DeporteDTO>();
            catalogoDeportes.setDeporteDTO(lista);

        }

    }
}
