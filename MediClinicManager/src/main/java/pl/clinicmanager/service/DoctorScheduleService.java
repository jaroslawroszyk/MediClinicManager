package pl.clinicmanager.service;

import pl.clinicmanager.model.DoctorSchedule;

import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DoctorScheduleService {
    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public DoctorScheduleService(DoctorScheduleRepository scheduleRepository, DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    public void createSchedule(int doctorId, LocalDateTime startTime, LocalDateTime endTime) {
        checkDoctorExist(doctorId);

        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        DoctorSchedule schedule = new DoctorSchedule(doctorId, startTime, endTime);
        scheduleRepository.save(schedule);
    }

    public List<DoctorSchedule> getSchedulesForNextWeek(int doctorId, LocalDate weekStart) {
        checkDoctorExist(doctorId);

        return scheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, weekStart);
    }

    private void checkDoctorExist(int doctorId) {
        doctorRepository.findById(doctorId).orElseThrow(() ->
                new IllegalArgumentException("Doctor with ID " + doctorId + " does 2not exist.")
        );
    }
}
