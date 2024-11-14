package pl.clinicmanager.model;

import java.util.List;

public interface IPatientService {
    boolean addPatient(Patient patient);

    Patient findPatientByPesel(String pesel);

    List<Patient> findPatientByLastName(String lastName);

    List<Patient> getAllPatients();

    boolean updatePatient(String pesel, Patient updatedPatient);

    boolean deletePatient(String pesel);
}
