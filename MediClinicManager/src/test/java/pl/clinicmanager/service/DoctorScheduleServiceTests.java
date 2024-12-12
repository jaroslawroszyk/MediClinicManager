package pl.clinicmanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.Doctor;
import pl.clinicmanager.model.DoctorSchedule;
import pl.clinicmanager.model.PersonalInfo;
import pl.clinicmanager.repository.DoctorRepository;
import pl.clinicmanager.repository.DoctorScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorScheduleServiceTests {
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
                new PersonalInfo(
                        "Jan",
                        "Kowalski",
                        "123456789",
                        "jan.kowalski@example.com",
                        "ul. Przykładowa 1"),
                Set.of()
        );

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime, endTime);
        scheduleService.createSchedule(schedule);

        List<DoctorSchedule> schedules = scheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, startTime);
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
        Doctor doctor = new Doctor(
                doctorId,
                new PersonalInfo(
                        "Jan",
                        "Kowalski",
                        "123456789",
                        "jan.kowalski@example.com",
                        "ul. Przykładowa 1"),
                Set.of()
        );
        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime, endTime);
        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(schedule));
    }

    @Test
    void createSchedule_InvalidTimes() {
        int doctorId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 17, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 11, 25, 9, 0);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor()));
        Doctor doctor = new Doctor(
                doctorId,
                new PersonalInfo(
                        "Jan",
                        "Kowalski",
                        "123456789",
                        "jan.kowalski@example.com",
                        "ul. Przykładowa 1"),
                Set.of()
        );
        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime, endTime);

        assertThrows(IllegalArgumentException.class, () -> scheduleService.createSchedule(schedule));
    }

    @Test
    void getSchedulesForNextWeek() {
        int doctorId = 1;
        LocalDateTime startTime1 = LocalDateTime.of(2024, 11, 25, 9, 0);
        LocalDateTime endTime1 = LocalDateTime.of(2024, 11, 25, 17, 0);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 11, 27, 10, 0);
        LocalDateTime endTime2 = LocalDateTime.of(2024, 11, 27, 15, 0);
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(new Doctor()));
        Doctor doctor = new Doctor(
                doctorId,
                new PersonalInfo(
                        "Jan",
                        "Kowalski",
                        "123456789",
                        "jan.kowalski@example.com",
                        "ul. Przykładowa 1"),
                Set.of()
        );
        DoctorSchedule schedule = new DoctorSchedule(doctor, startTime1, endTime1);
        scheduleService.createSchedule(schedule);
        DoctorSchedule schedule2 = new DoctorSchedule(doctor, startTime2, endTime2);
        scheduleService.createSchedule(schedule2);
        LocalDateTime startTime = LocalDateTime.of(2024, 11, 25, 9, 0);

        List<DoctorSchedule> schedules = scheduleService.getSchedulesForNextWeek(doctorId, startTime);

        assertEquals(2, schedules.size());
    }
}
