package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.client.AccountsJSONClient;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.Post;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {


    @Mock
    BankAccountService bankAccountService;
    @Mock
    BankAccountDTO BankAccountDTO;
    @Mock
    UserService userService;

    @Mock
    AccountsJSONClient accountsJSONClient;

    @Mock
    KafkaTemplate<Object, Object> kafkaTemplate;

    @InjectMocks
    BankingAccountController bankingAccountController;

    private static final Logger LOGGER = Logger.getLogger(BankingAccountControllerTest.class.getName());

    @Test
    void DadoServicioProcesaiddecuentas_Cuandoseneviaelid_Entoncesseagregaalacola() {
        LOGGER.info("Comienza sendUserAccount con kafka");
        List<BankAccountDTO> accounts = new ArrayList<>();
        accounts.add(new BankAccountDTO());
        accounts.add(new BankAccountDTO());
        LOGGER.info("Comienza getAccounts");
        when(bankAccountService.getAccounts()).thenReturn(accounts);
        bankingAccountController.sendUserAccount(1);
        LOGGER.info("Valida Kafka");
        verify(kafkaTemplate).send("useraccount-topic", accounts.get(1));
        verify(kafkaTemplate).send("prueba-Topic", accounts.get(1));
    }

    @Nested
    @DisplayName("Obtener cuentas por usuario")
    class ObtenerCuentas {
        @Test
        void DadoServicioobenercuentas_Cuandolafechanocumplelavalidacion_EntoncesResgresmensajedeerrorporfecha() {
            LOGGER.info("Test para fecha invalida");

            String user = "user1";
            String password = "password1";
            String date = "2023-03-05";
            ResponseEntity<?> responseEntity = bankingAccountController.getUserAccount(user, password, date);
            LOGGER.info("Response " + responseEntity.getBody());

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                    () -> assertEquals("Formato de Fecha Incorrecto", responseEntity.getBody())
            );
        }

        @Test
        void DadoServicioobenercuentas_Cuandolacontrasñanocumplelavalidacion_Entoncesregresaunerrordepassswordnocumple() {
            LOGGER.info("Test para contaseña invalida");
            String user = "user1";
            String password = "password1";
            String date = "01-09-1989";
            ResponseEntity<?> responseEntity = bankingAccountController.getUserAccount(user, password, date);
            LOGGER.info("Response " + responseEntity.getBody());

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                    () -> assertEquals("Contraseña no cumple con los parametros de seguridad", responseEntity.getBody())
            );
        }

        @Test
        void DadoServicioobenercuentas_Cuandolacontrasñacumplelavalidacion_Entoncesvalidaloginynoestaregistrado() {
            LOGGER.info("Test para contaseña invalida");
            String user = "user1";
            String password = "passA1@a";
            String date = "01-09-1989";
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("ER001");//ER001, OK000
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.setAccountName("guardadito");
            bankAccountDTO.setAccountBalance(1000.0);
            LOGGER.info("mock login");

            when(userService.login(user, password)).thenReturn(responseDTO);
            ResponseEntity<?> responseEntity = bankingAccountController.getUserAccount(user, password, date);
            LOGGER.info("Response " + responseEntity.getBody());

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                    () -> assertEquals("Password Incorrecto", responseEntity.getBody())
            );
        }

        @Test
        void DadoServicioobenercuentas_Cuandolacontrasñacumplelavalidacion_Entoncesvalidaloginestaregistrado() {
            LOGGER.info("Test password Valido");
            String user = "user1";
            String password = "passA1@a";
            String date = "01-09-1989";
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("OK000");//ER001, OK000
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.setAccountName("guardadito");
            bankAccountDTO.setAccountBalance(1000.0);
            LOGGER.info("Crea mock login");
            when(userService.login(user, password)).thenReturn(responseDTO);
            LOGGER.info("Crea getAccount");
            when(bankAccountService.getAccountDetails(anyString(), anyString())).thenReturn(bankAccountDTO);
            ResponseEntity<?> responseEntity = bankingAccountController.getUserAccount(user, password, date);
            LOGGER.info("Response  {}" + responseEntity.getBody());

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                    () -> assertEquals(bankAccountDTO, responseEntity.getBody())
            );
        }

    }

    @Test
    public void Dadoservicioobtienelascuentas_Cuandoobtienelascuentas_EntoncesentregalistadeCuentas() {
        LOGGER.info("Comienza getAccounts");

        List<BankAccountDTO> mockAccounts = new ArrayList<>();
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountName("guardadito");
        bankAccountDTO.setAccountBalance(1000.0);
        bankAccountDTO.setUser("usuario");
        mockAccounts.add(bankAccountDTO);
        LOGGER.info("Mock de los cuentas");
        when(bankAccountService.getAccounts()).thenReturn(mockAccounts);

        ResponseEntity<List<BankAccountDTO>> responseEntity = bankingAccountController.getAccounts();
        LOGGER.info("Response  {}" + responseEntity.getBody());
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertEquals(mockAccounts, responseEntity.getBody())
        );
    }

    @Test
    public void Dadoserviciocuentasportio_Cuandoserealizapeticion_Entoncesregresalistadecuentasportipo() {
        LOGGER.info("Comienza getAccountsGroupByType");
        List<BankAccountDTO> accounts = new ArrayList<>();
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountName("guardadito");
        bankAccountDTO.setAccountBalance(1000.0);
        bankAccountDTO.setUser("usuario");
        BankAccountDTO bankAccountDTO2 = new BankAccountDTO();
        bankAccountDTO2.setAccountName("guardadito2");
        bankAccountDTO2.setAccountBalance(1000.02);
        bankAccountDTO2.setUser("usuario2");
        accounts.add(bankAccountDTO);
        accounts.add(bankAccountDTO2);
        LOGGER.info("Mock de los cuentas");
        when(bankAccountService.getAccounts()).thenReturn(accounts);

        ResponseEntity<Map<String, List<BankAccountDTO>>> responseEntity = bankingAccountController.getAccountsGroupByType();
        LOGGER.info("responseEntity"+responseEntity.getBody());
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }

    @Test
    public void Dadoserviciocuentasportio_Cuandoserealizapeticion_Entoncesregresalistadecuentasporunfiltropornombredecuenta() {
        LOGGER.info("Comienza getAccountByName");

        List<BankAccountDTO> accounts = new ArrayList<>();
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountName("guardadito");
        bankAccountDTO.setAccountBalance(1000.0);
        bankAccountDTO.setUser("usuario");
        BankAccountDTO bankAccountDTO2 = new BankAccountDTO();
        bankAccountDTO2.setAccountName("guardadito2");
        bankAccountDTO2.setAccountBalance(1000.02);
        bankAccountDTO2.setUser("usuario2");
        accounts.add(bankAccountDTO);
        accounts.add(bankAccountDTO2);
        LOGGER.info("Mock de los cuentas");

        when(bankAccountService.getAccounts()).thenReturn(accounts, accounts);

        ResponseEntity<List<BankAccountDTO>> responseEntity = bankingAccountController.getAccountByName("guardadito");
        LOGGER.info("responseEntity"+responseEntity.getBody());

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }

        @Test
        public void Dadoserviciocuentasportio_Cuandoserealizapeticion_Entoncesregresalistadecuentasencriptadas () {
            LOGGER.info("Comienza getEncryptedAccounts");

            List<BankAccountDTO> accounts = new ArrayList<>();
            BankAccountDTO bankAccountDTO = new BankAccountDTO();
            bankAccountDTO.setAccountName("guardadito");
            bankAccountDTO.setAccountBalance(1000.0);
            bankAccountDTO.setUser("usuario");
            bankAccountDTO.setCountry("Mexico");
            BankAccountDTO bankAccountDTO2 = new BankAccountDTO();
            bankAccountDTO2.setAccountName("guardadito2");
            bankAccountDTO2.setAccountBalance(1000.02);
            bankAccountDTO2.setUser("usuario2");
            bankAccountDTO2.setCountry("Mexico");
            accounts.add(bankAccountDTO);
            accounts.add(bankAccountDTO2);

            when(bankAccountService.getAccounts()).thenReturn(accounts);

            ResponseEntity<List<BankAccountDTO>> responseEntity = bankingAccountController.getEncryptedAccounts();

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
            );
        }

    @Nested
    @DisplayName("Eliminar cuentas por usuario")
    class deleteAccounts {
        @Test
        public void Dadoserviciocuentasportio_Cuandoserealizapeticion_EntoncesregresaOKaleliminarlas() {
            LOGGER.info("Comienza deleteAccounts");
            when(bankAccountService.deleteAccounts()).thenReturn(true);

            ResponseEntity<String> responseEntity = bankingAccountController.deleteAccounts();

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
            );
        }

        @Test
        public void Dadoserviciocuentasportio_Cuandoserealizapeticionynoelimina_EntoncesregresErrordelServidor() {
            LOGGER.info("Comienza deleteAccounts false");
            when(bankAccountService.deleteAccounts()).thenReturn(false);

            ResponseEntity<String> responseEntity = bankingAccountController.deleteAccounts();

            assertAll(
                    () -> assertNotNull(responseEntity),
                    () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode())
            );
        }
    }

    @Test
    public void Dadoserviciocuentasportio_Cuandoserealizapeticion_Entoncesregresalistadecuentasporunfiltro() {
        LOGGER.info("Comienza getAccountByUser");

        List<BankAccountDTO> accounts = new ArrayList<>();
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountName("guardadito");
        bankAccountDTO.setAccountBalance(1000.0);
        bankAccountDTO.setUser("usuario");
        bankAccountDTO.setCountry("Mexico");
        BankAccountDTO bankAccountDTO2 = new BankAccountDTO();
        bankAccountDTO2.setAccountName("guardadito2");
        bankAccountDTO2.setAccountBalance(1000.02);
        bankAccountDTO2.setUser("usuario2");
        bankAccountDTO2.setCountry("Mexico");
        accounts.add(bankAccountDTO);
        accounts.add(bankAccountDTO2);

        when(bankAccountService.getAccountByUser(anyString())).thenReturn(accounts);

        ResponseEntity<List<BankAccountDTO>> responseEntity = bankingAccountController.getAccountByUser("user");

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }


    @Test
    public void DadoserviciopruebaSayHello_Cuandorespondeok_EntoncesRegresaelMensajesayHelloGuest() {
        LOGGER.info("Comienza sayHelloGuest");
        ResponseEntity<String> responseEntity = bankingAccountController.sayHelloGuest();

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );

    }
    @Test
    public void DadoservicioexternalUser_Cuandosolicitacuentas_EntoncesRegresalistadecuentas() {
        LOGGER.info("Comienza getExternalUser");
        Long userId = 1L;
        Post post = new Post();
        post.setUserId(String.valueOf(userId));
        post.setBody("No hay informacion");
        post.setTitle("Title");
        when(accountsJSONClient.getPostById(anyLong())).thenReturn(post);
        ResponseEntity<Post> responseEntity = bankingAccountController.getExternalUser(123L);

        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );

    }


}



