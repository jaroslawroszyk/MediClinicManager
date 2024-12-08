package pl.clinicmanager.model;

import java.time.LocalDateTime;

public class DoctorSchedule {
    private final Doctor doctor;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public DoctorSchedule(Doctor doctor, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctor = doctor;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDoctorId() {
        return doctor.getId();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "DoctorSchedule{" +
                "doctorId=" + getDoctorId() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
