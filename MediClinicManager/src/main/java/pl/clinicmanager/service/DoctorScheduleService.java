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

    public void createSchedule(DoctorSchedule schedule) {
        checkDoctorExist(schedule.getDoctorId());

        if (schedule.getEndTime().isBefore(schedule.getStartTime())) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        scheduleRepository.save(schedule);
    }

    public List<DoctorSchedule> getSchedulesForNextWeek(int doctorId, LocalDateTime weekStart) {
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
