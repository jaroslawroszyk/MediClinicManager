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
    public boolean deleteByPesel(String pesel) {
        return patientList.removeIf(patient -> patient.getPesel().equals(pesel));
    }

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
}
