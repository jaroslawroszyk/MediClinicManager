package pl.clinicmanager.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDoctorScheduleRepository {
    public void save(DoctorSchedule schedule);
    public List<DoctorSchedule> findSchedulesByDoctorIdAndWeek(int doctorId, LocalDate weekStart);
}
