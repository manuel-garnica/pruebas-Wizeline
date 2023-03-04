package com.wizeline.maven.learningjavamaven.batch;

import com.wizeline.maven.learningjavamaven.model.CursoDTO;
import com.wizeline.maven.learningjavamaven.repository.CursoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;

public class CursoWriter implements ItemWriter<CursoDTO> {

    @Autowired
    CursoRepository cursoRepository;
    private String archivo;

    public CursoWriter(String archivo) {
        this.archivo = archivo;
    }


    @Override
    public void write(List<? extends CursoDTO> cursos) throws Exception {
        cursoRepository.saveAll(cursos);
    }
}
