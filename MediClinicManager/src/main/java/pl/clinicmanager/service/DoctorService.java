package pl.clinicmanager.service;

import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.model.IDoctorRepository;

import java.util.Optional;
import java.util.Set;

// TODO: create interface
public class DoctorService {
    private IDoctorRepository doctorRepository;

    public DoctorService(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Tworzenie profilu lekarza z pełnymi danymi
    // TODO: refactor it to save whole doctor :)
    public Doctor createDoctorProfile(int id, String firstName, String lastName, String phoneNumber, String email, String address, Set<DoctorSpecialty> specialties) {
        Doctor doctor = new Doctor(id, firstName, lastName, phoneNumber, email, address, specialties);
        return doctorRepository.save(doctor);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }


    // Dodanie specjalizacji do istniejącego lekarza
    public Doctor addSpecialtyToDoctor(int id, DoctorSpecialty specialty) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            doctor.getSpecialties().add(specialty);
            return doctorRepository.save(doctor);
        } else {
            throw new IllegalArgumentException("Doctor not found");
        }
    }

    // Wyszukiwanie lekarza po ID
    public Optional<Doctor> findDoctorById(int id) {
        return doctorRepository.findById(id);
    }

    // Wyszukiwanie wszystkich lekarzy o danej specjalizacji
    public Set<Doctor> findDoctorsBySpecialty(DoctorSpecialty specialty) {
        return doctorRepository.findBySpecialty(specialty);
    }
}
