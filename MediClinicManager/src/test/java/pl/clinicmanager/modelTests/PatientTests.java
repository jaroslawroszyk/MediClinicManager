package pl.clinicmanager.modelTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.model.PersonalInfo;
import static org.junit.jupiter.api.Assertions.*;

public class PatientTests {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
    }

    @Test
    void checkIfFullNameIsReturnedCorrectly() {
        String patientName = patient.getPersonalInfo().getFirstName();

        String expectedName = "John";
        assertEquals(expectedName, patientName);
    }

    @Test
    public void testSetFirstNameCapitalizesFirstLetter() {
        patient.getPersonalInfo().setFirstName("john");
        assertEquals("John", patient.getPersonalInfo().getFirstName());

        patient.getPersonalInfo().setFirstName("mARY");
        assertEquals("Mary", patient.getPersonalInfo().getFirstName());

        patient.getPersonalInfo().setFirstName("alice");
        assertEquals("Alice", patient.getPersonalInfo().getFirstName());
    }

    @Test
    public void testSetLastNameCapitalizesFirstLetter() {
        patient.getPersonalInfo().setLastName("doe");
        assertEquals("Doe", patient.getPersonalInfo().getLastName());

        patient.getPersonalInfo().setLastName("SMITH");
        assertEquals("Smith", patient.getPersonalInfo().getLastName());

        patient.getPersonalInfo().setLastName("cARLSON");
        assertEquals("Carlson", patient.getPersonalInfo().getLastName());
    }

    @Test
    public void testInvalidAgeThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "invalid@email.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), -21);
        });
        assertEquals("Age cannot be negative", exception.getMessage());
    }

    @Test
    public void testValidPatientCreation() {
        assertEquals("John", patient.getPersonalInfo().getFirstName());
        assertEquals("Doe", patient.getPersonalInfo().getLastName());
        assertEquals("44051401359", patient.getPesel());
        assertEquals(18, patient.getAge());
        assertEquals("+48123456789", patient.getPersonalInfo().getPhoneNumber());
        assertEquals("john.doe@example.com", patient.getPersonalInfo().getEmail());
    }

    @Test
    public void testInvalidEmailThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "invalidemail.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testInvalidPhoneNumberThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient(1, new PersonalInfo("John", "Doe", "+invalidNumber", "invalidemail.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        });
        assertEquals("Invalid phone number format", exception.getMessage());
    }

    @Test
    public void testSetValidEmail() {
        patient.getPersonalInfo().setEmail("new.email@example.com");
        assertEquals("new.email@example.com", patient.getPersonalInfo().getEmail());
    }

    @Test
    public void testSetInvalidEmailThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.getPersonalInfo().setEmail("invalidemail.com");
        });
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    public void testSetValidPhoneNumber() {
        patient.getPersonalInfo().setPhoneNumber("+48123456790");
        assertEquals("+48123456790", patient.getPersonalInfo().getPhoneNumber());
    }

    @Test
    public void testSetInvalidPhoneNumberThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.getPersonalInfo().setPhoneNumber("invalidNumber");
        });
        assertEquals("Invalid phone number format", exception.getMessage());
    }

    @Test
    public void testSetPesel_ValidPesel() {
        String validPesel = "44051401359";
        patient.setPesel(validPesel);
        assertEquals(validPesel, patient.getPesel());
    }

    @Test
    public void testSetInvalidPeselFormatThrowsException() {
        String invalidPesel = "4405140135";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(invalidPesel);
        });
        assertEquals("Invalid PESEL format", exception.getMessage());
    }

    @Test
    public void testSetInvalidPeselChecksumThrowsException() {
        String invalidPesel = "44051401358";
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(invalidPesel);
        });
        assertEquals("Invalid PESEL format", exception.getMessage());
    }

    @Test
    public void testSetNullPeselThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(null);
        });
        assertEquals("Invalid PESEL format", exception.getMessage());
    }

}
