package pl.clinicmanager.repository;

import pl.clinicmanager.model.DoctorSchedule;
import pl.clinicmanager.model.IDoctorScheduleRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DoctorScheduleRepository implements IDoctorScheduleRepository {
    private final List<DoctorSchedule> schedules = new ArrayList<>();

    @Override
    public void save(DoctorSchedule schedule) {
        schedules.add(schedule);
    }

    @Override
    public Optional<DoctorSchedule> findByDoctorId(int doctorId) {
        return schedules.stream()
                .filter(schedule -> schedule.getDoctorId() == doctorId)
                .findFirst();
    }

    @Override
    public List<DoctorSchedule> findSchedulesByDoctorIdAndWeek(int doctorId, LocalDateTime weekStart) {
        return schedules.stream()
                .filter(schedule -> schedule.getDoctorId() == doctorId)
                .filter(schedule -> !schedule.getStartTime().toLocalDate().isBefore(weekStart.toLocalDate()) &&
                        !schedule.getStartTime().toLocalDate().isAfter(weekStart.plusDays(6).toLocalDate()))
                .collect(Collectors.toList());
    }
}
