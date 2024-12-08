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
//        test_grafik();
//        test_doctor();
        test_2();
    }

    public static void test_grafik() // grafik 4.1/4.2
    {
        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorScheduleRepository scheduleRepository = new DoctorScheduleRepository();
        DoctorScheduleService scheduleService = new DoctorScheduleService(scheduleRepository, doctorRepository);
        Doctor doctor = new Doctor(1, new PersonalInfo("Jan", "Kowalski", "123456789", "jan.kowalski@example.com", "Address"), null);
        doctorRepository.save(doctor);

        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 25, 17, 0);

        DoctorSchedule doctorSchedule = new DoctorSchedule(doctor, startTime, endTime);
        scheduleService.createSchedule(doctorSchedule);

        List<DoctorSchedule> nextWeekSchedules = scheduleService.getSchedulesForNextWeek(2, startTime);
        nextWeekSchedules.forEach(System.out::println);

        doctorSchedule = new DoctorSchedule(doctor, startTime, endTime);
        try {
            scheduleService.createSchedule(doctorSchedule);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void test_doctor() // zad 3
    {
        DoctorRepository doctorRepository = new DoctorRepository();
        DoctorService doctorService = new DoctorService(doctorRepository);

        Doctor doctor1 = new Doctor(1, new PersonalInfo("Jan", "Kowalski", "123456789", "jan.kowalski@example.com", "Address"), new HashSet<>(Set.of(DoctorSpecialty.CARDIOLOGY, DoctorSpecialty.PEDIATRICS)));
        Doctor doctor2 = new Doctor(2, new PersonalInfo("Jana", "Kowalski2", "123456789", "jana.kowalski2@example.com", "Address"), new HashSet<>(Set.of(DoctorSpecialty.ORTHOPEDICS, DoctorSpecialty.DERMATOLOGY)));
        Doctor doctor3 = new Doctor(3, new PersonalInfo("Jan2", "Kowalski3", "123456789", "jan.kowalski3@example.com", "Address"), new HashSet<>(Set.of(DoctorSpecialty.DERMATOLOGY)));
        Doctor doctor4 = new Doctor(4, new PersonalInfo("Jan1", "Kowalski4", "123456789", "jan.kowals42ki@example.com", "Address"), new HashSet<>(Set.of(DoctorSpecialty.DERMATOLOGY)));

        doctorService.createDoctor(doctor1);
        doctorService.createDoctor(doctor2);
        doctorService.createDoctor(doctor3);
        doctorService.createDoctor(doctor4);
        doctorService.printAllDoctors();
    }

    public static void test_2() {
        PatientRepository patientRepository = new PatientRepository();
        PatientService patientService = new PatientService(patientRepository);

        // todo: id powinno sie roznic
        Patient p1 = new Patient(1,new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        Patient p2 = new Patient(2, new PersonalInfo("Adam", "Doe", "+74092634317", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1992-06-10"), 22);
        patientService.addPatient(p1);
        patientService.addPatient(p2);

        for (Patient p : patientService.findPatientByLastName("Doe")) {
            System.out.println(p.getPersonalInfo().getFirstName() + " " + p.getPersonalInfo().getLastName());
        }

        p1.getPersonalInfo().setLastName("UpdatedLastName");
        boolean updateResult = patientService.updatePatient("44051401359", p1);
        System.out.println("\nPatient updated: " + updateResult);

        List<Patient> patientsByLastName = patientService.findPatientByLastName("UpdatedLastName");
        if (!patientsByLastName.isEmpty()) {
            patientService.printPatientInfo(patientsByLastName.get(0));
        } else {
            System.out.println("No patient found with the last name 'Doe'.");
        }

        Patient foundPatient = patientService.findPatientByPesel("74092634317");
        if (foundPatient != null) {
            System.out.println("Found patient by PESEL: " + foundPatient.getPersonalInfo().getFirstName() + " " + foundPatient.getPersonalInfo().getLastName());
        } else {
            System.out.println("Patient not found by PESEL.");
        }
    }
}
