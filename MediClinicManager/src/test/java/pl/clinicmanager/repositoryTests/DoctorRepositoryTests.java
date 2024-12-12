package pl.clinicmanager.repositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.model.PersonalInfo;
import pl.clinicmanager.repository.DoctorRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorRepositoryTests {

    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        doctorRepository = new DoctorRepository();
    }

    @Test
    void saveDoctorAndRetrieveById() {
        Doctor doctor = new Doctor(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "123 Main St"), Set.of(DoctorSpecialty.CARDIOLOGY));

        doctorRepository.save(doctor);
        Optional<Doctor> retrievedDoctor = doctorRepository.findById(1);

        assertTrue(retrievedDoctor.isPresent());
        assertEquals("John", retrievedDoctor.get().getPersonalInfo().getFirstName());
        assertEquals("Doe", retrievedDoctor.get().getPersonalInfo().getLastName());
        assertEquals(Set.of(DoctorSpecialty.CARDIOLOGY), retrievedDoctor.get().getSpecialties());
    }

    @Test
    void findById_NotFound() {
        Optional<Doctor> retrievedDoctor = doctorRepository.findById(99);

        assertFalse(retrievedDoctor.isPresent());
    }

    @Test
    void findBySpecialty_Found() {
        Doctor doctor1 = new Doctor(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "123 Main St"), Set.of(DoctorSpecialty.CARDIOLOGY));
        Doctor doctor2 = new Doctor(2, new PersonalInfo("Jane", "Smith", "+48123456780", "jane.smith@example.com", "456 Elm St"), Set.of(DoctorSpecialty.NEUROLOGY, DoctorSpecialty.CARDIOLOGY));
        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);

        Set<Doctor> cardiologists = doctorRepository.findBySpecialty(DoctorSpecialty.CARDIOLOGY);

        assertEquals(2, cardiologists.size());
        assertTrue(cardiologists.contains(doctor1));
        assertTrue(cardiologists.contains(doctor2));
    }

    @Test
    void findBySpecialty_NotFound() {
        Doctor doctor = new Doctor(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "123 Main St"), Set.of(DoctorSpecialty.CARDIOLOGY));
        doctorRepository.save(doctor);

        Set<Doctor> neurologists = doctorRepository.findBySpecialty(DoctorSpecialty.NEUROLOGY);

        assertTrue(neurologists.isEmpty());
    }
}
