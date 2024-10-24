package pl.clinicmanager.modelTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.Patient;

public class Patient_Tests {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), 18, "+48123456789", "john.doe@example.com");
    }

    @Test
    void checkIfFullNameIsReturnedCorrectly() {
        String patientName = patient.getFirstName();

        String expectedName = "John";
        Assertions.assertEquals(expectedName, patientName);
    }

    @Test
    public void testSetFirstNameCapitalizesFirstLetter() {
        patient.setFirstName("john");
        Assertions.assertEquals("John", patient.getFirstName());

        patient.setFirstName("mARY");
        Assertions.assertEquals("Mary", patient.getFirstName());

        patient.setFirstName("alice");
        Assertions.assertEquals("Alice", patient.getFirstName());
    }

    @Test
    public void testSetFirstNameHandlesEmptyAndNull() {
        patient.setFirstName("");
        Assertions.assertEquals("", patient.getFirstName());

        patient.setFirstName(null);
        Assertions.assertNull(patient.getFirstName());
    }

    @Test
    public void testSetLastNameCapitalizesFirstLetter() {
        patient.setLastName("doe");
        Assertions.assertEquals("Doe", patient.getLastName());

        patient.setLastName("SMITH");
        Assertions.assertEquals("Smith", patient.getLastName());

        patient.setLastName("cARLSON");
        Assertions.assertEquals("Carlson", patient.getLastName());
    }

    @Test
    public void testSetLastNameHandlesEmptyAndNull() {
        patient.setLastName("");
        Assertions.assertEquals("", patient.getLastName());

        patient.setLastName(null);
        Assertions.assertNull(patient.getLastName());
    }

    @Test
    public void testSetAge() {
        patient.setLastName("");
        Assertions.assertEquals("", patient.getLastName());

        patient.setLastName(null);
        Assertions.assertNull(patient.getLastName());
    }

    @Test
    public void testInvalidAgeThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), -1, "+48123456789", "invalidemail.com");
        });
        Assertions.assertEquals(exception.getMessage(), "Age cannot be negative");
    }

    @Test
    public void testValidPatientCreation() {
        Assertions.assertEquals(patient.getFirstName(), "John");
        Assertions.assertEquals(patient.getLastName(), "Doe");
        Assertions.assertEquals(patient.getPesel(), "44051401359");
        Assertions.assertEquals(patient.getBirthDate(), "1990-05-15");
        Assertions.assertEquals(patient.getAge(), 18);
        Assertions.assertEquals(patient.getPhoneNumber(), "+48123456789");
        Assertions.assertEquals(patient.getEmail(), "john.doe@example.com");
    }


    @Test
    public void testInvalidEmailThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), 18, "+48123456789", "invalidemail.com");
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid email format");
    }

    @Test
    public void testInvalidPhoneNumberThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), 18, "invalidNumber", "john.doe@example.com");
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid phone number format");
    }

    @Test
    public void testSetValidEmail() {
        patient.setEmail("new.email@example.com");
        Assertions.assertEquals(patient.getEmail(), "new.email@example.com");
    }

    @Test
    public void testSetInvalidEmailThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setEmail("invalidemail.com");
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid email format");
    }

    @Test
    public void testSetValidPhoneNumber() {
        patient.setPhoneNumber("+48123456790");
        Assertions.assertEquals(patient.getPhoneNumber(), "+48123456790");
    }

    @Test
    public void testSetInvalidPhoneNumberThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPhoneNumber("invalidNumber");
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid phone number format");
    }

    @Test
    public void testSetPesel_ValidPesel() {
        String validPesel = "44051401359";
        patient.setPesel(validPesel);
        Assertions.assertEquals(validPesel, patient.getPesel());
    }

    @Test
    public void testSetInvalidPeselFormatThrowsException() {
        String invalidPesel = "4405140135";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(invalidPesel);
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid PESEL format");
    }

    @Test
    public void testSetInvalidPeselChecksumThrowsException() {
        String invalidPesel = "44051401358";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(invalidPesel);
        });
        Assertions.assertEquals(exception.getMessage(),"Invalid PESEL format");
    }

    @Test
    public void testSetNullPeselThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(null);
        });
        Assertions. assertEquals(exception.getMessage(), "Invalid PESEL format");
    }

}
/*
Part of tests can be deleted because its repetition on validation.
But consider that
*/
