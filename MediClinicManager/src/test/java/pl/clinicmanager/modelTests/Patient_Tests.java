package pl.clinicmanager.modelTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.model.PersonalInfo;

public class Patient_Tests {

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient(new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
    }

    @Test
    void checkIfFullNameIsReturnedCorrectly() {
        String patientName = patient.getPersonalInfo().getFirstName();

        String expectedName = "John";
        Assertions.assertEquals(expectedName, patientName);
    }

    @Test
    public void testSetFirstNameCapitalizesFirstLetter() {
        patient.getPersonalInfo().setFirstName("john");
        Assertions.assertEquals("John", patient.getPersonalInfo().getFirstName());

        patient.getPersonalInfo().setFirstName("mARY");
        Assertions.assertEquals("Mary", patient.getPersonalInfo().getFirstName());

        patient.getPersonalInfo().setFirstName("alice");
        Assertions.assertEquals("Alice", patient.getPersonalInfo().getFirstName());
    }

    @Test
    public void testSetLastNameCapitalizesFirstLetter() {
        patient.getPersonalInfo().setLastName("doe");
        Assertions.assertEquals("Doe", patient.getPersonalInfo().getLastName());

        patient.getPersonalInfo().setLastName("SMITH");
        Assertions.assertEquals("Smith", patient.getPersonalInfo().getLastName());

        patient.getPersonalInfo().setLastName("cARLSON");
        Assertions.assertEquals("Carlson", patient.getPersonalInfo().getLastName());
    }

    @Test
    public void testInvalidAgeThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient(new PersonalInfo("John", "Doe", "+48123456789", "invalid@email.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), -21);
        });
        Assertions.assertEquals(exception.getMessage(), "Age cannot be negative");
    }

    @Test
    public void testValidPatientCreation() {

        Assertions.assertEquals(patient.getPersonalInfo().getFirstName(), "John");
        Assertions.assertEquals(patient.getPersonalInfo().getLastName(), "Doe");
        Assertions.assertEquals(patient.getPesel(), "44051401359");
        Assertions.assertEquals(patient.getBirthDate(), "1990-05-15");
        Assertions.assertEquals(patient.getAge(), 18);
        Assertions.assertEquals(patient.getPersonalInfo().getPhoneNumber(), "+48123456789");
        Assertions.assertEquals(patient.getPersonalInfo().getEmail(), "john.doe@example.com");
    }

    @Test
    public void testInvalidEmailThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient(new PersonalInfo("John", "Doe", "+48123456789", "invalidemail.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid email format");
    }

    @Test
    public void testInvalidPhoneNumberThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Patient(new PersonalInfo("John", "Doe", "+invalidNumber", "invalidemail.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid phone number format");
    }

    @Test
    public void testSetValidEmail() {
        patient.getPersonalInfo().setEmail("new.email@example.com");
        Assertions.assertEquals(patient.getPersonalInfo().getEmail(), "new.email@example.com");
    }

    @Test
    public void testSetInvalidEmailThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.getPersonalInfo().setEmail("invalidemail.com");
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid email format");
    }

    @Test
    public void testSetValidPhoneNumber() {
        patient.getPersonalInfo().setPhoneNumber("+48123456790");
        Assertions.assertEquals(patient.getPersonalInfo().getPhoneNumber(), "+48123456790");
    }

    @Test
    public void testSetInvalidPhoneNumberThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.getPersonalInfo().setPhoneNumber("invalidNumber");
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
        Assertions.assertEquals(exception.getMessage(), "Invalid PESEL format");
    }

    @Test
    public void testSetNullPeselThrowsException() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            patient.setPesel(null);
        });
        Assertions.assertEquals(exception.getMessage(), "Invalid PESEL format");
    }

}
