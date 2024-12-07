package pl.clinicmanager.service;

import pl.clinicmanager.model.MedicalAppointment;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;
import pl.clinicmanager.repository.MedicalAppointmentRepository;

import java.time.LocalDateTime;

public class MedicalAppointmentService {
    private final DoctorRepository doctorRepository;
    private final DoctorScheduleRepository scheduleRepository;
    private final MedicalAppointmentRepository appointmentRepository;

    public MedicalAppointmentService(DoctorRepository doctorRepository, DoctorScheduleRepository scheduleRepository, MedicalAppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void bookAppointment(int appointmentId, int doctorId, int patientId, LocalDateTime startTime) {
        // Check if doctor exists
        if (doctorRepository.findById(doctorId).isEmpty()) {
            throw new IllegalArgumentException("Doctor with ID " + doctorId + " does not exist.");
        }

        boolean isInSchedule = scheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, startTime.toLocalDate())
                .stream()
                .anyMatch(schedule -> !startTime.isBefore(schedule.getStartTime()) && !startTime.plusMinutes(15).isAfter(schedule.getEndTime()));

        if (!isInSchedule) {
            throw new IllegalArgumentException("Appointment time is outside of doctor's available schedule.");
        }

        boolean isDoctorAvailable = appointmentRepository.findByDoctorIdAndTime(doctorId, startTime).isEmpty();
        if (!isDoctorAvailable) {
            throw new IllegalArgumentException("Doctor is already booked at this time.");
        }

        appointmentRepository.save(new MedicalAppointment(appointmentId, doctorId, patientId, startTime));
    }
}
