package pl.clinicmanager.model;

import pl.clinicmanager.model.Patient;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing Patient data in the repository.
 */
public interface IPatientRepository {

    /**
     * Saves a new patient to the repository.
     *
     * @param patient the Patient object to save
     */
    void save(Patient patient);

    /**
     * Retrieves all patients from the repository.
     *
     * @return a list of all Patient objects
     */
    List<Patient> findAll();

    /**
     * Find Patient by PESEL provided
     *
     * @param pesel the PESEL of the patient to find
     * @return an Optional containing the Patient object if found, otherwise empty
     */
    Optional<Patient> findByPesel(String pesel);

    /**
     * Finds all patients by last name provided.
     *
     * @param lastName the last name of the patients to find
     * @return an Optional containing a list of Patient objects if found, otherwise empty
     */
    Optional<List<Patient>> findPatientsByLastName(String lastName);

    Optional<List<Patient>> findPatientsByFirstName(String firstName);

    /**
     * Deletes a patient from the repository by their PESEL.
     *
     * @param pesel the PESEL of the patient to delete
     * @return true if the patient was successfully deleted, false otherwise
     */
    boolean deleteByPesel(String pesel);

    /**
     * Updates an existing patient's information using their PESEL.
     *
     * @param pesel          the PESEL of the patient to update
     * @param updatedPatient the new Patient object with updated information
     * @return true if the update was successful, false otherwise
     */
    boolean updateByPesel(String pesel, Patient updatedPatient);
}
