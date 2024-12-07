package pl.clinicmanager.model;

import java.time.LocalDateTime;

public class MedicalAppointment {
    private int appointmentId;
    private int doctorId;
    private int patientId;
    private LocalDateTime startTime;

    public MedicalAppointment(int appointmentId, int doctorId, int patientId, LocalDateTime startTime) {
        this.appointmentId = appointmentId;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.startTime = startTime;
    }

    // Gettery i settery
    public int getAppointmentId() { return appointmentId; }
    public int getDoctorId() { return doctorId; }
    public int getPatientId() { return patientId; }
    public LocalDateTime getStartTime() { return startTime; }
}
