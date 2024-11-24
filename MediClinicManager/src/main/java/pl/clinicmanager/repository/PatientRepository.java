package pl.clinicmanager.repository;

import pl.clinicmanager.model.IPatientRepository;
import pl.clinicmanager.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the IPatientRepository interface.
 * Provides basic in-memory storage and operations for managing patients.
 * This class simulates a data repository and is not connected to any database.
 */
public class PatientRepository implements IPatientRepository {
    private List<Patient> patientList = new ArrayList<>();

    /**
     * Saves a new patient to the repository.
     *
     * @param patient the patient object to add
     */
    @Override
    public void save(Patient patient) {
        patientList.add(patient);
    }

    /**
     * Retrieves all patients stored in the repository.
     * For debugging purposes, prints each patient to the console.
     *
     * @return a list of all patients
     */
    @Override
    public List<Patient> findAll() {
        for (Patient patient : patientList) {
            System.out.println(patient);
        }
        return patientList;
    }

    /**
     * Finds a patient by their PESEL (unique identifier).
     *
     * @param pesel the PESEL of the patient to find
     * @return an Optional containing the patient if found, or empty if not found
     */
    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return patientList.stream()
                .filter(patient -> patient.getPesel().equals(pesel))
                .findFirst(); // TODO: what if we dont find?
    }

    /**
     * Finds patients by their last name.
     * Since last names may not be unique, this method returns a list.
     *
     * @param lastName the last name to search for
     * @return an Optional containing a list of patients with the matching last name, or an empty Optional if none are found
     **/
    // TODO: why list? Cause we can have similar names
    @Override
    public Optional<List<Patient>> findPatientsByLastName(String lastName) {
        return Optional.of(patientList.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList());
    }

    @Override
    public Optional<List<Patient>> findPatientsByFirstName(String firstName) {
        return Optional.of(patientList.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName))
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
     * // TODO: Evaluate if merging data instead of replacing is a better approach.
     */
    @Override
    public boolean updateByPesel(String pesel, Patient updatedPatient) { // TODO: think about it
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
