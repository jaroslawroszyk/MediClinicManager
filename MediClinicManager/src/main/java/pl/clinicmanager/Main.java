package pl.clinicmanager;

import pl.clinicmanager.model.*;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;
import pl.clinicmanager.repository.PatientRepository;
import pl.clinicmanager.service.DoctorScheduleService;
import pl.clinicmanager.service.DoctorService;
import pl.clinicmanager.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        test_grafik();
    }

    public static void test_grafik() // grafik 4.1/4.2
    {
        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorScheduleRepository scheduleRepository = new DoctorScheduleRepository();
        DoctorScheduleService scheduleService = new DoctorScheduleService(scheduleRepository, doctorRepository);

        // Przypadek 1: Tworzenie grafiku dla istniejącego lekarza
        doctorRepository.save(new Doctor(1, "Jan", "Kowalski", "123456789", "jan.kowalski@example.com", "Address", null));
        scheduleService.createSchedule(1, LocalDateTime.of(2024, 11, 25, 9, 0), LocalDateTime.of(2024, 11, 25, 17, 0));

        List<DoctorSchedule> nextWeekSchedules = scheduleService.getSchedulesForNextWeek(1, LocalDate.of(2024, 11, 25));
        nextWeekSchedules.forEach(System.out::println);

        // Przypadek 2: Próba stworzenia grafiku dla nieistniejącego lekarza
        try {
            scheduleService.createSchedule(2, LocalDateTime.of(2024, 11, 26, 10, 0), LocalDateTime.of(2024, 11, 26, 14, 0));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void test_doctor() // zad 3
    {
        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository);

        Doctor doctor1 = new Doctor(1, "Anna", "Smith", "987654321", "anna.smith@example.com", "123 Medical St", new HashSet<>(Set.of(DoctorSpecialty.CARDIOLOGY, DoctorSpecialty.PEDIATRICS)));
        Doctor doctor2 = new Doctor(2, "John", "Doe", "123456789", "john.doe@example.com", "456 Health Ave", new HashSet<>(Set.of(DoctorSpecialty.ORTHOPEDICS, DoctorSpecialty.DERMATOLOGY)));
        Doctor doctor3 = new Doctor(3, "Emily", "Davis", "1122334455", "emily.davis@example.com", "789 Wellness Blvd", new HashSet<>(Set.of(DoctorSpecialty.DERMATOLOGY)));
        Doctor doctor4 = new Doctor(4, "d", "b", "1122334455", "emily.davis@example.com", "789 Wellness Blvd", new HashSet<>(Set.of(DoctorSpecialty.DERMATOLOGY)));

//        doctorService.createDoctorProfile(doctor1.getId(), doctor1.getFirstName(), doctor1.getLastName(), doctor1.getPhoneNumber(), doctor1.getEmail(), doctor1.getAddress(), doctor1.getSpecialties());
//        doctorService.createDoctorProfile(doctor2.getId(), doctor2.getFirstName(), doctor2.getLastName(), doctor2.getPhoneNumber(), doctor2.getEmail(), doctor2.getAddress(), doctor2.getSpecialties());
//        doctorService.createDoctorProfile(doctor3.getId(), doctor3.getFirstName(), doctor3.getLastName(), doctor3.getPhoneNumber(), doctor3.getEmail(), doctor3.getAddress(), doctor3.getSpecialties());
        doctorService.createDoctor(doctor1);
        doctorService.createDoctor(doctor2);
        doctorService.createDoctor(doctor3);
        doctorService.createDoctor(doctor4);

//        doctorService.addSpecialtyToDoctor(2, DoctorSpecialty.DERMATOLOGY);  // Dodanie nowej specjalizacji dla doktora z ID 2

        Doctor foundDoctor = doctorService.findDoctorById(1).orElse(null); // Szuka lekarza o ID 1
        if (foundDoctor != null) {
            System.out.println("Found doctor by ID 1: " + foundDoctor.getFirstName() + " " + foundDoctor.getLastName() + " - Specialties: " + foundDoctor.getSpecialties());
        } else {
            System.out.println("Doctor with ID 1 not found.");
        }

        Set<Doctor> cardiologists = doctorService.findDoctorsBySpecialty(DoctorSpecialty.DERMATOLOGY);
        System.out.println("\nDoctors with DERMATOLOGY specialty:");
        for (Doctor cardiologist : cardiologists) {
            System.out.println(cardiologist.getFirstName() + " " + cardiologist.getLastName() + " - ID: " + cardiologist.getId());
        }
    }

    public void test_2() {
        PatientRepository patientRepository = new PatientRepository();

        PatientService patientService = new PatientService(patientRepository);

        Patient p1 = new Patient("John", "Doe", "44051401359", new BirthDate("1990-05-15"), 18, "+48123456789", "john.doe@example.com");
        Patient p2 = new Patient("Adam", "Doe", "74092634317", new BirthDate("1992-06-10"), 22, "+48123456788", "john2.doe@example.com");

        patientService.addPatient(p1);
        patientService.addPatient(p2);
        List<Patient> foundPotientJohn = patientService.findPatientByLastName("Doe");

        for (Patient p : foundPotientJohn) {
            System.out.println(p.getFirstName() + " " + p.getLastName());
        }
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
