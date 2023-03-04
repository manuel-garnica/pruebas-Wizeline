package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.EstadosdeMexico;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/apiDireccion")
@RestController
public class DireecionController {

    @GetMapping("/obtenerEstadosArray")
    public ResponseEntity<String[]> EstadoArray() {
        EstadosdeMexico estado = new EstadosdeMexico();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(estado.obtenerArrayEstado(), responseHeaders, HttpStatus.OK);

    }
    @GetMapping("/obtenerEstadosLista")
    public ResponseEntity<List<String>> EstadoLista() {
        EstadosdeMexico estado = new EstadosdeMexico();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(estado.obtenerListaEstado(), responseHeaders, HttpStatus.OK);

    }
    @GetMapping("/obtenerEstadosMapa")
    public ResponseEntity<Map<Integer,String >> EstadoMapa() {
        EstadosdeMexico estado = new EstadosdeMexico();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(estado.obtenerMapaEstado(), responseHeaders, HttpStatus.OK);

    }
    }
