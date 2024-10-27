package pl.clinicmanager.repositoryTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.repository.PatientRepository;

import java.util.Optional;

public class PatientRepository_Tests {
    private PatientRepository repository;
    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    public void setUp() {
        repository = new PatientRepository();
        patient1 = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        patient2 = new Patient("Jane", "Smith", "44051401359", null, 25, "123456789", "jane@example.com");
        repository.save(patient1);
        repository.save(patient2);
    }

    @Test
    public void testFindByPesel() {
        Optional<Patient> foundPatient = repository.findByPesel("44051401359");
        Assertions.assertTrue(foundPatient.isPresent());
        Assertions.assertEquals(patient1, foundPatient.get());
    }

    @Test
    public void testDeleteByPesel() {
        boolean deleted = repository.deleteByPesel("44051401359");
        Assertions.assertTrue(deleted);
        Assertions.assertFalse(repository.findByPesel("44051401359").isPresent());
    }

    @Test
    public void testUpdateByPesel() {
        Patient updatedPatient = new Patient("John", "Doe", "44051401359", null, 31, "123456789", "john.new@example.com");
        boolean updated = repository.updateByPesel("44051401359", updatedPatient);
        Assertions.assertTrue(updated);
        Assertions.assertEquals("123456789", repository.findByPesel("44051401359").get().getPhoneNumber());
    }

    @Test
    public void testFindAll() {
        Assertions.assertEquals(2, repository.findAll().size());
    }
}
