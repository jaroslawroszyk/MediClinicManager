package pl.clinicmanager.service;

import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.model.IDoctorRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// TODO: create interface
public class DoctorService {
    private IDoctorRepository doctorRepository;

    public DoctorService(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor addSpecialtyToDoctor(int id, DoctorSpecialty specialty) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            Set<DoctorSpecialty> specialties = new HashSet<>(doctor.getSpecialties());
            specialties.add(specialty);
            doctor.setSpecialties(specialties);
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
