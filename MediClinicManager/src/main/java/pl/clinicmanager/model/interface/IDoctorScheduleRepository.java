package pl.clinicmanager.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IDoctorScheduleRepository {
    void save(DoctorSchedule schedule);
    List<DoctorSchedule> findSchedulesByDoctorIdAndWeek(int doctorId, LocalDateTime weekStart);
    Optional<DoctorSchedule> findByDoctorId(int doctorId);
}
