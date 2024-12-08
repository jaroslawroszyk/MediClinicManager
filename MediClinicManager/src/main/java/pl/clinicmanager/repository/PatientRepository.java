package pl.clinicmanager.repository;

import pl.clinicmanager.model.IPatientRepository;
import pl.clinicmanager.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepository implements IPatientRepository {
    private final List<Patient> patientList = new ArrayList<>();

    @Override
    public void save(Patient patient) {
        patientList.add(patient);
    }

    @Override
    public List<Patient> findAll() {
        for (Patient patient : patientList) {
            System.out.println(patient);
        }
        return patientList;
    }

    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return patientList.stream()
                .filter(patient -> patient.getPesel().equals(pesel))
                .findFirst();
    }

    @Override
    public Optional<List<Patient>> findPatientsByLastName(String lastName) {
        return Optional.of(patientList.stream()
                .filter(p -> p.getPersonalInfo().getLastName().equalsIgnoreCase(lastName))
                .toList());
    }

    @Override
    public Optional<List<Patient>> findPatientsByFirstName(String firstName) {
        return Optional.of(patientList.stream()
                .filter(p -> p.getPersonalInfo().getFirstName().equalsIgnoreCase(firstName))
                .toList());
    }

    /**
     * Deletes a patient by their PESEL.
     *
     * @param pesel the PESEL of the patient to delete
     * @return true if a patient was removed, false if no matching patient was found
     */
    @Override
    public boolean deleteByPesel(String pesel) {
        return patientList.removeIf(patient -> patient.getPesel().equals(pesel));
    }

    /**
     * Updates a patient's record by their PESEL.
     * Replaces the existing patient data with the updated data.
     *
     * @param pesel          the PESEL of the patient to update
     * @param updatedPatient the updated patient object
     * @return true if the update was successful, false if no matching patient was found
     */
    @Override
    public boolean updateByPesel(String pesel, Patient updatedPatient) {
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPesel().equals(pesel)) {
                patientList.set(i, updatedPatient);
                return true;
            }
        }
        return false;
    }


    // missing functions?
}
