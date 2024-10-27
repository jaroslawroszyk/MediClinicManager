package pl.clinicmanager.service;

import pl.clinicmanager.model.Patient;
import pl.clinicmanager.repository.PatientRepository;

import java.util.List;

public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService() {
        this.patientRepository = new PatientRepository();
    }

    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    public Patient findPatientByPesel(String pesel) {
        return patientRepository.findByPesel(pesel).orElse(null);
    }

    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findPatientsByLastName(lastName).orElse(null);
    }

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

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public boolean updatePatient(String pesel, Patient updatedPatient) {
        return patientRepository.updateByPesel(pesel, updatedPatient);
    }

    public boolean deletePatient(String pesel) {
        return patientRepository.deleteByPesel(pesel);
    }
}
