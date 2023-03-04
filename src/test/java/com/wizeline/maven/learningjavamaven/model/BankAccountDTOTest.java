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

class BankAccountDTOTest {
    @Test
    public void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new SetterMustExistRule())
                .with(new SetterTester()).with(new GetterTester()).build();
        validator.validate(PojoClassFactory.getPojoClass(BankAccountDTO.class));
    }
    @Test
    public final void testToString() {
        assertFalse(new BankAccountDTO().toString().contains("@"));

    }
}

