package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.enums.Country;
import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

    @InjectMocks
    BankAccountServiceImpl bankAccountService;
    @Mock
    BankingAccountRepository bankAccountRepository;

    @Mock
    MongoTemplate mongoTemplate;




    @Test
    public void DadoRepositoriobankAccountCollectionEntregaUnaListaDebankAccountCollection_CuandoObtieneTodosLosPaises_RegresaMismosElementos() {
        BankAccountDTO bankAccountOne = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", "user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        BankAccountDTO bankAccountTwo = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", "user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        BankAccountDTO bankAccountThree = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", "user2@wizeline.com", false, Country.US, LocalDateTime.now().minusYears(4));
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(bankAccountOne);
        accountDTOList.add(bankAccountTwo);
        accountDTOList.add(bankAccountThree);
        when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(accountDTOList);

        List<BankAccountDTO> result = bankAccountService.getAccounts();
        assertEquals(3, result.size());
    }

    @Test
    public void Dadounusuario_CuandoSeprocesanlosdatos_EntoncesregresacuentasconDetalles() {

        String user = "user@example.com";
        String lastUsage = "01-01-2022";
        BankAccountDTO expected = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", user, true, Country.MX, LocalDate.parse(lastUsage, DateTimeFormatter.ofPattern("dd-MM-yyyy")).atStartOfDay());

        BankAccountDTO result = bankAccountService.getAccountDetails(user, lastUsage);


        assertEquals(expected.getUser(), result.getUser());
    }

    @Test
    public void Dadounusuario_CuandoSeprocesanlosdatos_EntoncesregresacuentasconDetallesylafechadeseUltimoUso() {
        String user = "user@example.com";
        BankAccountDTO expected = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", user, true);
        BankAccountDTO result = bankAccountService.getAccountDetails(user);
        assertEquals(expected.getUser(), result.getUser());
    }

    @Nested
    class DeleteAccounts {
        @Test
        public void DadoRepositoriobankAccountCollection_Siempre_Eliminatodoslosregistros() {

            doNothing().when(bankAccountRepository).deleteAll();

            boolean result = bankAccountService.deleteAccounts();

            assertTrue(result);
            verify(bankAccountRepository, times(1)).deleteAll();
        }
        @Test
        public void DadoRepositoriobankAccountCollection_CuandolaBaseResponseFalse_EntoncesNoEliminalosregistros() {

            doThrow(RuntimeException.class).when(bankAccountRepository).deleteAll();

            boolean result = bankAccountService.deleteAccounts();

            assertFalse(result);
            verify(bankAccountRepository, times(1)).deleteAll();
        }
    }

    @Test
    public void DadoRepositoriobankAccountCollection_CuandosebsucaenBDporUser_EntoncesRegresalistadeCuentas() {
        BankAccountDTO bankAccountOne = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", "user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        BankAccountDTO bankAccountTwo = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", "user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        BankAccountDTO bankAccountThree = ReflectionTestUtils.invokeMethod(bankAccountService, "buildBankAccount", "user2@wizeline.com", false, Country.US, LocalDateTime.now().minusYears(4));
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        accountDTOList.add(bankAccountOne);
        accountDTOList.add(bankAccountTwo);
        accountDTOList.add(bankAccountThree);
        when(mongoTemplate.find(any(),any())).thenReturn(Collections.singletonList(accountDTOList));

        List<BankAccountDTO> result = bankAccountService.getAccountByUser("usuario");
        assertNotNull(result);
    }


}