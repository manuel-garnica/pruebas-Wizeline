package com.wizeline.maven.learningjavamaven.observer.observers;

import com.wizeline.maven.learningjavamaven.model.CatalogoDeportes;
import com.wizeline.maven.learningjavamaven.service.DeporteService;
import org.springframework.beans.factory.annotation.Autowired;

public class CatalogoDeportesObserver implements Observer  {
    private DeporteService deporteService;
    public CatalogoDeportesObserver(DeporteService deporteService){
        this.deporteService=deporteService;
    }

    @Override
    public void update(boolean fueEditado) {
        System.out.println( " Actualizar Singleton  CatalogoDeportesObserver ");
        CatalogoDeportes catalogoDeportes =CatalogoDeportes.getInstance();
        catalogoDeportes.setDeporteDTO(deporteService.obtenerTodosDeportes());

    }
}
