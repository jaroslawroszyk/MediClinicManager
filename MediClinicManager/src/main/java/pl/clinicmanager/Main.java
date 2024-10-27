package pl.clinicmanager;
//
import pl.clinicmanager.model.BirthDate;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.service.PatientService;
//
//public class Main {
//
//    public static void main(String[] args) {
//        Patient patient = new Patient("First", "Last");
//        patient.setEmail("fo@gmail.com");
//
//        System.out.println(patient.getFirstName());
//    }
//}


import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PatientService patientService = new PatientService();
//        Scanner scanner = new Scanner(System.in);
        Patient p1 = new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), 18, "+48123456789", "john.doe@example.com");
        Patient p2 = new Patient("John2", "Doe2", "44051401359", new BirthDate("1990-05-15"), 18, "+48123456789", "john.doe@example.com");
        patientService.addPatient(p1);
        patientService.addPatient(p2);
        p1.setLastName("dupa");
        patientService.updatePatient("44051401359", p1);
//        patientService.printPatientInfo(p1);
        List<Patient> d = patientService.findPatientByLastName("dek");
        patientService.printPatientInfo(d.get(0));


//        System.out.println("Patient list:" + patientService.getAllPatients().size());

//        List<Patient> patientPesel1 = patientService.getAllPatients();
//
//        for(Patient p : patientPesel1) {
//            System.out.println(p.getFirstName());
//        }

//        System.out.println(patientPesel1);
    }


//        while (true) {
//            System.out.println("1. Add Patient");
//            System.out.println("2. Find Patient by PESEL");
//            System.out.println("3. Exit");
//            System.out.print("Choose an option: ");
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            switch (choice) {
//                case 1:
//                    System.out.print("Enter first name: ");
//                    String firstName = scanner.nextLine();
//                    System.out.print("Enter last name: ");
//                    String lastName = scanner.nextLine();
//                    System.out.print("Enter PESEL: ");
//                    String pesel = scanner.nextLine();
//                    // TODO: should have more options.
//
//                    Patient patient = new Patient();
//                    patient.setFirstName(firstName);
//                    patient.setLastName(lastName);a
//                    patient.setPesel(pesel);
//                    patientService.addPatient(patient);
//                    System.out.println("Patient added successfully.");
//                    break;
//                case 2:
//                    System.out.print("Enter PESEL: ");
//                    String searchPesel = scanner.nextLine();
////                    Patient foundPatient = patientService.findPatientByPesel(searchPesel);
////                    if (foundPatient != null) {
////                        System.out.println("Patient found: " + foundPatient.getFirstName() + " " + foundPatient.getLastName());
////                    } else {
////                        System.out.println("Patient not found.");
////                    }
//                    break;
//                case 3:
//                    System.out.println("Exiting the application.");
//                    return;
//                default:
//                    System.out.println("Invalid option. Try again.");
//            }
//        }
//    }
}
