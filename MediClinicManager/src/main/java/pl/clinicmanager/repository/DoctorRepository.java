package pl.clinicmanager.repository;

import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.model.IDoctorRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DoctorRepository implements IDoctorRepository {
    private Map<Integer, Doctor> doctorMap = new HashMap<>();

    @Override
    public Doctor save(Doctor doctor) {
        doctorMap.put(doctor.getId(), doctor);
        return doctor;
    }

    @Override
    public Optional<Doctor> findById(int id) {
        return Optional.ofNullable(doctorMap.get(id));
    }

    @Override
    public Set<Doctor> findBySpecialty(DoctorSpecialty specialty) {
        Set<Doctor> result = new HashSet<>();
        for (Doctor doctor : doctorMap.values()) {
            if (doctor.getSpecialties().contains(specialty)) {
                result.add(doctor);
            }
        }
        return result;
    }

    @Override
    public void printAllDoctors()
    {
        for (Doctor doctor : doctorMap.values()) {
            System.out.println("Doctor Id " + doctor.getId());
            doctor.getPersonalInfo().printInfo();
            System.out.println("\n");
        }
    }

}
