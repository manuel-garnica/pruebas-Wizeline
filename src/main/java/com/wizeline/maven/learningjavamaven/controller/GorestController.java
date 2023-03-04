package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.UserGorest;
import com.wizeline.maven.learningjavamaven.service.GorestService;
import com.wizeline.maven.learningjavamaven.service.GorestServiceImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequestMapping("/apiGorest")
@RestController
public class GorestController {
    @Autowired
    GorestServiceImpl gorestService;
    private static final Logger LOGGER = Logger.getLogger(GorestController.class.getName());

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserGorest>> getUserGorests() {
        LOGGER.info("");
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
       // LOGGER.info(gorestService.getUsers().toString());
        List<UserGorest> userGorest= new ArrayList<>();
        userGorest= gorestService.getUsers();

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(userGorest, responseHeaders, HttpStatus.OK);

    }
    @GetMapping("/getUsersV2")
    public ResponseEntity<List<UserGorest>> getUserGorestsV2() {
        LOGGER.info("getUsersV2");
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        // LOGGER.info(gorestService.getUsers().toString());
        List<UserGorest> userGorest= new ArrayList<>();
        userGorest= gorestService.getUsersV2();

        Instant finalDeEjecucion = Instant.now();

        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(userGorest, responseHeaders, HttpStatus.OK);

    }

    @GetMapping("/getUser")
    public ResponseEntity<UserGorest> getUserGorest() {
        LOGGER.info("");
        Instant inicioDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET");
        // LOGGER.info(gorestService.getUsers().toString());
        UserGorest userGorest= gorestService.StringtoJsongetUsers();
        Instant finalDeEjecucion = Instant.now();
        LOGGER.info("LearningJava - Cerrando recursos ...");
        String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(userGorest, responseHeaders, HttpStatus.OK);

    }
}
