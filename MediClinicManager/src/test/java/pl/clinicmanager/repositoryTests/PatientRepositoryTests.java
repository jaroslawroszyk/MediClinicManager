package pl.clinicmanager.repositoryTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.model.PersonalInfo;
import pl.clinicmanager.repository.PatientRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class PatientRepositoryTests {
    private PatientRepository repository;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    public void setUp() {
        repository = new PatientRepository();
        patient1 = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        patient2 = new Patient(1, new PersonalInfo("Jane", "Smith", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        repository.save(patient1);
        repository.save(patient2);
    }

    @Test
    public void testFindByPesel() {
        Optional<Patient> foundPatient = repository.findByPesel("44051401359");
        assertTrue(foundPatient.isPresent());
        assertEquals(patient1, foundPatient.get());
    }

    @Test
    public void testDeleteByPesel() {
        boolean deleted = repository.deleteByPesel("44051401359");
        assertTrue(deleted);
        assertFalse(repository.findByPesel("44051401359").isPresent());
    }

    @Test
    public void testUpdateByPesel() {
        Patient updatedPatient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        boolean updated = repository.updateByPesel("44051401359", updatedPatient);
        assertTrue(updated);
        assertEquals("+48123456789", repository.findByPesel("44051401359").get().getPersonalInfo().getPhoneNumber());
    }

    @Test
    public void testFindAll() {
        assertEquals(2, repository.findAll().size());
    }
}
