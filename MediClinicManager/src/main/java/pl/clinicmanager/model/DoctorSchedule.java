package pl.clinicmanager.model;

import java.time.LocalDateTime;

public class DoctorSchedule {
    private int doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public DoctorSchedule(int doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        this.doctorId = doctorId; // what if doctor doesont exit?
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDoctorId() {
        return doctorId;
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
                "doctorId=" + doctorId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
