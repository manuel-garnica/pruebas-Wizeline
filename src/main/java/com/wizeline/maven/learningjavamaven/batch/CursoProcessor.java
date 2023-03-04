package com.wizeline.maven.learningjavamaven.batch;

import com.wizeline.maven.learningjavamaven.model.CursoDTO;
import org.springframework.batch.item.ItemProcessor;

public class CursoProcessor implements ItemProcessor<CursoDTO, CursoDTO> {
    @Override
    public CursoDTO process(CursoDTO curso) throws Exception {
        // Agregar la columna adicional
        String procesado = "SI";
        CursoDTO cursoProcesado = new CursoDTO(curso.getNombre(), curso.getNivel(), curso.getInstructor(), curso.getHorario() + "," + procesado);
        return cursoProcesado;
    }
}