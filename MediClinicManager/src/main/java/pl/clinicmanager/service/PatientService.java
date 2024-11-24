package pl.clinicmanager.service;

import pl.clinicmanager.model.IPatientRepository;
import pl.clinicmanager.model.IPatientService;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.repository.PatientRepository;

import java.util.List;

/**
 * Service layer implementation for managing patient operations.
 * Acts as an intermediary between the controller layer and the repository layer.
 * Provides business logic for handling patients in the system.
 */
public class PatientService implements IPatientService {
    private IPatientRepository patientRepository;

//    public PatientService() {
//        this.patientRepository = new PatientRepository();
//    }

    /**
     * Constructor for injecting a custom implementation of IPatientRepository.
     * This is useful for testing or switching repository implementations without modifying the service layer.
     *
     * @param patientRepository the repository instance to use
     * // TODO: Decide if this constructor should be used exclusively for testing or extended to support dependency injection in production.
     */
    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Adds a new patient to the repository.
     *
     * @param patient the patient object to add
     * @return true if the patient was successfully added, false otherwise
     */
    @Override
    public boolean addPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Finds a patient by their PESEL (unique identifier).
     *
     * @param pesel the PESEL of the patient to find
     * @return the found patient, or null if no patient was found
     */
    @Override
    public Patient findPatientByPesel(String pesel) {
        return patientRepository.findByPesel(pesel).orElse(null);
    }

    /**
     * Finds a patient by their FirstName
     * todo: remove after testing
     */
    @Override
    public List<Patient> findPatientByFirstName(String firstName) { // For test purposes
        return patientRepository.findPatientsByFirstName(firstName).orElse(null);
    }

    /**
     * Finds patients by their last name.
     *
     * @param lastName the last name to search for
     * @return a list of matching patients, or null if none are found
     */
    @Override
    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findPatientsByLastName(lastName).orElse(null);
    }

    /**
     * Prints detailed information about a given patient.
     * Uses the patient's PESEL to fetch their full details from the repository.
     *
     * @param patient the patient whose information should be printed
     */
    public void printPatientInfo(Patient patient) {
        Patient found = findPatientByPesel(patient.getPesel());
        System.out.println("First Name: " + found.getFirstName());
        System.out.println("Last Name: " + found.getLastName());
        System.out.println("Pesel: " + found.getPesel());
        System.out.println("Birth data: " + found.getBirthDate());
        System.out.println("Age " + found.getAge());
        System.out.println("Phone Number " + found.getPhoneNumber());
        System.out.println("Email " + found.getEmail());
    }

    /**
     * Retrieves all patients stored in the repository.
     *
     * @return a list of all patients
     */
    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Updates the data of an existing patient identified by their PESEL.
     *
     * @param pesel          the PESEL of the patient to update
     * @param updatedPatient the updated patient data
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updatePatient(String pesel, Patient updatedPatient) {
        return patientRepository.updateByPesel(pesel, updatedPatient);
    }

    /**
     * Deletes a patient identified by their PESEL.
     *
     * @param pesel the PESEL of the patient to delete
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean deletePatient(String pesel) {
        return patientRepository.deleteByPesel(pesel);
    }
}
