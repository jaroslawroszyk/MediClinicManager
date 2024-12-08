package pl.clinicmanager.service;

import pl.clinicmanager.model.IPatientRepository;
import pl.clinicmanager.model.IPatientService;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.repository.PatientRepository;

import java.util.List;

public class PatientService implements IPatientService {
    private final IPatientRepository patientRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
    }

    public PatientService(IPatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public boolean addPatient(Patient patient) {
        try {
            patientRepository.save(patient);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Patient findPatientByPesel(String pesel) {
        return patientRepository.findByPesel(pesel).orElse(null);
    }

    @Override
    public List<Patient> findPatientByFirstName(String firstName) { // For test purposes
        return patientRepository.findPatientsByFirstName(firstName).orElse(null);
    }

    @Override
    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findPatientsByLastName(lastName).orElse(null);
    }

    public void printPatientInfo(Patient patient) {
        Patient found = findPatientByPesel(patient.getPesel());
        System.out.println("First Name: " + found.getPersonalInfo().getFirstName());
        System.out.println("Last Name: " + found.getPersonalInfo().getLastName());
        System.out.println("Pesel: " + found.getPesel());
        System.out.println("Birth data: " + found.getBirthDate());
        System.out.println("Age " + found.getAge());
        System.out.println("Phone Number " + found.getPersonalInfo().getPhoneNumber());
        System.out.println("Email " + found.getPersonalInfo().getEmail());
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public boolean updatePatient(String pesel, Patient updatedPatient) {
        return patientRepository.updateByPesel(pesel, updatedPatient);
    }

    @Override
    public boolean deletePatient(String pesel) {
        return patientRepository.deleteByPesel(pesel);
    }
}
