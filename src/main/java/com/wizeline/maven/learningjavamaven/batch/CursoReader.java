package com.wizeline.maven.learningjavamaven.batch;

import com.wizeline.maven.learningjavamaven.model.CursoDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CursoReader  implements ItemReader<CursoDTO> {
        private String archivo;
        private List<CursoDTO> cursos;
        private int indiceCursoActual;

        public CursoReader(String archivo) {
            this.archivo = archivo;
            cursos = new ArrayList<>();
            indiceCursoActual = 0;
        }

        @Override
        public CursoDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
            if (cursos.isEmpty()) {
                // Leer los datos del archivo CSV
                ClassPathResource classPathResource = new ClassPathResource(archivo);
                BufferedReader br = new BufferedReader(new FileReader(classPathResource.getFile()));
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println("------"+linea);
                    String[] campos = linea.split(";");
                    try {
                        CursoDTO curso = new CursoDTO(campos[0], campos[1], campos[2], campos[3]);
                        cursos.add(curso);
                    }catch (Exception e)
                    {
                        System.out.println("Error"+"Linea----"+linea);
                    }

                }
                br.close();
            }

            // Devolver el siguiente curso, o null si no hay más cursos
            if (indiceCursoActual < cursos.size()) {
                CursoDTO cursoActual = cursos.get(indiceCursoActual);
                indiceCursoActual++;
                return cursoActual;
            } else {
                return null;
            }
        }
    }
