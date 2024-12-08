package pl.clinicmanager.model;

import java.time.LocalDateTime;

public class MedicalAppointment {
    private int appointmentId;
//    private int doctorId;
    private Doctor doctor;
    private Patient patient;
//    private int patientId;
    private LocalDateTime startTime;

    public MedicalAppointment(Patient patient, Doctor doctor, LocalDateTime startTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.startTime = startTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    
    public int getAppointmentId() { return appointmentId; }
//    public int getDoctorId() { return doctorId; }
//    public int getPatientId() { return patientId; }
    public LocalDateTime getStartTime() { return startTime; }
}
