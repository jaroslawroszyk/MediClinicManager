package pl.clinicmanager.repository;

import pl.clinicmanager.model.DoctorSchedule;
import pl.clinicmanager.model.IDoctorScheduleRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorScheduleRepository implements IDoctorScheduleRepository {
    private List<DoctorSchedule> schedules = new ArrayList<>();

    @Override
    public void save(DoctorSchedule schedule) {
        schedules.add(schedule);
    }

    @Override
    public List<DoctorSchedule> findSchedulesByDoctorIdAndWeek(int doctorId, LocalDate weekStart) {
        return schedules.stream()
                .filter(schedule -> schedule.getDoctorId() == doctorId)
                .filter(schedule -> !schedule.getStartTime().toLocalDate().isBefore(weekStart) &&
                        !schedule.getStartTime().toLocalDate().isAfter(weekStart.plusDays(6)))
                .collect(Collectors.toList());
    }
}
