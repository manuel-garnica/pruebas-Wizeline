package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.EstadosdeMexico;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Direecion", description = "API los Estados de Mexico")
public class DireecionController {

    @GetMapping("/obtenerEstadosArray")
    @Operation(summary = "Regresa Array de los estados de mexico")
    public ResponseEntity<String[]> EstadoArray() {
        EstadosdeMexico estado = new EstadosdeMexico();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(estado.obtenerArrayEstado(), responseHeaders, HttpStatus.OK);

    }
    @GetMapping("/obtenerEstadosLista")
    @Operation(summary = "Regresa Lista de los estados de mexico")
    public ResponseEntity<List<String>> EstadoLista() {
        EstadosdeMexico estado = new EstadosdeMexico();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(estado.obtenerListaEstado(), responseHeaders, HttpStatus.OK);

    }
    @GetMapping("/obtenerEstadosMapa")
    @Operation(summary = "Regresa Mapa de los estados de mexico")
    public ResponseEntity<Map<Integer,String >> EstadoMapa() {
        EstadosdeMexico estado = new EstadosdeMexico();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(estado.obtenerMapaEstado(), responseHeaders, HttpStatus.OK);

    }
    }
