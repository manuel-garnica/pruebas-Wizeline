package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseGenericoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    // Given
    String user = "user";
    String password = "password";

    @Test
    public void DadoUserandPass_CuandoSoncorrectos_EntonceelServicioRegresaunOK() {

        ResponseDTO expectedResponse = new ResponseDTO();

        when(userService.login(user, password)).thenReturn(expectedResponse);

        ResponseEntity<ResponseDTO> responseEntity = userController.login(user, password);

        assertEquals(expectedResponse, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void DadoUserandPass_CuandoSoncorrectos_EntonceelServicioRegresaunOKconotroResponse() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUser(user);
        userDTO.setPassword(password);
        ResponseGenericoDTO expectedResponse = new ResponseGenericoDTO();
        expectedResponse.setCode(String.valueOf(200));

        when(userService.loginV2(userDTO.getUser(), userDTO.getPassword())).thenReturn(expectedResponse);

        ResponseEntity<ResponseGenericoDTO> responseEntity = userController.loginV2(userDTO);

        assertAll(
                () -> assertEquals(expectedResponse, responseEntity.getBody()),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );

    }

    @Test
    public void DadoobjetoUserDTO_CuandoelObjetook_EntoncesSecreaUsuario() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(user);
        userDTO.setPassword(password);
        ResponseDTO expectedResponse = new ResponseDTO();


        when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(expectedResponse);

        ResponseEntity<ResponseDTO> responseEntity = userController.createUser(userDTO);

        assertAll(
                () -> assertEquals(expectedResponse, responseEntity.getBody()),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }

}
