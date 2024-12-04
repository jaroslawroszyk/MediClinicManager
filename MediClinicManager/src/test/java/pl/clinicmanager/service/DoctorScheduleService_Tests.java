package pl.clinicmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSchedule;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorScheduleService_Tests {
    private DoctorScheduleRepository scheduleRepository;
    private DoctorRepository doctorRepository;
    private DoctorScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        scheduleRepository = new DoctorScheduleRepository();
        doctorRepository = mock(DoctorRepository.class);
        scheduleService = new DoctorScheduleService(scheduleRepository, doctorRepository);
    }

    @Test
    void createSchedule_ValidSchedule() {
        int doctorId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 25, 17, 0);

        Doctor doctor = new Doctor(
                doctorId,
                "Jan",
                "Kowalski",
                "123456789",
                "jan.kowalski@example.com",
                "ul. Przykładowa 1",
                Set.of()
        );

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));

        scheduleService.createSchedule(doctorId, startTime, endTime);

        List<DoctorSchedule> schedules = scheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, startTime.toLocalDate());
        assertEquals(1, schedules.size());
        assertEquals(startTime, schedules.get(0).getStartTime());
        assertEquals(endTime, schedules.get(0).getEndTime());
    }

    @Test
    void createSchedule_NonExistentDoctor() {
        int doctorId = 999;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 25, 17, 0);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(doctorId, startTime, endTime));
    }

    @Test
    void createSchedule_InvalidTimes() {
        int doctorId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 17, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 25, 9, 0);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor()));

        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(doctorId, startTime, endTime));
    }

    @Test
    void getSchedulesForNextWeek() {
        int doctorId = 1;
        LocalDateTime startTime1 = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2024, 11, 25, 17, 0);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 11, 27, 10, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2024, 11, 27, 15, 0);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor()));

        scheduleService.createSchedule(doctorId, startTime1, endTime1);
        scheduleService.createSchedule(doctorId, startTime2, endTime2);

        List<DoctorSchedule> schedules = scheduleService.getSchedulesForNextWeek(doctorId, LocalDate.of(2024, 11, 25));

        assertEquals(2, schedules.size());
    }
}
