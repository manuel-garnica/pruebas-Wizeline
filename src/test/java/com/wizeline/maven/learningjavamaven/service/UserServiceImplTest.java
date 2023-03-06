package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.ErrorDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseGenericoDTO;
import com.wizeline.maven.learningjavamaven.repository.UserRepository;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Nested
    class CreateUser {
        @Test
        public void DadoUserAndPasswordCreaUser_CuanodElUsuarioesValido_EntoncesregresUnStatusOK() {
            String user = "user";
            String password = "password";
            String result = "success";
            when(userRepository.createUser(user, password)).thenReturn(result);
            ResponseDTO expected = new ResponseDTO();
            expected.setCode("OK000");
            expected.setStatus(result);
            ResponseDTO actual = userService.createUser(user, password);
            assertEquals(expected.getCode(), actual.getCode());
            assertEquals(expected.getStatus(), actual.getStatus());
        }

        @Test
        public void DadoUserAndPasswordAdnPassisNull_CuanodseValidoPassworAndUser_EntoncesregresaErorr() {
            String user = null;
            String password = null;
            String result = "fail";
            ResponseDTO expected = new ResponseDTO();
            expected.setCode("OK000");
            expected.setStatus(result);
            expected.setErrors(new ErrorDTO("ER001", "Error al crear usuario"));
            ResponseDTO actual = userService.createUser(user, password);
            assertEquals(expected.getStatus(), actual.getStatus());
        }
    }

    @Nested
    class Login{
    @Test
    public void DadoUserAndPassword_CuanodElUsuarioAndPasswordEsValido_EntoncesregresUnStatusOK() {
        String user = "user";
        String password = "password";
        String result = "success";
        when(userRepository.login(user, password)).thenReturn(result);
        ResponseDTO expected = new ResponseDTO();
        expected.setCode("OK000");
        expected.setStatus(result);
        ResponseDTO actual = userService.login(user, password);
        assertEquals(expected.getCode(), actual.getCode());
    }

    @Test
    public void DadoUserAndPassword_CuanodElUsuarioAndPasswordNoEsValido_EntoncesregresUnError() {
        String user = "user";
        String password = "wrong_password";
        String result = "Invalid username or password";
        when(userRepository.login(user, password)).thenReturn(result);
        ResponseDTO expected = new ResponseDTO();
        expected.setCode("ER001");
        expected.setErrors(new ErrorDTO("ER001", result));
        expected.setStatus("fail");
        ResponseDTO actual = userService.login(user, password);
        assertEquals(expected.getCode(), actual.getCode());
    }
}
    @Nested
    class Loginv2{
        @Test
        public void DadoUserAndPassword_CuanodElUsuarioAndPasswordEsValido_EntoncesregresUnStatusOK() {
            String user = "user";
            String password = "password";
            String result = "success";
            when(userRepository.login(user, password)).thenReturn(result);
            ResponseDTO expected = new ResponseDTO();
            expected.setCode("OK000");
            expected.setStatus(result);
            ResponseGenericoDTO actual = userService.loginV2(user, password);
            assertEquals(expected.getCode(), actual.getCode());
        }

        @Test
        public void DadoUserAndPassword_CuanodElUsuarioAndPasswordNoEsValido_EntoncesregresUnErrorV2() {
            String user = "user";
            String password = "wrong_password";
            String result = "Invalid username or password";
            when(userRepository.login(user, password)).thenReturn(result);
            ResponseDTO expected = new ResponseDTO();
            expected.setCode("ER001");
            expected.setErrors(new ErrorDTO("ER001", result));
            expected.setStatus("fail");
            ResponseGenericoDTO actual = userService.loginV2(user, password);
            assertEquals(expected.getCode(), actual.getCode());
        }
    }
}
