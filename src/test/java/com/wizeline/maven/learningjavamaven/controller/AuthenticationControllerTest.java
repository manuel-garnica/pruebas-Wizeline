package com.wizeline.maven.learningjavamaven.controller;


import com.wizeline.maven.learningjavamaven.config.JwtTokenConfig;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    @Mock
    private JwtTokenConfig jwtTokenConfig;
    @Mock
    private UserDetailsService userDetailsService;
    @InjectMocks
    private AuthenticationController authenticationController;
    @Test
    @DisplayName("getAuthenticationToken ")
    public void DadoserviceLogin_CuandoEnviausuarioyContrasena_RegresaAuthenticationToken () {
        UserDetails user = new User("username", "password", Collections.emptyList());
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VybmFtZSIsInVzZXJuYW1lIjoidXNlcm5hbWUiLCJhdXRob3JpdGllcyI6IiIsImRhdGUiOjE2MTY4Mjk5MDU4MjAsImlhdCI6MTYxNjgyOTkwNX0.NI5P5ie_cX9sy-8b6IbwY6Z1XXQa1psycFwU6yavU0g";
        when(jwtTokenConfig.generateToken(any(), any())).thenReturn(token);
        UserDTO userDTO = new UserDTO("username", "password");
        ResponseEntity<?> response = authenticationController.getAuthenticationToken(userDTO);
        assertAll(
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
            () ->assertEquals(token, response.getBody())
        );
    }

    @Test
    public void DadoServiceLogin_CuandoEnviausuarioyContrasenaIncorrectos_RegresaUsuarioInvalido() {
        when(userDetailsService.loadUserByUsername(anyString()))
                .thenThrow(new UsernameNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO("unknown", "password");
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            authenticationController.getAuthenticationToken(userDTO);
        });
        assertAll(
                () ->assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus()),
                () -> assertEquals("User not found", exception.getReason())
        );
    }
}