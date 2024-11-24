package pl.clinicmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.model.IPatientRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientService_Tests {
    private PatientService patientService;
    private IPatientRepository patientRepository;

    @BeforeEach
    void setUp() {
        patientRepository = mock(IPatientRepository.class);
        patientService = new PatientService(patientRepository);
    }

    @Test
    void addPatient_ShouldReturnTrue_WhenSaveSuccessful() {
        Patient patient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        doNothing().when(patientRepository).save(patient);

        boolean result = patientService.addPatient(patient);

        assertTrue(result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void addPatient_ShouldReturnFalse_WhenSaveFails() {
        Patient patient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        doThrow(new RuntimeException("Save failed")).when(patientRepository).save(patient);

        boolean result = patientService.addPatient(patient);

        assertFalse(result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void findPatientByPesel_ShouldReturnPatient_WhenPatientExists() {
        Patient patient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");

        when(patientRepository.findByPesel("12345678901")).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatientByPesel("12345678901");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(patientRepository, times(1)).findByPesel("12345678901");
    }

    @Test
    void findPatientByPesel_ShouldReturnNull_WhenPatientDoesNotExist() {
        when(patientRepository.findByPesel("12345678901")).thenReturn(Optional.empty());

        Patient result = patientService.findPatientByPesel("12345678901");

        assertNull(result);
        verify(patientRepository, times(1)).findByPesel("12345678901");
    }

    @Test
    void findPatientByLastName_ShouldReturnListOfPatients_WhenPatientsExist() {
        Patient patient1 = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        Patient patient2 = new Patient("Jane", "Doe", "66022365628", null, 30, "987654321", "jane@example.com");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientRepository.findPatientsByLastName("Doe")).thenReturn(Optional.of(patients));

        List<Patient> result = patientService.findPatientByLastName("Doe");

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findPatientsByLastName("Doe");
    }

    @Test
    void findPatientByLastName_ShouldReturnNull_WhenNoPatientsExist() {
        when(patientRepository.findPatientsByLastName("Smith")).thenReturn(Optional.empty());

        List<Patient> result = patientService.findPatientByLastName("Smith");

        assertNull(result);
        verify(patientRepository, times(1)).findPatientsByLastName("Smith");
    }

    @Test
    void getAllPatients_ShouldReturnListOfAllPatients() {
        Patient patient1 = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");
        Patient patient2 = new Patient("Jane", "Doe", "66022365628", null, 30, "987654321", "jane@example.com");

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientRepository.findAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void updatePatient_ShouldReturnTrue_WhenUpdateSuccessful() {
        Patient updatedPatient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");

        when(patientRepository.updateByPesel("12345678901", updatedPatient)).thenReturn(true);

        boolean result = patientService.updatePatient("12345678901", updatedPatient);

        assertTrue(result);
        verify(patientRepository, times(1)).updateByPesel("12345678901", updatedPatient);
    }

    @Test
    void updatePatient_ShouldReturnFalse_WhenUpdateFails() {
        Patient updatedPatient = new Patient("John", "Doe", "44051401359", null, 30, "123456789", "john@example.com");

        when(patientRepository.updateByPesel("12345678901", updatedPatient)).thenReturn(false);

        boolean result = patientService.updatePatient("12345678901", updatedPatient);

        assertFalse(result);
        verify(patientRepository, times(1)).updateByPesel("12345678901", updatedPatient);
    }

    @Test
    void deletePatient_ShouldReturnTrue_WhenDeleteSuccessful() {
        when(patientRepository.deleteByPesel("12345678901")).thenReturn(true);

        boolean result = patientService.deletePatient("12345678901");

        assertTrue(result);
    }
}