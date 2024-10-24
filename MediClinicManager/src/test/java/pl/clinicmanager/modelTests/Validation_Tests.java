package pl.clinicmanager.modelTests;

import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Validator;

import static org.junit.jupiter.api.Assertions.*;

public class Validation_Tests {
    @Test
    public void testValidEmail() {
        assertTrue(Validator.isValidEmail("example@example.com"));
        assertTrue(Validator.isValidEmail("user@example.com"));
        assertTrue(Validator.isValidEmail("valid.email+alias@domain.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(Validator.isValidEmail("example.com"));
        assertFalse(Validator.isValidEmail("example@"));
        assertFalse(Validator.isValidEmail(null));
    }

    @Test
    public void testInvalidPhoneNumber() {
        assertFalse(Validator.isValidPhoneNumber("12345"));
        assertFalse(Validator.isValidPhoneNumber("1234567890123456"));
        assertFalse(Validator.isValidPhoneNumber("+12-345"));
        assertFalse(Validator.isValidPhoneNumber("phone123"));
        assertFalse(Validator.isValidPhoneNumber(null));
    }

    @Test
    public void testValidPhoneNumber() {
        assertTrue(Validator.isValidPhoneNumber("123456789"));
        assertTrue(Validator.isValidPhoneNumber("+1234567890123"));
        assertTrue(Validator.isValidPhoneNumber("9876543210"));
    }

    @Test
    public void testIsValidPesel_ValidPesel() {
        assertTrue(Validator.isValidPesel("44051401359"));
    }

    @Test
    public void testIsValidPesel_InvalidPeselFormat() {
        assertFalse(Validator.isValidPesel("4405140135"));
        assertFalse(Validator.isValidPesel("440514013590"));
        assertFalse(Validator.isValidPesel("abcde123456"));
    }

    @Test
    public void testIsValidPesel_InvalidPeselChecksum() {
        assertFalse(Validator.isValidPesel("44051401358"));
    }

    @Test
    public void testIsValidPesel_NullPesel() {
        assertFalse(Validator.isValidPesel(null));
    }

    @Test
    public void testIsValidEmail_ValidEmail() {
        assertTrue(Validator.isValidEmail("john.doe@example.com"));
    }

    @Test
    public void testIsValidEmail_InvalidEmailFormat() {
        assertFalse(Validator.isValidEmail("invalidemail.com"));
        assertFalse(Validator.isValidEmail("john.doe@.com"));
        assertFalse(Validator.isValidEmail("john.doe@com"));
    }

    @Test
    public void testIsValidEmail_NullEmail() {
        assertFalse(Validator.isValidEmail(null));
    }

    @Test
    public void testIsValidPhoneNumber_ValidPhoneNumber() {
        assertTrue(Validator.isValidPhoneNumber("+48123456789"));
    }

    @Test
    public void testIsValidPhoneNumber_InvalidPhoneNumberFormat() {
        assertFalse(Validator.isValidPhoneNumber("123456"));
        assertFalse(Validator.isValidPhoneNumber("invalidNumber"));
        assertFalse(Validator.isValidPhoneNumber("++48123456789"));
    }

    @Test
    public void testIsValidPhoneNumber_NullPhoneNumber() {
        assertFalse(Validator.isValidPhoneNumber(null));
    }
}
