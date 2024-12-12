package pl.clinicmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.*;
import pl.clinicmanager.model.IDoctorRepository;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;
import pl.clinicmanager.repository.MedicalAppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicalAppointmentService_Tests {
    private DoctorScheduleRepository scheduleRepository;
    private MedicalAppointmentRepository appointmentRepository;
    private MedicalAppointmentService appointmentService;
    private DoctorService doctorService;
    private Doctor doctor;
    private PatientService patientService;


    @BeforeEach
    void setUp() {
        scheduleRepository = new DoctorScheduleRepository();
        appointmentRepository = new MedicalAppointmentRepository();
        IDoctorRepository doctorRepository = new DoctorRepository();
        doctorService = new DoctorService(doctorRepository);
        patientService = new PatientService();
        appointmentService = new MedicalAppointmentService(appointmentRepository, patientService, doctorService, scheduleRepository);
        doctor = new Doctor(1, new PersonalInfo("John", "Doe"), null);
        doctorService.createDoctor(doctor);
    }

    @Test
    void bookAppointment_NonExistentDoctor() {
        int patientId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);
        patientService.addPatient(new Patient(patientId, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18));

        Exception exception = assertThrows(RuntimeException.class,
                () -> appointmentService.bookAppointment("44051401359", 211, startTime));

        assertEquals("Doctor not found", exception.getMessage());
    }

    @Test
    void bookAppointment_ValidAppointment() {
        int doctorId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);

        DoctorSchedule schedule = new DoctorSchedule(doctor, LocalDateTime.of(2024, 11, 25, 9, 0), LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);
        Patient patient = new Patient(1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        patientService.addPatient(patient);

        appointmentService.bookAppointment(patient.getPesel(), doctorId, startTime);

        List<MedicalAppointment> appointments = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime);
        assertEquals(1, appointments.size());
        assertEquals(doctorId, appointments.get(0).getDoctor().getId());
        assertEquals(1, appointments.get(0).getPatient().getId());
        assertEquals(startTime, appointments.get(0).getStartTime());
    }

    @Test
    void bookAppointment_DoctorAlreadyBooked() {
        int doctorId = 1;
        int patientId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);

        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime, LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);
        Patient patient = new Patient(patientId, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        patientService.addPatient(patient);
        MedicalAppointment existingAppointment = new MedicalAppointment(patient, doctor, startTime);
        appointmentRepository.save(existingAppointment);

        assertThrows(RuntimeException.class, () -> appointmentService.bookAppointment(patient.getPesel(), doctorId, startTime));
    }

    @Test
    void bookAppointment_MultipleAppointmentsValid() {
        int doctorId = 1;
        int patientId1 = 1;
        int patientId2 = 2;
        LocalDateTime startTime1 = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 11, 25, 9, 35);

        Doctor doctor = new Doctor(doctorId, new PersonalInfo("John", "Doe"), null);
        doctorService.createDoctor(doctor);

        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime1, LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);

        Patient patient1 = new Patient(patientId1, new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        Patient patient2 = new Patient(patientId2, new PersonalInfo("John2", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        patientService.addPatient(patient1);
        patientService.addPatient(patient2);

        appointmentService.bookAppointment(patient1.getPesel(), doctorId, startTime1);
        appointmentService.bookAppointment(patient2.getPesel(), doctorId, startTime2);

        List<MedicalAppointment> appointmentsAtTime1 = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime1);
        List<MedicalAppointment> appointmentsAtTime2 = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime2);

        assertEquals(1, appointmentsAtTime1.size());
        assertEquals(1, appointmentsAtTime2.size());
    }

    @Test
    void bookAppointment_DoctorOutsideWorkingHours() {
        int doctorId = 1;
        LocalDateTime appointmentTime = LocalDateTime.of(2024, 11, 25, 8, 0);

        DoctorSchedule schedule = new DoctorSchedule(doctor,
                LocalDateTime.of(2024, 11, 25, 9, 0),
                LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);

        Patient patient = new Patient(1,
                new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"),
                "44051401359",
                new BirthDate("1990-05-15"),
                18);
        patientService.addPatient(patient);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> appointmentService.bookAppointment(patient.getPesel(), doctorId, appointmentTime));

        assertEquals("Doctor is not available at the requested time.", exception.getMessage());
    }
}
