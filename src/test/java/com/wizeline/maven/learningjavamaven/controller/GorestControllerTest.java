package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.UserGorest;
import com.wizeline.maven.learningjavamaven.service.GorestServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GorestControllerTest {

    @Mock
    private GorestServiceImpl gorestService;

    @InjectMocks
    private GorestController gorestController;

    private List<UserGorest> userGorestList;

    @BeforeEach
    void setUp() {
        userGorestList = new ArrayList<>();
        UserGorest user1 = new UserGorest();
        user1.setId(1L);
        user1.setName("nombre1");
        user1.setEmail("email@example.com");
        userGorestList.add(user1);

        UserGorest user2 = new UserGorest();
        user2.setId(2L);
        user2.setName("nombre2");
        user2.setEmail("email2@example.com");
        userGorestList.add(user2);
    }

    @Test
    public void DadoservicioGores_CuandoApiRegresalistna_EntoncesRegresaListaUsuarios() {
        when(gorestService.getUsers()).thenReturn(userGorestList);

        ResponseEntity<List<UserGorest>> responseEntity = gorestController.getUserGorests();

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () ->assertEquals(userGorestList, responseEntity.getBody())
        );
    }

    @Test
    public void DadoservicioGores_CuandoApiRegresalistaDelaVersion2_EntoncesRegresaListaUsuarios() {
        when(gorestService.getUsersV2()).thenReturn(userGorestList);

        ResponseEntity<List<UserGorest>> responseEntity = gorestController.getUserGorestsV2();

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () ->assertEquals(userGorestList, responseEntity.getBody())
        );
    }

    @Test
    public void DadoservicioGores_CuandoApiRegresalista_EntoncesRegresaListaUsuariosString() {
        UserGorest userGorest = new UserGorest();
        userGorest.setId(1L);
        userGorest.setName("user");
        userGorest.setEmail("email@example.com");

        when(gorestService.StringtoJsongetUsers()).thenReturn(userGorest);

        ResponseEntity<UserGorest> responseEntity = gorestController.getUserGorest();

        assertAll(
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () ->assertEquals(userGorest, responseEntity.getBody())
        );
    }

}