package pl.clinicmanager.reqTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import pl.clinicmanager.model.IPatientRepository;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.service.PatientService;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class ReqPatientTest {
    private PatientService patientService;
    private IPatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = mock(IPatientRepository.class);
        patientService = new PatientService(patientRepository);
    }

    @Test
    void receptionistCreateNewPatientProfile() // 1.1
    {
        /*
        As a receptionist, I want to be able to create a new patient profile containing their personal and contact information needed to provide medical services.
        */
        Patient patient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        patientService.addPatient(patient);

        verify(patientRepository, times(1)).save(patient);

        Assertions.assertNotNull(patient.getFirstName());
        Assertions.assertNotNull(patient.getLastName());
        Assertions.assertNotNull(patient.getPesel());
        Assertions.assertNotNull(patient.getPhoneNumber());
        Assertions.assertNotNull(patient.getEmail());
    }

    @Test
    void receptionistFindByPesel() { // 1.2
        /*
         As a receptionist, I want to be able to find a patient by PESEL number and display all of his data.
         */
        Patient patient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");

        when(patientRepository.findByPesel("44051401359")).thenReturn(Optional.of(patient));

        Optional<Patient> foundPatient = Optional.ofNullable(patientService.findPatientByPesel("44051401359"));

        Assertions.assertTrue(foundPatient.isPresent());
        Assertions.assertEquals(patient, foundPatient.get());
        Assertions.assertEquals("John", foundPatient.get().getFirstName());
        Assertions.assertEquals("Doe", foundPatient.get().getLastName());
        Assertions.assertEquals("44051401359", foundPatient.get().getPesel());

        verify(patientRepository, times(1)).findByPesel("44051401359");
    }

    @Test
    void receptionistFindByFirstNameAllMatchingPatients() { // 1.3.1 - delete it later
        /*
        TODO:
        for testing purposes delete it leter
         */
        Patient patient1 = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        Patient patient2 = new Patient("John", "Doe", "44051401359", null, 28, "987654321", "jane@example.com");
        List<Patient> patients = List.of(patient1, patient2);

        when(patientRepository.findPatientsByFirstName("John")).thenReturn(Optional.of(patients));

        List<Patient> foundPatients = patientService.findPatientByFirstName("John");

        Assertions.assertNotNull(foundPatients);
        Assertions.assertEquals(2, foundPatients.size());
        Assertions.assertTrue(foundPatients.contains(patient1));
        Assertions.assertTrue(foundPatients.contains(patient2));

        verify(patientRepository, times(1)).findPatientsByFirstName("John");
    }

    @Test
    void receptionistFindBySurnameAllMatchingPatients() { // 1.3
        /*
        Correct test
        As a receptionist I want to be able to search for all matching patients with a given last name and display all the details of the patients found.
         */
        Patient patient1 = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        Patient patient2 = new Patient("Ada,", "Doe", "44051401359", null, 28, "987654321", "jane@example.com");
        List<Patient> patients = List.of(patient1, patient2);

        when(patientRepository.findPatientsByLastName("Doe")).thenReturn(Optional.of(patients));

        List<Patient> foundPatients = patientService.findPatientByLastName("Doe");

        Assertions.assertNotNull(foundPatients);
        Assertions.assertEquals(2, foundPatients.size());
        Assertions.assertTrue(foundPatients.contains(patient1));
        Assertions.assertTrue(foundPatients.contains(patient2));

        verify(patientRepository, times(1)).findPatientsByLastName("Doe");
    }

}
