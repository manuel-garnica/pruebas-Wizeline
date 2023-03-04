package com.wizeline.maven.learningjavamaven.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wizeline.maven.learningjavamaven.enums.Country;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class UtilsTest {
    @Test
    void testGetString() {
        assertEquals("42", Utils.getString("42"));
        assertEquals("", Utils.getString(null));
    }

    @Test
    void testValidateNullValue() {
        assertTrue(Utils.validateNullValue("42"));
        assertFalse(Utils.validateNullValue(null));
    }
    @Test
    void testIsPasswordValid() {
        assertFalse(Utils.isPasswordValid("iloveyou"));
    }

    @Test
    void testIsDateFormatValid() {
        assertFalse(Utils.isDateFormatValid("2020-03-01"));
        assertTrue(Utils.isDateFormatValid("99-99-9999"));
    }
    @Test
    void testRandomAcountNumber() {
        Utils.randomAcountNumber();
    }
    @Test
    void testRandomBalance() {
        Utils.randomBalance();
    }

    @Test
    void testPickRandomAccountType() {
        Utils.pickRandomAccountType();
    }

    @Test
    void testRandomInt() {

        Utils.randomInt();
    }

    @Test
    void testGetCountry() {
        assertEquals("United States", Utils.getCountry(Country.US));
        assertEquals("Mexico", Utils.getCountry(Country.MX));
        assertEquals("France", Utils.getCountry(Country.FR));
    }
}

