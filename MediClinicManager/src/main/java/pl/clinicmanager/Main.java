package pl.clinicmanager;

import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.repository.PatientRepository;
import pl.clinicmanager.service.PatientService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        PatientRepository patientRepository = new PatientRepository();

        PatientService patientService = new PatientService(patientRepository);

        Patient p1 = new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), 18, "+48123456789", "john.doe@example.com");
        Patient p2 = new Patient("John2", "Doe2", "74092634317", new BirthDate("1992-06-10"), 22, "+48123456788", "john2.doe@example.com");

        patientService.addPatient(p1);
        patientService.addPatient(p2);

        p1.setLastName("UpdatedLastName");
        boolean updateResult = patientService.updatePatient("44051401359", p1);
        System.out.println("Patient updated: " + updateResult);

        List<Patient> patientsByLastName = patientService.findPatientByLastName("UpdatedLastName");//p1.getLastName());
        if (!patientsByLastName.isEmpty()) {
            patientService.printPatientInfo(patientsByLastName.get(0));
        } else {
            System.out.println("No patient found with the last name 'Doe'.");
        }

        Patient foundPatient = patientService.findPatientByPesel("74092634317");
        if (foundPatient != null) {
            System.out.println("Found patient by PESEL: " + foundPatient.getFirstName() + " " + foundPatient.getLastName());
        } else {
            System.out.println("Patient not found by PESEL.");
        }
    }
}
