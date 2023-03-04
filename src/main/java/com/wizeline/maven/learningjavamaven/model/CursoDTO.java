package com.wizeline.maven.learningjavamaven.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ProcesamientoCursos")
public class CursoDTO {
    private String nombre;
    private String nivel;
    private String instructor;
    private String horario;

    public CursoDTO() {}

    public CursoDTO(String nombre, String nivel, String instructor, String horario) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.instructor = instructor;
        this.horario = horario;
    }

    // Getters y setters


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return nombre + "," + nivel + "," + instructor + "," + horario;
    }
}

