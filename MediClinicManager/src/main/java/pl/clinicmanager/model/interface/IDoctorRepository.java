package pl.clinicmanager.model;

import java.util.Optional;
import java.util.Set;

public interface IDoctorRepository {
    Doctor save(Doctor doctor);

    Optional<Doctor> findById(int id);

    Set<Doctor> findBySpecialty(DoctorSpecialty specialty);
}
