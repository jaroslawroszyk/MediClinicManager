package pl.clinicmanager.service;

import pl.clinicmanager.model.DoctorSchedule;

import pl.clinicmanager.model.IDoctorRepository;
import pl.clinicmanager.model.IDoctorScheduleRepository;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DoctorScheduleService {
    private final IDoctorScheduleRepository scheduleRepository;
    private final IDoctorRepository doctorRepository;

    public DoctorScheduleService(IDoctorScheduleRepository scheduleRepository, IDoctorRepository doctorRepository) {
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

    public void createSchedule2(int doctorId, DoctorSchedule schedule) {
        checkDoctorExist(doctorId);

        if (schedule.getEndTime().isBefore(schedule.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

//        DoctorSchedule schedule = new DoctorSchedule(doctorIdId, startTime, endTime);
        scheduleRepository.save(schedule);
    }

    public List<DoctorSchedule> getSchedulesForNextWeek(int doctorId, LocalDate weekStart) {
        checkDoctorExist(doctorId);
//        if (weekStart == null || weekStart.isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException("Week start date must be today or in the future.");
//        }
        return scheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, weekStart);
    }

    private void checkDoctorExist(int doctorId) {
        doctorRepository.findById(doctorId).orElseThrow(() ->
                new IllegalArgumentException("Doctor with ID " + doctorId + " does 2not exist.")
        );
    }
}
