package pl.clinicmanager.model;

import pl.clinicmanager.model.Patient;

import java.util.List;
import java.util.Optional;

public interface IPatientRepository {
    void save(Patient patient);

    List<Patient> findAll();

    Optional<Patient> findByPesel(String pesel);

    Optional<List<Patient>> findPatientsByLastName(String lastName);

    boolean deleteByPesel(String pesel);

    boolean updateByPesel(String pesel, Patient updatedPatient);
}
