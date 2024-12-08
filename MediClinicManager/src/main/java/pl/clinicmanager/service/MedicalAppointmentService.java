package pl.clinicmanager.service;

import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSchedule;
import pl.clinicmanager.model.MedicalAppointment;
import pl.clinicmanager.model.Patient;
import pl.clinicmanager.repository.DoctorScheduleRepository;
import pl.clinicmanager.repository.MedicalAppointmentRepository;

import java.time.LocalDateTime;

public class MedicalAppointmentService {
    private final MedicalAppointmentRepository medicalAppointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DoctorScheduleRepository doctorScheduleRepository;

    public MedicalAppointmentService(
            MedicalAppointmentRepository medicalAppointmentRepository,
            PatientService patientService,
            DoctorService doctorService,
            DoctorScheduleRepository doctorScheduleRepository
    ) {
        this.medicalAppointmentRepository = medicalAppointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.doctorScheduleRepository = doctorScheduleRepository;
    }

    public void bookAppointment(String pesel, int doctorId, LocalDateTime appointmentTime) {
        Patient patient = patientService.findPatientByPesel(pesel);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }

        Doctor doctor = doctorService.findDoctorById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (medicalAppointmentRepository.existsByDoctorIdAndTime(doctorId, appointmentTime)) {
            throw new RuntimeException("Doctor is already booked at this time");
        }

        DoctorSchedule schedule = doctorScheduleRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor schedule not found"));

        if (appointmentTime.isBefore(schedule.getStartTime()) || appointmentTime.isAfter(schedule.getEndTime())) {
            throw new IllegalArgumentException("Doctor is not available at the requested time.");
        }

        MedicalAppointment appointment = new MedicalAppointment(patient, doctor, appointmentTime);
        medicalAppointmentRepository.save(appointment);
    }
}
