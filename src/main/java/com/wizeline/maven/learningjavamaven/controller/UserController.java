package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseGenericoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.repository.UserRepositoryImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
@Tag(name = "Usuario", description = "Api para usarios")
public class UserController {

    @Autowired
    UserService  userService;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

@GetMapping(value = "/login",produces = "application/json")

    public ResponseEntity<ResponseDTO> login (@RequestParam String user, String password){
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET - Starting...");
        ResponseDTO response = new ResponseDTO();
        response= userService.login(user,password);
    LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/login",produces = "application/json")
    @Operation(summary = "Login V2")
    public ResponseEntity<ResponseGenericoDTO> loginV2 (@RequestBody UserDTO request){
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo GET - Starting...");
        ResponseGenericoDTO response = new ResponseGenericoDTO();
        response= userService.loginV2(request.getUser(),request.getPassword());
        LOGGER.info("Login - Completed");
        return new ResponseEntity<ResponseGenericoDTO>(response, HttpStatus.OK);
    }
    @PostMapping(value = "/createUser", produces = "application/json")
    @Operation(summary = "Crear usuario")
    public ResponseEntity<ResponseDTO> createUser (@RequestBody UserDTO request){
        LOGGER.info("LearningJava - Procesando peticion HTTP de tipo POST - Starting...");
        ResponseDTO response = new ResponseDTO();
        response= userService.createUser(request.getUser(),request.getPassword());
        LOGGER.info("Created user -Completed");
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
}
