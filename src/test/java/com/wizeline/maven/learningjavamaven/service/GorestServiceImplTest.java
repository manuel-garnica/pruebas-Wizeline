package com.wizeline.maven.learningjavamaven.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.maven.learningjavamaven.model.UserGorest;
import com.wizeline.maven.learningjavamaven.model.UsersResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GorestServiceImplTest {

    @InjectMocks
    private GorestServiceImpl gorestService;

    @Mock
    private RestTemplate restTemplate;

    private final String API_BASE_URL = "https://gorest.co.in/public/v1";
    static UserGorest userGorest = new UserGorest();
    static UserGorest userGorest2 = new UserGorest();
    @BeforeAll
    static  void beforeAll() {

        userGorest.setId(1L);
        userGorest.setName("Nombre");
        userGorest.setGender("hombre");
        userGorest.setEmail("email@wize.com.mx");
        userGorest.setStatus("activo");
        userGorest2.setId(2L);
        userGorest2.setName("Nombre2");
        userGorest2.setGender("hombre2");
        userGorest2.setEmail("email2@wize.com.mx");
        userGorest2.setStatus("inactivo");
    }

    @Test
    void DadoApiexterna_CuandoObtieneListaDeUsuarios_RegresastatuslistaDeUsuarios() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(null);
        headers.setContentType(MediaType.valueOf(MediaType.TEXT_PLAIN_VALUE));
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

         UsersResponse usersResponse = new UsersResponse();
        usersResponse.setData(Arrays.asList(userGorest, userGorest2));
        ResponseEntity<UsersResponse> responseEntity = new ResponseEntity<>(usersResponse, HttpStatus.OK);

       // when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(UsersResponse.class))).thenReturn(responseEntity);
       when(restTemplate.exchange(API_BASE_URL + "/users", HttpMethod.GET, entity, UsersResponse.class))
               .thenReturn(responseEntity);

        List<UserGorest> users = gorestService.getUsers();

        assertEquals(2, users.size());
        assertEquals(userGorest, users.get(0));
        assertEquals(userGorest2, users.get(1));
    }

    @Test
    void DadoApiexternaVersion2_CuandoObtieneListaDeUsuarios_RegresastatuslistaDeUsuarios() {

       UsersResponse usersResponse = new UsersResponse();
        usersResponse.setData(Arrays.asList(userGorest, userGorest2));

        when(restTemplate.getForObject(API_BASE_URL + "/users", UsersResponse.class))
                .thenReturn(usersResponse);

        List<UserGorest> users = gorestService.getUsersV2();

        assertEquals(2, users.size());
        assertEquals(userGorest, users.get(0));
        assertEquals(userGorest2, users.get(1));
    }

    @Test
    void DadoApiexterna_CuandoObtieneListaDeUsuarios_RegresaStringDeUsuarios() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"id\":515721,\"name\":\"Dipali Mehrotra\",\"email\":\"dipali_mehrotra@bosco.name\",\"gender\":\"female\",\"status\":\"inactive\"}";
        UserGorest expectedUser =   objectMapper.readValue(json, UserGorest.class);
        UserGorest resultUser = gorestService.StringtoJsongetUsers();

        assertNotNull( resultUser);
    }
}