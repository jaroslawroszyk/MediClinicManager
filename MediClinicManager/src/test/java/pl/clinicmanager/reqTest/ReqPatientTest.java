package pl.clinicmanager.reqTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import pl.clinicmanager.model.*;
import org.junit.jupiter.api.Test;
import pl.clinicmanager.model.IDoctorScheduleRepository;
import pl.clinicmanager.model.IPatientRepository;
import pl.clinicmanager.model.IDoctorRepository;
import pl.clinicmanager.model.DoctorSpecialty;
import pl.clinicmanager.service.DoctorScheduleService;
import pl.clinicmanager.service.DoctorService;
import pl.clinicmanager.service.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;


public class ReqPatientTest {
    private PatientService patientService;
    private IPatientRepository patientRepository;
    private DoctorService doctorService;
    private IDoctorRepository doctorRepository;
    private DoctorScheduleService doctorScheduleService;
    private IDoctorScheduleRepository doctorScheduleRepository;

    @BeforeEach
    void setUp() {
        patientRepository = mock(IPatientRepository.class);
        patientService = new PatientService(patientRepository);
        doctorRepository = mock(IDoctorRepository.class);
        doctorService = new DoctorService(doctorRepository);

        doctorScheduleRepository = mock(IDoctorScheduleRepository.class);
        doctorScheduleService = new DoctorScheduleService(doctorScheduleRepository, doctorRepository);
    }

    @Test
    void receptionistCreateNewPatientProfile() // 1.1
    {
        /*
        As a receptionist, I want to be able to create a new patient profile containing their personal and contact information needed to provide medical services.
        */
        Patient patient = new Patient(new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        patientService.addPatient(patient);

        verify(patientRepository, times(1)).save(patient);

        Assertions.assertNotNull(patient.getPersonalInfo().getFirstName());
        Assertions.assertNotNull(patient.getPersonalInfo().getLastName());
        Assertions.assertNotNull(patient.getPesel());
        Assertions.assertNotNull(patient.getPersonalInfo().getPhoneNumber());
        Assertions.assertNotNull(patient.getPersonalInfo().getEmail());
    }

    @Test
    void receptionistFindByPesel() { // 1.2
        /*
         As a receptionist, I want to be able to find a patient by PESEL number and display all of his data.
         */
        Patient patient = new Patient(new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);

        when(patientRepository.findByPesel("44051401359")).thenReturn(Optional.of(patient));

        Optional<Patient> foundPatient = Optional.ofNullable(patientService.findPatientByPesel("44051401359"));

        Assertions.assertTrue(foundPatient.isPresent());
        Assertions.assertEquals(patient, foundPatient.get());
        Assertions.assertEquals("John", foundPatient.get().getPersonalInfo().getFirstName());
        Assertions.assertEquals("Doe", foundPatient.get().getPersonalInfo().getLastName());
        Assertions.assertEquals("44051401359", foundPatient.get().getPesel());

        verify(patientRepository, times(1)).findByPesel("44051401359");
    }

    @Test
    void receptionistFindByFirstNameAllMatchingPatients() { // 1.3.1 - delete it later
        /*
        TODO:
        for testing purposes delete it leter
         */
        Patient patient1 =
                new Patient(new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        Patient patient2 =
                new Patient(new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        List<Patient> patients = List.of(patient1, patient2);

        when(patientRepository.findPatientsByFirstName("John")).thenReturn(Optional.of(patients));

        List<Patient> foundPatients = patientService.findPatientByFirstName("John");

        Assertions.assertNotNull(foundPatients);
        Assertions.assertEquals(2, foundPatients.size());
        Assertions.assertTrue(foundPatients.contains(patient1));
        Assertions.assertTrue(foundPatients.contains(patient2));

        verify(patientRepository, times(1)).findPatientsByFirstName("John");
    }

    @Test
    void receptionistFindBySurnameAllMatchingPatients() { // 1.3
        /*
        Correct test
        As a receptionist I want to be able to search for all matching patients with a given last name and display all the details of the patients found.
         */
        Patient patient1 =
                new Patient(new PersonalInfo("John", "Doe", "+48123456789", "john.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 18);
        Patient patient2 =
                new Patient(new PersonalInfo("Ada", "Doe", "+48123456789", "du.doe@example.com", "Wroclaw"), "44051401359", new BirthDate("1990-05-15"), 28);
        List<Patient> patients = List.of(patient1, patient2);

        when(patientRepository.findPatientsByLastName("Doe")).thenReturn(Optional.of(patients));

        List<Patient> foundPatients = patientService.findPatientByLastName("Doe");

        Assertions.assertNotNull(foundPatients);
        Assertions.assertEquals(2, foundPatients.size());
        Assertions.assertTrue(foundPatients.contains(patient1));
        Assertions.assertTrue(foundPatients.contains(patient2));

        verify(patientRepository, times(1)).findPatientsByLastName("Doe");
    }

    @Test
    void hrCreateNewDoctorProfile() { // 2.1
        /*
         Jako pracownik działu HR chcę mieć możliwość utworzenia profilu zatrudnionego lekarza uwzględniając wszystkie jego specjalizacje.
         */
        Set<DoctorSpecialty> specialties = Set.of(DoctorSpecialty.CARDIOLOGY, DoctorSpecialty.DERMATOLOGY);
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), specialties);

        doctorService.createDoctor(doctor);

        verify(doctorRepository, times(1)).save(doctor);

        Assertions.assertNotNull(doctor.getSpecialties());
        Assertions.assertTrue(doctor.getSpecialties().contains(DoctorSpecialty.CARDIOLOGY));
        Assertions.assertTrue(doctor.getSpecialties().contains(DoctorSpecialty.DERMATOLOGY));
    }

    @Test
    void hrAddSpecialtyToExistingDoctor() { // 2.2
        /*
         Jako pracownik działu HR chcę mieć możliwość dodania nowej specjalizacji lekarzowi, który istnieje już w systemie.
         */
        Set<DoctorSpecialty> specialties = Set.of(DoctorSpecialty.CARDIOLOGY);
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), specialties);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        doctorService.addSpecialtyToDoctor(1, DoctorSpecialty.DERMATOLOGY);
        verify(doctorRepository, times(1)).save(doctor);

        Assertions.assertEquals(2, doctor.getSpecialties().size());
        Assertions.assertTrue(doctor.getSpecialties().contains(DoctorSpecialty.DERMATOLOGY));
    }

    @Test
    void receptionistFindDoctorById() { // 2.3
        /*
         Jako recepcjonista chcę mieć możliwość znalezienia lekarza po jego numerze ID oraz wyświetlenia jego danych oraz specjalizacji.
         */
        Set<DoctorSpecialty> specialties = Set.of(DoctorSpecialty.CARDIOLOGY);
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), specialties);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));

        Optional<Doctor> foundDoctor = doctorService.findDoctorById(1);

        Assertions.assertTrue(foundDoctor.isPresent());
        Assertions.assertEquals(doctor, foundDoctor.get());
        Assertions.assertEquals("Anna", foundDoctor.get().getPersonalInfo().getFirstName());
        Assertions.assertEquals("Nowak", foundDoctor.get().getPersonalInfo().getLastName());
        Assertions.assertTrue(foundDoctor.get().getSpecialties().contains(DoctorSpecialty.CARDIOLOGY));

        verify(doctorRepository, times(1)).findById(1);
    }

    @Test
    void receptionistFindDoctorsBySpecialty() { // 2.4
        /*
         Jako recepcjonista chcę mieć możliwość znalezienia wszystkich lekarzy o określonej specjalizacji oraz wyświetlenia ich danych oraz ID.
         */
        Doctor doctor1 = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
        Doctor doctor2 = new Doctor(2, new PersonalInfo("Jan", "Kowalski", "987654321", "jan.kowalski@example.com", "Warszawa"), Set.of(DoctorSpecialty.CARDIOLOGY));
        Set<Doctor> doctors = Set.of(doctor1, doctor2);

        when(doctorRepository.findBySpecialty(DoctorSpecialty.CARDIOLOGY)).thenReturn(doctors);

        Set<Doctor> foundDoctors = doctorService.findDoctorsBySpecialty(DoctorSpecialty.CARDIOLOGY);

        Assertions.assertNotNull(foundDoctors);
        Assertions.assertEquals(2, foundDoctors.size());
        Assertions.assertTrue(foundDoctors.contains(doctor1));
        Assertions.assertTrue(foundDoctors.contains(doctor2));

        verify(doctorRepository, times(1)).findBySpecialty(DoctorSpecialty.CARDIOLOGY);
    }

    @Test
    void managerCreateDoctorSchedule() { // 4.1
        /*
        Jako kierownik placówki chcę mieć możliwość tworzenia grafików dla wszystkich pracujących lekarzy.
        Pojedynczy grafik powinien zawierać godziny pracy wybranego lekarza w wybranym dniu.
        */
        Doctor doctor = new Doctor(1, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        int doctorId = 1;
        LocalDateTime startTime = LocalDateTime.of(2024, 12, 10, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 12, 10, 17, 0);
        DoctorSchedule schedule = new DoctorSchedule(doctorId, startTime, endTime);

        doctorScheduleService.createSchedule2(doctorId, schedule);

        verify(doctorScheduleRepository, times(1)).save(schedule);

        Assertions.assertEquals(doctorId, schedule.getDoctorId());
        Assertions.assertEquals(startTime, schedule.getStartTime());
        Assertions.assertEquals(endTime, schedule.getEndTime());
    }

    @Test
    void receptionistGetSchedulesForDoctorForNextWeek() {
    /*
     Jako pracownik recepcji chcę móc pobrać wszystkie utworzone grafiki wybranego lekarza na najbliższy tydzień,
     abym mógł sprawdzić w jakich godzinach przyjmuje pacjentów.
     */
        int doctorId = 1;

        Doctor doctor = new Doctor(doctorId, new PersonalInfo("Anna", "Nowak", "123456789", "anna.nowak@example.com", "Wroclaw"), Set.of(DoctorSpecialty.CARDIOLOGY));
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        LocalDate weekStart = LocalDate.now();

        List<DoctorSchedule> schedules = List.of(
                new DoctorSchedule(doctorId, LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0)),
                new DoctorSchedule(doctorId, LocalDateTime.now().plusDays(3).withHour(10).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().plusDays(3).withHour(14).withMinute(0).withSecond(0).withNano(0)),
                new DoctorSchedule(doctorId, LocalDateTime.now().plusDays(5).withHour(8).withMinute(0).withSecond(0).withNano(0), LocalDateTime.now().plusDays(5).withHour(12).withMinute(0).withSecond(0).withNano(0))
        );

        when(doctorScheduleRepository.findSchedulesByDoctorIdAndWeek(doctorId, weekStart)).thenReturn(schedules);

        List<DoctorSchedule> foundSchedules = doctorScheduleService.getSchedulesForNextWeek(doctorId, weekStart);

        Assertions.assertNotNull(foundSchedules);
        Assertions.assertEquals(3, foundSchedules.size());
        Assertions.assertEquals(doctorId, foundSchedules.get(0).getDoctorId());

        LocalDateTime expectedStartTime = LocalDateTime.now().plusDays(1).withHour(9).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime expectedEndTime = LocalDateTime.now().plusDays(1).withHour(15).withMinute(0).withSecond(0).withNano(0);
        Assertions.assertEquals(expectedStartTime, foundSchedules.get(0).getStartTime());
        Assertions.assertEquals(expectedEndTime, foundSchedules.get(0).getEndTime());

        verify(doctorScheduleRepository, times(1)).findSchedulesByDoctorIdAndWeek(doctorId, weekStart);
    }

}
