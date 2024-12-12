package pl.clinicmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.model.PersonalInfo;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.model.IDoctorRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DoctorServiceTests {
    private DoctorService doctorService;
    private IDoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        doctorRepository = new DoctorRepository();
        doctorService = new DoctorService(doctorRepository);
    }

    @Test
    void createDoctorProfile() {
        Set<DoctorSpecialty> specialties = new HashSet<>();
        specialties.add(DoctorSpecialty.CARDIOLOGY);
        specialties.add(DoctorSpecialty.NEUROLOGY);

        Doctor doctor = new Doctor(1, new PersonalInfo("John", "Doe", "123456789", "john.doe@example.com", "Some Address"), specialties);
        doctorRepository.save(doctor);
        Doctor createdDoctor = doctorService.createDoctor(doctor);

        assertNotNull(createdDoctor);
        assertEquals(1, createdDoctor.getId());
        assertEquals("John", createdDoctor.getPersonalInfo().getFirstName());
        assertEquals("Doe", createdDoctor.getPersonalInfo().getLastName());
        assertEquals("123456789", createdDoctor.getPersonalInfo().getPhoneNumber());
        assertEquals("john.doe@example.com", createdDoctor.getPersonalInfo().getEmail());
        assertEquals("Some Address", createdDoctor.getPersonalInfo().getAddress());
        assertTrue(createdDoctor.getSpecialties().contains(DoctorSpecialty.CARDIOLOGY));
        assertTrue(createdDoctor.getSpecialties().contains(DoctorSpecialty.NEUROLOGY));
    }

    @Test
    void addSpecialtyToDoctor() {
        Set<DoctorSpecialty> specialties = new HashSet<>();
        specialties.add(DoctorSpecialty.CARDIOLOGY);

        Doctor doctor = new Doctor(1, new PersonalInfo("John", "Doe", "123456789", "john.doe@example.com", "Some Address"), specialties);
        doctorRepository.save(doctor);

        Doctor updatedDoctor = doctorService.addSpecialtyToDoctor(1, DoctorSpecialty.ORTHOPEDICS);

        assertNotNull(updatedDoctor);
        assertTrue(updatedDoctor.getSpecialties().contains(DoctorSpecialty.ORTHOPEDICS));
    }

    @Test
    void findDoctorById() {
        Set<DoctorSpecialty> specialties = new HashSet<>();
        specialties.add(DoctorSpecialty.CARDIOLOGY);

        Doctor doctor = new Doctor(1, new PersonalInfo("John", "Doe", "123456789", "john.doe@example.com", "Some Address"), specialties);
        doctorRepository.save(doctor);

        Optional<Doctor> foundDoctor = doctorService.findDoctorById(1);

        assertTrue(foundDoctor.isPresent());
        assertEquals(1, foundDoctor.get().getId());
        assertEquals("John", foundDoctor.get().getPersonalInfo().getFirstName());
        assertTrue(foundDoctor.get().getSpecialties().contains(DoctorSpecialty.CARDIOLOGY));
    }

    @Test
    void findDoctorsBySpecialty() {
        Set<DoctorSpecialty> specialties1 = new HashSet<>();
        specialties1.add(DoctorSpecialty.CARDIOLOGY);
        Doctor doctor1 = new Doctor(1, new PersonalInfo("John", "Doe", "123456789", "john.doe@example.com", "Some Address"), specialties1);

        Set<DoctorSpecialty> specialties2 = new HashSet<>();
        specialties2.add(DoctorSpecialty.CARDIOLOGY);
        Doctor doctor2 = new Doctor(2, new PersonalInfo("Jane", "Smith", "987654321", "jane.smith@example.com", "Another Address"), specialties2);

        doctorRepository.save(doctor1);
        doctorRepository.save(doctor2);

        Set<Doctor> foundDoctors = doctorService.findDoctorsBySpecialty(DoctorSpecialty.CARDIOLOGY);

        assertEquals(2, foundDoctors.size());
    }
}
