package pl.clinicmanager.model;

import java.util.List;

/**
 * Service interface for managing patient operations.
 * Provides business-level operations related to Patient management.
 */
public interface IPatientService {
    /**
     * Adds a new patient to the system.
     * @param patient the Patient object to be added
     * @return true if the patient was successfully added, false otherwise
     */
    boolean addPatient(Patient patient);
    /**
     * Finds a patient by their PESEL (national identification number).
     * @param pesel the PESEL of the patient to find
     * @return the Patient object if found, otherwise null
     */
    Patient findPatientByPesel(String pesel);
    /**
     * Finds patients by their last name.
     * @param lastName the last name of the patients to find
     * @return a list of Patient objects with the specified last name
     */
    List<Patient> findPatientByLastName(String lastName);
    /**
     * Retrieves all patients in the system.
     * @return a list of all Patient objects
     */
    List<Patient> getAllPatients();
    /**
     * Updates an existing patient's information using their PESEL.
     * @param pesel the PESEL of the patient to update
     * @param updatedPatient the new Patient object with updated information
     * @return true if the update was successful, false otherwise
     */
    boolean updatePatient(String pesel, Patient updatedPatient);
    /**
     * Deletes a patient from the system using their PESEL.
     * @param pesel the PESEL of the patient to delete
     * @return true if the patient was successfully deleted, false otherwise
     */
    boolean deletePatient(String pesel);
}
