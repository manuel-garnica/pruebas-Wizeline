package com.wizeline.maven.learningjavamaven.model;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import com.wizeline.maven.learningjavamaven.enums.AccountType;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BankAccountDTOTest {

    @Test
    public void testToString() {
        BankAccountDTO account = mock(BankAccountDTO.class);
        when(account.getAccountNumber()).thenReturn(123456789L);
        when(account.getAccountName()).thenReturn("Guardadito");
        when(account.getUser()).thenReturn("user");
        when(account.getAccountBalance()).thenReturn(1000.00);
        when(account.getAccountType()).thenReturn(AccountType.NOMINA);
        when(account.getCountry()).thenReturn("Mexico");
        when(account.isAccountActive()).thenReturn(true);
        when(account.getLastUsage()).thenReturn("2022-02-28 12:00:00");
        when(account.getCreationDate()).thenReturn(LocalDateTime.of(2022, 02, 28, 12, 00, 00));
        String expectedString = "BankAccountDTO{accountNumber=123456789, accountName='Guardadito', user='user', accountBalance=1000.0, accountType=NOMINA, country='Mexico', accountActive=true, lastUsage='2022-02-28 12:00:00', creationDate=2022-02-28T12:00}";
        assertEquals(expectedString, account.toString());
    }
}

