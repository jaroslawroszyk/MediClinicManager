package pl.clinicmanager;

import pl.clinicmanager.model.Patient;

public class Main {

    public static void main(String[] args) {
        Patient patient = new Patient("First", "Last");
        patient.setEmail("fo@gmail.com");

        System.out.println(patient.getFirstName());
    }
}
