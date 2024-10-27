package pl.clinicmanager.repository;

import pl.clinicmanager.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepository {
    private List<Patient> patientList = new ArrayList<>();

    public void save(Patient patient) {
        patientList.add(patient);
    }

    public List<Patient> findAll() {
        for(Patient patient : patientList) {
            System.out.println(patient);
        }
        return patientList;
    }

    public Optional<Patient> findByPesel(String pesel) {
        return patientList.stream()
                .filter(patient -> patient.getPesel().equals(pesel))
                .findFirst(); // TODO: what if we dont find?
    }

    // TODO: why list? Cause we can have similar names
    public Optional<List<Patient>> findPatientsByLastName(String lastName) {
        return Optional.of(patientList.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .toList());
    }

    public boolean deleteByPesel(String pesel) {
        return patientList.removeIf(patient -> patient.getPesel().equals(pesel));
    }

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
