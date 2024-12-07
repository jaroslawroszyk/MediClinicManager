package pl.clinicmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.DoctorSchedule;
import pl.clinicmanager.model.MedicalAppointment;
import pl.clinicmanager.repository.DoctorScheduleRepository;
import pl.clinicmanager.repository.MedicalAppointmentRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MedicalAppointmentService_Tests {
    private DoctorScheduleRepository scheduleRepository;
    private MedicalAppointmentRepository appointmentRepository;
    private MedicalAppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        scheduleRepository = new DoctorScheduleRepository();
        appointmentRepository = new MedicalAppointmentRepository();
        appointmentService = new MedicalAppointmentService(scheduleRepository, appointmentRepository);
    }

    @Test
    void bookAppointment_ValidAppointment() {
        int doctorId = 1;
        int patientId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);

        DoctorSchedule schedule = new DoctorSchedule(doctorId, LocalDateTime.of(2024, 11, 25, 9, 0), LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);

        appointmentService.bookAppointment(1, doctorId, patientId, startTime);

        List<MedicalAppointment> appointments = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime);
        assertEquals(1, appointments.size());
        assertEquals(doctorId, appointments.get(0).getDoctorId());
        assertEquals(patientId, appointments.get(0).getPatientId());
        assertEquals(startTime, appointments.get(0).getStartTime());
    }

    @Test
    void bookAppointment_DoctorOutsideWorkingHours() {
        // Given
        int doctorId = 1;
        int patientId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 8, 0); // Przed godzinami pracy

        DoctorSchedule schedule = new DoctorSchedule(doctorId, LocalDateTime.of(2024, 11, 25, 9, 0), LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> appointmentService.bookAppointment(1, doctorId, patientId, startTime));
    }

    @Test
    void bookAppointment_DoctorAlreadyBooked() {
        int doctorId = 1;
        int patientId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);

        DoctorSchedule schedule = new DoctorSchedule(doctorId, LocalDateTime.of(2024, 11, 25, 9, 0), LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);

        MedicalAppointment existingAppointment = new MedicalAppointment(1, doctorId, 2, startTime);
        appointmentRepository.save(existingAppointment);

        assertThrows(IllegalArgumentException.class, () -> appointmentService.bookAppointment(2, doctorId, patientId, startTime));
    }

    @Test
    void bookAppointment_MultipleAppointmentsValid() {
        int doctorId = 1;
        int patientId1 = 1;
        int patientId2 = 2;
        LocalDateTime startTime1 = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 11, 25, 9, 15);

        DoctorSchedule schedule = new DoctorSchedule(doctorId, LocalDateTime.of(2024, 11, 25, 9, 0), LocalDateTime.of(2024, 11, 25, 17, 0));
        scheduleRepository.save(schedule);

        appointmentService.bookAppointment(1, doctorId, patientId1, startTime1);
        appointmentService.bookAppointment(2, doctorId, patientId2, startTime2);

        List<MedicalAppointment> appointmentsAtTime1 = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime1);
        List<MedicalAppointment> appointmentsAtTime2 = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime2);

        assertEquals(1, appointmentsAtTime1.size());
        assertEquals(1, appointmentsAtTime2.size());
    }
}
